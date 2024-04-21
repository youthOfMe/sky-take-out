package com.chenhai.controller.user.team;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenhai.dto.team.TeamDTO;
import com.chenhai.dto.team.TeamQueryDTO;
import com.chenhai.entity.User;
import com.chenhai.entity.team.Team;
import com.chenhai.entity.team.UserTeam;
import com.chenhai.exception.BaseException;
import com.chenhai.result.Result;
import com.chenhai.service.UserService;
import com.chenhai.service.team.TeamService;
import com.chenhai.service.team.UserTeamService;
import com.chenhai.vo.team.TeamUserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("userTeamController")
@RequestMapping("/user/team")
@Api(tags = "C端队伍相关接口")
@Slf4j
public class TeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserTeamService userTeamService;

    @PostMapping("/add")
    public Result<Long> addTeam(@RequestBody TeamDTO teamAddRequest) {
        if (teamAddRequest == null) {
            throw new BaseException("请求参数为空");
        }
        User loginUser = userService.getLoginUser();
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest, team);
        long teamId = teamService.addTeam(team, loginUser);
        return Result.success(teamId);
    }

    @GetMapping("/list")
    public Result<List<TeamUserVO>> listTeams(TeamQueryDTO teamQuery) {
        if (teamQuery == null) {
            throw new BaseException("参数错误");
        }
        boolean isAdmin = userService.isAdmin();
        // 1、查询队伍列表
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        final List<Long> teamIdList = teamList.stream().map(TeamUserVO::getId).collect(Collectors.toList());
        // 2、判断当前用户是否已加入队伍
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        try {
            User loginUser = userService.getLoginUser();
            userTeamQueryWrapper.eq("user_id", loginUser.getId());
            userTeamQueryWrapper.in("team_id", teamIdList);
            List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
            // 已加入的队伍 id 集合
            Set<Long> hasJoinTeamIdSet = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
            teamList.forEach(team -> {
                boolean hasJoin = hasJoinTeamIdSet.contains(team.getId());
                team.setHasJoin(hasJoin);
            });
        } catch (Exception e) {
        }
        // 3、查询已加入队伍的人数
        QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
        userTeamJoinQueryWrapper.in("team_id", teamIdList);
        List<UserTeam> userTeamList = userTeamService.list(userTeamJoinQueryWrapper);
        // 队伍 id => 加入这个队伍的用户列表
        Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        teamList.forEach(team -> team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(), new ArrayList<>()).size()));
        return Result.success(teamList);
    }
}
