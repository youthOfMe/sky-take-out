package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     * 条件查询
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址数据
     * @param addressBook
     */
    @Insert("insert into address_book" +
            "(user_id, consignee, sex, phone, province_code, province_name, city_code, " +
            "city_name, district_code, district_name, detail, label, is_default) " +
            "values (#{user_id}, #{consignee}, #{sex}, #{phone}, #{province_code}, #{province_name}, #{city_code}," +
            "#{city_name}, #{district_code}, #{district_name}, #{detail}, #{label}, #{is_default})")
    void insert(AddressBook addressBook);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);
}
