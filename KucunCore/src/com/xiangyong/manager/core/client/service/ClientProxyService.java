package com.xiangyong.manager.core.client.service;

import com.xiangyong.manager.core.client.exception.ClientApiException;
import com.xiangyong.manager.core.client.model.ClientProxyRequest;
import com.xiangyong.manager.core.client.model.ClientProxyResponse;

/**
 * Created by Rex.Lei on 2017/8/30.
 */
public interface ClientProxyService {

    ClientProxyResponse doProxy(ClientProxyRequest request) throws ClientApiException;

}
