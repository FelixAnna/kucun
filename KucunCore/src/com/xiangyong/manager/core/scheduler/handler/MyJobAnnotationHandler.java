package com.xiangyong.manager.core.scheduler.handler;

import com.alibaba.fastjson.JSON;
import com.xiangyong.manager.common.util.JsonUtils;
import com.xiangyong.manager.core.scheduler.EnableLTS;
import com.xiangyong.manager.core.scheduler.annotation.MyJob;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ConditionalOnBean(annotation = EnableLTS.class)
@Component
public class MyJobAnnotationHandler implements ApplicationListener<ContextRefreshedEvent> {

    private JobClient jobClient;

    private static Logger logger = LoggerFactory.getLogger(MyJobAnnotationHandler.class);

    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        this.jobClient = (JobClient) applicationContext.getBean("jobClient");
        if(jobClient == null){
            logger.warn("找不到jobClient,请检查ApplicationContext是否注入jobClient");
            return;
        }
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(MyJob.class);
        if(beansWithAnnotation != null){
            logger.info("发现任务{}个",beansWithAnnotation.size());
        }
        try {
            if(beansWithAnnotation != null && !beansWithAnnotation.isEmpty()){
                List<Job> jobList = new ArrayList<>();
                for (Map.Entry<String,Object> entry : beansWithAnnotation.entrySet()) {
                    Object bean = entry.getValue();
                    Class<?> clazz = ClassUtils.getUserClass(bean);
                    //获取类上的MyJob注解
                    MyJob classJob = clazz.getAnnotation(MyJob.class);
                    String className = clazz.getName();
                    //类上的MyJob注解未声明taskId，则解析方法
                    if(StringUtils.isEmpty(classJob.taskId())){
                        Method[] methods = clazz.getMethods();
                        if(methods == null || methods.length == 0){
                            logger.warn("类{}上标记了MyJob注解，既未指定taskId，也未声明任何方法",className);
                        }else{
                            for (Method method : methods) {
                                MyJob methodJob = method.getAnnotation(MyJob.class);
                                if(methodJob == null){
                                    continue;
                                }
                                jobList.add(packageJob(methodJob,className,method.getName()));
                            }
                        }
                    }else{
                        jobList.add(packageJob(classJob,className,null));
                    }
                }
                logger.info("待提交的任务，jobs:{}", JsonUtils.toJSONString(jobList));
                if (!CollectionUtils.isEmpty(jobList)) {
                    Response response = this.jobClient.submitJob(jobList);
                    logger.info("提交任务结束，response:{},jobs:{}", JsonUtils.toJSONString(response), JsonUtils.toJSONString(jobList));
                }
            }
        }catch (Exception e){
            logger.error("添加任务发生异常",e);
        }

    }

    @SuppressWarnings("unchecked")
    private Job packageJob(MyJob myJob, String className, String methodName) {
        Job job = new Job();
        job.setCronExpression(myJob.cronExpression());
        job.setTaskTrackerNodeGroup("itao_taskTracker");
        job.setTaskId("itaoyou_" + myJob.taskId());

        String extParamsString = myJob.extParams();
        if (StringUtils.isNotBlank(extParamsString)) {
            Map<String,String> extParams = JSON.parseObject(extParamsString, Map.class);
            job.setExtParams(extParams);
        }
        job.setParam("_type","system");
        job.setParam("className", className);
        if (StringUtils.isNotEmpty(methodName)) {
            job.setParam("methodName", methodName);
        }

        job.setMaxRetryTimes(myJob.maxRetryTimes());
        job.setPriority(myJob.priority());
        job.setNeedFeedback(true);
        job.setReplaceOnExist(true);
        job.setRelyOnPrevCycle(true);

        return job;
    }
}
