package com.chenhai.service.team.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhai.entity.team.UserTeam;
import com.chenhai.mapper.team.UserTeamMapper;
import com.chenhai.service.team.UserTeamService;
import org.springframework.stereotype.Service;

@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam> implements UserTeamService {
}
