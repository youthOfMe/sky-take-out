create table address_book
(
    id            bigint auto_increment comment '主键'
        primary key,
    user_id       bigint                       not null comment '用户id',
    consignee     varchar(50)                  null comment '收货人',
    sex           varchar(2)                   null comment '性别',
    phone         varchar(11)                  not null comment '手机号',
    province_code varchar(12) charset utf8mb4  null comment '省级区划编号',
    province_name varchar(32) charset utf8mb4  null comment '省级名称',
    city_code     varchar(12) charset utf8mb4  null comment '市级区划编号',
    city_name     varchar(32) charset utf8mb4  null comment '市级名称',
    district_code varchar(12) charsetQ utf8mb4  null comment '区级区划编号',
    district_name varchar(32) charset utf8mb4  null comment '区级名称',
    detail        varchar(200) charset utf8mb4 null comment '详细地址',
    label         varchar(100) charset utf8mb4 null comment '标签',
    is_default    tinyint(1) default 0         not null comment '默认 0 否 1是'
)
    comment '地址簿' collate = utf8_bin;

create table app_img
(
    id           bigint            not null comment '主键ID'
        primary key,
    image_url    varchar(512)      null comment '图片地址',
    created_time datetime          null comment '创建时间',
    type         tinyint default 1 not null comment '图片类型 1 = 首页轮播'
)
    comment '应用图片';

create table black
(
    id          bigint unsigned null,
    type        int             null,
    target      varchar(32)     null,
    create_time datetime        null,
    update_time datetime        null
);

create table category
(
    id          bigint auto_increment comment '主键'
        primary key,
    type        int           null comment '类型   1 菜品分类 2 套餐分类',
    name        varchar(32)   not null comment '分类名称',
    sort        int default 0 not null comment '顺序',
    status      int           null comment '分类状态 0:禁用，1:启用',
    create_time datetime      null comment '创建时间',
    update_time datetime      null comment '更新时间',
    create_user bigint        null comment '创建人',
    update_user bigint        null comment '修改人',
    constraint idx_category_name
        unique (name)
)
    comment '菜品及套餐分类' collate = utf8_bin;

create table chart
(
    id           bigint auto_increment comment 'id'
        primary key,
    goal         text                                   null comment '分析目标',
    name         varchar(128)                           null comment '图表名称',
    chart_data   text                                   null comment '图表数据',
    chart_type   varchar(128)                           null comment '图表类型',
    gen_chart    text                                   null comment '生成的图表数据',
    gen_result   text                                   null comment '生成的分析结论',
    status       varchar(128) default 'succeed'         not null comment 'wait,running,succeed,failed',
    exec_message text                                   null comment '执行信息',
    user_id      bigint                                 null comment '创建用户 id',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint      default 0                 not null comment '是否删除'
)
    comment '图表信息表' collate = utf8mb4_unicode_ci;

create table community_category
(
    id           bigint auto_increment
        primary key,
    user_id      bigint  default 1 not null comment '创建者(版主)',
    manager_id   bigint  default 1 not null comment '版主',
    parent_id    bigint            null comment '归属分类',
    name         varchar(256)      null comment '板块名称',
    description  text              null comment '板块描述',
    cover_url    varchar(512)      null comment '分类封图',
    created_time datetime          null comment '创建时间',
    updated_time datetime          null comment '更新时间',
    deleted      tinyint default 0 not null comment '是否逻辑删除 0 = null 1 = 删除',
    recommended  tinyint default 0 not null comment '是否进行推荐 0 = 不推荐 1 = 推荐',
    sort         int     default 0 not null comment '排序'
)
    comment '社区板块';

create table community_commit
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    content      text     null comment '评论内容',
    user_id      bigint   not null comment '评论人id',
    post_id      bigint   null comment '帖子ID',
    parent_id    bigint   null comment '回复评论的ID',
    created_time datetime null comment '评论时间'
)
    comment '社区评论';

create table community_image
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    image_url    varchar(512) null comment '图片地址',
    post_id      bigint       null comment '帖子ID',
    created_time datetime     null comment '创建时间',
    updated_time datetime     null comment '更新时间'
);

create table community_parent_category
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    user_id      int               null comment 'bigint',
    name         varchar(256)      null comment '分类名称',
    description  text              null comment '分类描述',
    created_time datetime          null comment '创建时间',
    update_time  datetime          null comment '更新时间',
    `delete`     tinyint default 0 not null comment '是否逻辑删除 0 = 不删除 1 = 删除',
    sort         tinyint default 0 not null comment '排序'
)
    comment '板块分类';

create table community_post
(
    id            bigint auto_increment comment '主键ID'
        primary key,
    user_id       bigint            not null comment '博主',
    title         varchar(256)      null comment '帖子标题',
    content       text              null comment '帖子内容',
    thumb         int     default 0 not null comment '帖子点赞量',
    commit        int     default 0 not null comment 'commit',
    share         int     default 0 not null comment '帖子转发量',
    cover_url     varchar(512)      null comment '帖子封面',
    labels        varchar(256)      null comment '帖子标签',
    image_labels  varchar(256)      null comment '图片识别的标签',
    category_id   bigint            null comment '归属板块',
    column_id     bigint            null comment '归属专栏',
    created_time  datetime          null comment '创建时间',
    updated_time  datetime          null comment '更新时间',
    deleted       tinyint default 0 not null comment '逻辑删除 0 = 不删除 1 = 删除',
    avatar_url    varchar(512)      null comment '帖子内容',
    name          varchar(256)      null comment '用户昵称',
    image_urls    text              null comment '图片列表',
    active_status int     default 1 not null comment '博主的在线状态',
    category_name varchar(256)      null comment '归属板块名称',
    recommended   tinyint default 0 not null comment '是否被推荐'
);

create table contact
(
    id          bigint unsigned null,
    uid         bigint          null,
    room_id     bigint          null,
    read_time   datetime        null,
    active_time datetime        null,
    last_msg_id bigint          null,
    create_time datetime        null,
    update_time datetime        null
);

create table dish
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(32)    not null comment '菜品名称',
    category_id bigint         not null comment '菜品分类id',
    price       decimal(10, 2) null comment '菜品价格',
    image       varchar(255)   null comment '图片',
    description varchar(255)   null comment '描述信息',
    status      int default 1  null comment '0 停售 1 起售',
    create_time datetime       null comment '创建时间',
    update_time datetime       null comment '更新时间',
    create_user bigint         null comment '创建人',
    update_user bigint         null comment '修改人',
    constraint idx_dish_name
        unique (name)
)
    comment '菜品' collate = utf8_bin;

create table dish_flavor
(
    id      bigint auto_increment comment '主键'
        primary key,
    dish_id bigint       not null comment '菜品',
    name    varchar(32)  null comment '口味名称',
    value   varchar(255) null comment '口味数据list'
)
    comment '菜品口味关系表' collate = utf8_bin;

create table employee
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(32)   not null comment '姓名',
    username    varchar(32)   not null comment '用户名',
    password    varchar(64)   not null comment '密码',
    phone       varchar(11)   not null comment '手机号',
    sex         varchar(2)    not null comment '性别',
    id_number   varchar(18)   not null comment '身份证号',
    status      int default 1 not null comment '状态 0:禁用，1:启用',
    create_time datetime      null comment '创建时间',
    update_time datetime      null comment '更新时间',
    create_user bigint        null comment '创建人',
    update_user bigint        null comment '修改人',
    constraint idx_username
        unique (username)
)
    comment '员工信息' collate = utf8_bin;

create table group_member
(
    id          bigint unsigned null,
    group_id    bigint          null,
    uid         bigint          null,
    role        int             null,
    create_time datetime        null,
    update_time datetime        null
);

create table item_config
(
    id          bigint unsigned null,
    type        int             null,
    img         varchar(255)    null,
    `describe`  varchar(255)    null,
    create_time datetime        null,
    update_time datetime        null
);

create table message
(
    id           bigint unsigned null,
    room_id      bigint          null,
    from_uid     bigint          null,
    content      varchar(1024)   null,
    reply_msg_id bigint          null,
    status       int             null,
    gap_count    int             null,
    type         int             null,
    extra        json            null,
    create_time  datetime        null,
    update_time  datetime        null
);

create table message_mark
(
    id          bigint unsigned null,
    msg_id      bigint          null,
    uid         bigint          null,
    type        int             null,
    status      int             null,
    create_time datetime        null,
    update_time datetime        null
);

create table order_detail
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(32)    null comment '名字',
    image       varchar(255)   null comment '图片',
    order_id    bigint         not null comment '订单id',
    dish_id     bigint         null comment '菜品id',
    setmeal_id  bigint         null comment '套餐id',
    dish_flavor varchar(50)    null comment '口味',
    number      int default 1  not null comment '数量',
    amount      decimal(10, 2) not null comment '金额'
)
    comment '订单明细表' collate = utf8_bin;

create table orders
(
    id                      bigint auto_increment comment '主键'
        primary key,
    number                  varchar(50)          null comment '订单号',
    status                  int        default 1 not null comment '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
    user_id                 bigint               not null comment '下单用户',
    address_book_id         bigint               not null comment '地址id',
    order_time              datetime             not null comment '下单时间',
    checkout_time           datetime             null comment '结账时间',
    pay_method              int        default 1 not null comment '支付方式 1微信,2支付宝',
    pay_status              tinyint    default 0 not null comment '支付状态 0未支付 1已支付 2退款',
    amount                  decimal(10, 2)       not null comment '实收金额',
    remark                  varchar(100)         null comment '备注',
    phone                   varchar(11)          null comment '手机号',
    address                 varchar(255)         null comment '地址',
    user_name               varchar(32)          null comment '用户名称',
    consignee               varchar(32)          null comment '收货人',
    cancel_reason           varchar(255)         null comment '订单取消原因',
    rejection_reason        varchar(255)         null comment '订单拒绝原因',
    cancel_time             datetime             null comment '订单取消时间',
    estimated_delivery_time datetime             null comment '预计送达时间',
    delivery_status         tinyint(1) default 1 not null comment '配送状态  1立即送出  0选择具体时间',
    delivery_time           datetime             null comment '送达时间',
    pack_amount             int                  null comment '打包费',
    tableware_number        int                  null comment '餐具数量',
    tableware_status        tinyint(1) default 1 not null comment '餐具数量状态  1按餐量提供  0选择具体数量'
)
    comment '订单表' collate = utf8_bin;

create table our_story_column
(
    id                bigint auto_increment comment '主键ID'
        primary key,
    name              varchar(128)     not null comment '专栏名称',
    name_desc         text             not null comment '专栏描述',
    created_user      bigint default 1 not null comment '创建者 老大 主键关联',
    post_number       int    default 0 not null comment '帖子数量',
    thumb_number      int    default 0 not null comment '点赞数',
    collection_number int    default 0 not null comment '收藏数量',
    cover_url         varchar(512)     null comment '封面',
    created_time      datetime         not null comment '创建时间',
    updated_time      datetime         not null comment '更新时间'
)
    comment '我们的故事的专栏';

create table our_story_post
(
    id                int auto_increment comment '主键ID'
        primary key,
    user_id           bigint        null comment '发帖子的用户',
    title             varchar(256)  not null comment '帖子标题',
    content           text          not null comment '帖子内容',
    our_story_column  int           null comment '归属专栏',
    thumb_number      int default 0 not null comment '点赞数',
    collection_number int default 0 not null comment '收藏数',
    cover_url         varchar(512)  null comment '封面图片',
    create_time       datetime      null comment '创建时间',
    update_time       datetime      null comment '更新时间',
    image_url_list    text          not null comment '图片列表'
)
    comment '我们的故事的帖子';

create table role
(
    id          bigint unsigned null,
    name        varchar(64)     null,
    create_time datetime        null,
    update_time datetime        null
);

create table room
(
    id          bigint unsigned null,
    type        int             null,
    hot_flag    int             null,
    active_time datetime        null,
    last_msg_id bigint          null,
    ext_json    json            null,
    create_time datetime        null,
    update_time datetime        null
);

create table room_friend
(
    id          bigint unsigned null,
    room_id     bigint          null,
    uid1        bigint          null,
    uid2        bigint          null,
    room_key    varchar(64)     null,
    status      int             null,
    create_time datetime        null,
    update_time datetime        null
);

create table room_group
(
    id            bigint unsigned null,
    room_id       bigint          null,
    name          varchar(16)     null,
    avatar        varchar(256)    null,
    ext_json      json            null,
    delete_status int             null,
    create_time   datetime        null,
    update_time   datetime        null
);

create table secure_invoke_record
(
    id                 bigint unsigned null,
    secure_invoke_json json            null,
    status             tinyint         null,
    next_retry_time    datetime        null,
    retry_times        int             null,
    max_retry_times    int             null,
    fail_reason        text            null,
    create_time        datetime        null,
    update_time        datetime        null
);

create table sensitive_word
(
    word varchar(255) null
);

create table setmeal
(
    id          bigint auto_increment comment '主键'
        primary key,
    category_id bigint         not null comment '菜品分类id',
    name        varchar(32)    not null comment '套餐名称',
    price       decimal(10, 2) not null comment '套餐价格',
    status      int default 1  null comment '售卖状态 0:停售 1:起售',
    description varchar(255)   null comment '描述信息',
    image       varchar(255)   null comment '图片',
    create_time datetime       null comment '创建时间',
    update_time datetime       null comment '更新时间',
    create_user bigint         null comment '创建人',
    update_user bigint         null comment '修改人',
    constraint idx_setmeal_name
        unique (name)
)
    comment '套餐' collate = utf8_bin;

create table setmeal_dish
(
    id         bigint auto_increment comment '主键'
        primary key,
    setmeal_id bigint         null comment '套餐id',
    dish_id    bigint         null comment '菜品id',
    name       varchar(32)    null comment '菜品名称 （冗余字段）',
    price      decimal(10, 2) null comment '菜品单价（冗余字段）',
    copies     int            null comment '菜品份数'
)
    comment '套餐菜品关系' collate = utf8_bin;

create table shopping_cart
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(32)    null comment '商品名称',
    image       varchar(255)   null comment '图片',
    user_id     bigint         not null comment '主键',
    dish_id     bigint         null comment '菜品id',
    setmeal_id  bigint         null comment '套餐id',
    dish_flavor varchar(50)    null comment '口味',
    number      int default 1  not null comment '数量',
    amount      decimal(10, 2) not null comment '金额',
    create_time datetime       null comment '创建时间'
)
    comment '购物车' collate = utf8_bin;

create table tag
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    tag_name     varchar(256)                       not null comment '标签名',
    user_id      bigint                             null comment '用户ID',
    parent_id    bigint                             null comment '父标签ID',
    is_parent    tinyint                            null comment '是否为父标签 0 = 不是 1 = 是',
    created_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    deleted      tinyint  default 0                 not null comment '是否删除 0 = 不删除 1 = 删除',
    constraint unique_tag_name
        unique (tag_name)
)
    comment '标签表';

create index idx_user_id
    on tag (user_id);

create table team
(
    id          bigint auto_increment
        primary key,
    description varchar(512)                       null,
    max_num     tinyint  default 1                 not null,
    expire_time datetime                           null,
    user_id     bigint                             null,
    status      tinyint  default 0                 not null,
    password    varchar(256)                       null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint  default 0                 not null,
    name        varchar(512)                       null
);

create table thumb_post_user
(
    id         bigint auto_increment comment '点赞'
        primary key,
    post_id    varchar(256) not null comment '帖子ID',
    user_id    varchar(256) not null comment '用户ID',
    thumb_time datetime     not null comment '点赞时间'
)
    comment '用户 帖子 点赞关联表';

create table user
(
    id             bigint auto_increment comment '主键'
        primary key,
    openid         varchar(45)                                 null comment '微信用户唯一标识',
    name           varchar(32)                                 null comment '姓名',
    phone          varchar(11)                                 null comment '手机号',
    sex            varchar(2)                                  null comment '性别',
    id_number      varchar(18)                                 null comment '身份证号',
    avatar         varchar(500)                                null comment '头像',
    create_time    datetime                                    null,
    account        varchar(256)                                null comment '用户账号',
    password       varchar(256)   default '123456'             not null comment '用户密码',
    update_time    datetime                                    null comment '更新时间',
    column_name    varchar(256)                                null comment 'signature',
    signature      varchar(256)                                null comment '用户签名',
    xinghaibi      decimal(10, 2) default 0.00                 not null comment '星海币',
    last_opt_time  datetime(3)    default CURRENT_TIMESTAMP(3) null comment '最后上线时间',
    ip_info        json                                        null comment 'ip信息',
    item_id        bigint                                      null comment '勋章的id',
    status         int            default 0                    not null comment '用户的状态',
    active_status  int            default 1                    null comment '在线1 离线0',
    background_url varchar(512)                                null comment '用户资料背景图片',
    lv             int            default 1                    not null comment '用户等级',
    thumb          bigint         default 0                    not null comment '用户获赞',
    experience     int            default 0                    not null comment '经验值',
    sign_icon      int            default 0                    not null comment '签到币',
    sign_day       int            default 0                    not null comment '连续签到的天数',
    is_sign        tinyint        default 0                    not null comment '判断今日是否签到了 0 = 无 1 = 有',
    tags           varchar(1024)                               null comment '用户标签',
    rmb            int            default 0                    not null comment '人民币',
    role           int            default 0                    not null comment '权限 0 = 普通人 1 = 管理员'
)
    comment '用户信息' collate = utf8_bin;

create table user_apply
(
    id          bigint unsigned null,
    uid         bigint          null,
    type        int             null,
    target_id   bigint          null,
    msg         varchar(64)     null,
    status      int             null,
    read_status int             null,
    create_time datetime        null,
    update_time datetime        null
);

create table user_backpack
(
    id          bigint unsigned null,
    uid         bigint          null,
    item_id     bigint          null,
    status      int             null,
    idempotent  varchar(64)     null,
    create_time datetime        null,
    update_time datetime        null
);

create table user_emoji
(
    id             bigint unsigned null,
    uid            bigint          null,
    expression_url varchar(255)    null,
    delete_status  int             null,
    create_time    datetime        null,
    update_time    datetime        null
);

create table user_friend
(
    id            bigint unsigned null,
    uid           bigint          null,
    friend_uid    bigint          null,
    delete_status int             null,
    create_time   datetime        null,
    update_time   datetime        null
);

create table user_role
(
    id          bigint unsigned null,
    uid         bigint          null,
    role_id     bigint          null,
    create_time datetime        null,
    update_time datetime        null
);

create table user_team
(
    id          bigint auto_increment
        primary key,
    user_id     bigint                             null,
    team_id     bigint                             null,
    join_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    create_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint  default 0                 not null
);

