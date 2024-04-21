package com.chenhai.controller.user.team;

import com.chenhai.dto.team.TeamDTO;
import com.chenhai.entity.User;
import com.chenhai.entity.team.Team;
import com.chenhai.exception.BaseException;
import com.chenhai.result.Result;
import com.chenhai.service.UserService;
import com.chenhai.service.team.TeamService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userTeamController")
@RequestMapping("/user/team")
@Api(tags = "C端队伍相关接口")
@Slf4j
public class TeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

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
}
