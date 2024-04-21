package com.chenhai.service.team;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhai.entity.User;
import com.chenhai.entity.team.Team;

public interface TeamService extends IService<Team> {
    long addTeam(Team team, User loginUser);
}
