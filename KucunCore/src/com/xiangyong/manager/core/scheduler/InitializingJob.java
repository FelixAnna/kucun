package com.xiangyong.manager.core.scheduler;

import java.util.Map;

public interface InitializingJob {

    void run(Map<String,String> params);
}
