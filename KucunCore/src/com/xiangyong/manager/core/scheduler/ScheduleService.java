package com.xiangyong.manager.core.scheduler;

import java.util.Date;
import java.util.Map;

public interface ScheduleService {

    void submitJob(String jobType,String taskId, Map<String, String> params);

    void submitRepeatJob(String jobType,String taskId, Map<String, String> params, int repeatCount, long repeatInterval);

    void submitCronJob(String jobType,String taskId, Map<String, String> params, String cronExpression);

    void submitJobWithTriggerTime(String jobType,String taskId, Map<String, String> params, Date triggerTime);

}
