package com.xiangyong.manager.core.client.service;

import com.xiangyong.manager.core.client.exception.ClientApiException;
import com.xiangyong.manager.core.client.model.ClientProxyRequest;
import com.xiangyong.manager.core.client.model.ClientProxyResponse;

public interface ClientProxyService {

    ClientProxyResponse doProxy(ClientProxyRequest request) throws ClientApiException;

}
