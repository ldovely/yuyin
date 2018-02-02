package com.truelore.yuyin.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lisbo
 * @description
 * @since 2018-2-1 16:23
 */

@Component
public class BaiDuParm {

    @Value ("${com.baidu.yuyin.appkey}")
    private String apiKey;

    @Value("${com.baidu.yuyin.secretkey}")
    private String secretkey;

    @Value("${com.baidu.auth.url}")
    private String authUrl;

    @Value("${com.baidu.yuyinshibie.url}")
    private String yuYinUrl;

    @Value("${com.baidu.cuid}")
    private String cuid;

    @Value("${yuyin.filePath}")
    private String filePath;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getYuYinUrl() {
        return yuYinUrl;
    }

    public void setYuYinUrl(String yuYinUrl) {
        this.yuYinUrl = yuYinUrl;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
