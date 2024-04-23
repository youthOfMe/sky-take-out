package com.chenhai.controller.user.aichat;

import com.chenhai.manager.AiManager;
import com.chenhai.result.Result;
import com.chenhai.vo.aichat.AiChatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user/aichat")
@Api(tags = "AI智能聊天接口")
@Slf4j
public class AiChatController {

    @Autowired
    private AiManager aiManager;

    /**
     * AI智能聊天接口
     * @param message
     * @return
     */
    @GetMapping("/chat")
    @ApiOperation("AI智能聊天接口")
    public Result<AiChatVO> getAiChat(String message) {
        String result = aiManager.doChat(1782676249773596674L, message);
        AiChatVO aiChatVO = new AiChatVO();
        aiChatVO.setMessage(result);
        aiChatVO.setDateTime(LocalDateTime.now());
        return Result.success(aiChatVO);
    }
}
