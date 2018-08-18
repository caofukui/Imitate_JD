package com.mjd.imitate_jd.bean;

public class MessageEvent {

    private String mobile;
    private String password;
    private String url;

    public MessageEvent(String mobile, String password, String url) {
        this.mobile = mobile;
        this.password = password;
        this.url = url;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
