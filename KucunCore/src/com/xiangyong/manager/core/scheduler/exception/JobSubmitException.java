package com.xiangyong.manager.core.scheduler.exception;

import com.github.ltsopensource.jobclient.domain.Response;

public class JobSubmitException extends RuntimeException {

    private Response response;

    public JobSubmitException(String message,Response response) {
        super(response == null ? message : response.getMsg());
    }

    public Response getResponse() {
        return response;
    }
}
