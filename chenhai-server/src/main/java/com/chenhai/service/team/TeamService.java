package com.chenhai.service.team;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhai.dto.team.TeamJoinDTO;
import com.chenhai.dto.team.TeamQueryDTO;
import com.chenhai.dto.team.TeamQuitDTO;
import com.chenhai.entity.User;
import com.chenhai.entity.team.Team;
import com.chenhai.vo.user.TeamUserVO;

import java.util.List;

public interface TeamService extends IService<Team> {
    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 查询队伍列表
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQueryDTO teamQuery, boolean isAdmin);

    boolean joinTeam(TeamJoinDTO teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitDTO teamQuitRequest, User loginUser);
}
