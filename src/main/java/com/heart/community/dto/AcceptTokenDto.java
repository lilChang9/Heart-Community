package com.heart.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收到的token信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptTokenDto {

    private String access_token;

    private String scope;

    private String token_type;
}
