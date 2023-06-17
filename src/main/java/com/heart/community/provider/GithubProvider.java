package com.heart.community.provider;

import com.alibaba.fastjson.JSON;
import com.heart.community.dto.AcceptTokenDto;
import com.heart.community.dto.AccessTokenDto;
import com.heart.community.dto.GithubUserInfo;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {

    private final String AccessTokenUrl = "https://github.com/login/oauth/access_token";

    private final String UserInfoUrl = "https://api.github.com/user";

    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(JSON.toJSON(accessTokenDto).toString(), mediaType);
        Request request = new Request.Builder()
                .addHeader("Accept","application/json")
                .url(AccessTokenUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            AcceptTokenDto acceptToken = JSON.parseObject(response.body().string(), AcceptTokenDto.class);
            return acceptToken.getAccess_token();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取 Access Token失败");
        }
    }

    /**
     * 根据用户的 token 获取用户信息
     *
     * @return 用户信息
     */
    public GithubUserInfo getUserInfo(String token) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UserInfoUrl)
                .addHeader("Authorization", "Bearer " + token)
                .build();


        try (Response response = client.newCall(request).execute()) {
            System.out.println(JSON.toJSON(response.body()).toString());
            // 将得到的用户信息转换为 UserInfoDto
            GithubUserInfo githubUserInfo = JSON.parseObject(response.body().string(), GithubUserInfo.class);
            return githubUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
