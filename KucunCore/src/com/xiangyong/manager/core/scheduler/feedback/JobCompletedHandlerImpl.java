package com.xiangyong.manager.core.scheduler.feedback;

import com.xiangyong.manager.common.util.DateUtils;
import com.xiangyong.manager.core.scheduler.EnableLTS;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.domain.JobResult;
import com.github.ltsopensource.jobclient.support.JobCompletedHandler;
import com.github.ltsopensource.spring.boot.annotation.JobCompletedHandler4JobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.Date;
import java.util.List;

// 增加这个注解, 即可作为任务执行结果反馈回调接口(也可以不使用)
@ConditionalOnBean(annotation = EnableLTS.class)
@JobCompletedHandler4JobClient
public class JobCompletedHandlerImpl implements JobCompletedHandler {

    private Logger logger = LoggerFactory.getLogger(JobCompletedHandlerImpl.class);

    @Override
    public void onComplete(List<JobResult> jobResults) {
        // 任务执行反馈结果处理
        if (CollectionUtils.isNotEmpty(jobResults)) {
            for (JobResult jobResult : jobResults) {
                logger.info(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss") + " 任务执行完成：" + jobResult);
            }
        }
    }
}
