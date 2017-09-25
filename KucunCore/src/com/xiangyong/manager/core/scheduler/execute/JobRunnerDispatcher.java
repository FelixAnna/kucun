package com.xiangyong.manager.core.scheduler.execute;

import com.xiangyong.manager.core.scheduler.EnableLTS;
import com.xiangyong.manager.core.scheduler.annotation.JobType;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.spring.tasktracker.JobDispatcher;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnBean(annotation = EnableLTS.class)
@JobRunner4TaskTracker
public class JobRunnerDispatcher implements JobRunner,ApplicationListener<ContextRefreshedEvent> {

    private static final ConcurrentHashMap<String, JobRunner>
            JOB_RUNNER_MAP = new ConcurrentHashMap<String, JobRunner>();

    private static Logger logger = LoggerFactory.getLogger(JobDispatcher.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, JobRunner> beansWithAnnotation = applicationContext.getBeansOfType(JobRunner.class);
        if(beansWithAnnotation != null){
            logger.info("发现任务处理器{}个",beansWithAnnotation.size());
        }
        if(beansWithAnnotation != null && !beansWithAnnotation.isEmpty()) {
            List<Job> jobList = new ArrayList<>();
            for (Map.Entry<String, JobRunner> entry : beansWithAnnotation.entrySet()) {
                if(entry.getValue() instanceof JobRunnerDispatcher){
                    continue;
                }
                JobType jobType = entry.getValue().getClass().getAnnotation(JobType.class);
                if(jobType == null){
                    logger.warn("发现任务处理器{}未标记JobType注解",entry.getValue().getClass().getName());
                    continue;
                }
                logger.info("添加任务处理器：任务类型{},任务处理器类{}",jobType.value(),entry.getValue().getClass().getName());
                JOB_RUNNER_MAP.put(jobType.value(),entry.getValue());
            }
        }
    }

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        String type = jobContext.getJob().getParam("_type");
        return JOB_RUNNER_MAP.get(type).run(jobContext);
    }
}