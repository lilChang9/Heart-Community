package com.heart.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {

    /**
     * 从 GitHub 收到的 OAuth 应用程序的客户端 ID
     */
    private String client_id;

    /**
     * 从 GitHub 收到的 OAuth 应用程序的客户端密码
     */
    private String client_secret;

    /**
     * 收到的作为对请求用户 Github 身份的响应的代码
     */
    private String code;

    /**
     * 用户在授权后被发送到的应用程序中的 URL
     */
    private String redirect_uri;
}
