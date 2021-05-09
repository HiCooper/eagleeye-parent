package com.berry.eagleeye.auth.module.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/12 10:51
 * fileName：LoginSuccessVo
 * Use：
 */
@Data
public class LoginSuccessVo {

    private String accessToken;

    private long expires;

    private UserInfo userInfo;

    public LoginSuccessVo(String accessToken, long expires, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.expires = expires;
        this.userInfo = userInfo;
    }

    @Data
    public static class UserInfo {
        private String username;

        private String nickName;

        public UserInfo(String username, String nickName) {
            this.username = username;
            this.nickName = nickName;
        }
    }
}
