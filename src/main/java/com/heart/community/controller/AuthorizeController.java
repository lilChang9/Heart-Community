package com.heart.community.controller;

import com.heart.community.dto.AccessTokenDto;
import com.heart.community.dto.GithubUserInfo;
import com.heart.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class AuthorizeController {

    @Resource
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    /**
     * 根据github的回调函数发起POST请求获取access_token
     * @param code 临时代码
     * @param state 不可猜测的随机字符串,它用于防止跨站请求伪造攻击
     * @return 登录认证完成后跳转到主页面
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){

        AccessTokenDto accessTokenDto = new AccessTokenDto(
                clientId,
                clientSecret,
                code,
                redirectUri);
        // 获取用户的 accessToken
        String token = githubProvider.getAccessToken(accessTokenDto);
        System.out.println("token = " + token);
        // 根据用户的 token 获取用户的信息
        GithubUserInfo githubUserInfo = githubProvider.getUserInfo(token);
        System.out.println("githubUserInfo = " + githubUserInfo);
        return "index";
    }
}
