package com.xiangyong.manager.core.client.model;

import com.xiangyong.manager.core.client.enums.RequestSource;

import java.io.Serializable;

/**
 * Created by Rex.Lei on 2017/8/30.
 */
public class ClientProxyRequest implements Serializable {

    private String cmd;

    private RequestSource requestSource;

    private String request;

    private String openid;

    private Long memberId;

    private String memberName;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public RequestSource getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(RequestSource requestSource) {
        this.requestSource = requestSource;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
