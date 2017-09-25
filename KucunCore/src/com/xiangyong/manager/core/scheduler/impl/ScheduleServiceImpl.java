package com.xiangyong.manager.core.scheduler.impl;

import com.xiangyong.manager.common.util.JsonUtils;
import com.xiangyong.manager.core.scheduler.EnableLTS;
import com.xiangyong.manager.core.scheduler.ScheduleService;
import com.xiangyong.manager.core.scheduler.exception.JobSubmitException;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@ConditionalOnBean(annotation = EnableLTS.class)
@Component
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private JobClient jobClient;

    private Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Override
    public void submitJob(String jobType, String taskId, Map<String, String> params) {
        submitJob(jobType,taskId,params,null,null,0,null);
    }

    @Override
    public void submitRepeatJob(String jobType, String taskId, Map<String, String> params, int repeatCount, long repeatInterval) {
        submitJob(jobType,taskId,params,null,null,repeatCount,repeatInterval);
    }

    @Override
    public void submitCronJob(String jobType, String taskId, Map<String, String> params, String cronExpression) {
        submitJob(jobType,taskId,params,null,cronExpression,0,null);
    }

    @Override
    public void submitJobWithTriggerTime(String jobType, String taskId, Map<String, String> params, Date triggerTime) {
        submitJob(jobType,taskId,params,triggerTime,null,0,null);
    }

    private void submitJob(String jobType, String taskId, Map<String, String> params, Date triggerTime,String cronExpression, int repeatCount, Long repeatInterval){
        Response response = null;
        try {
            Job job = new Job();
            job.setTaskId(taskId);
            job.setExtParams(params);
            job.setParam("_type", jobType);
            job.setTaskTrackerNodeGroup("itao_taskTracker");
            job.setTriggerDate(triggerTime);
            job.setCronExpression(cronExpression);
            job.setRepeatCount(repeatCount);
            job.setRepeatInterval(repeatInterval);
            job.setNeedFeedback(true);
            job.setReplaceOnExist(true);
            response = jobClient.submitJob(job);
            logger.info(JsonUtils.toJSONString(response));
        }catch (Exception e){
            throw new JobSubmitException("任务提交失败",response);
        }
    }
}
