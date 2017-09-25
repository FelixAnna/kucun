package com.xiangyong.manager.core.scheduler.annotation;

import java.lang.annotation.*;

/**
 * 注入到Spring ApplicationContext的Bean如果标记此注解，会在Spring加载完成后自动提交任务到LTS
 * @See com.github.ltsopensource.core.domain.Job
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJob {

    /**
     * 任务id，注意不能重复
     * @return
     */
    String taskId();

    /**
     * 任务执行所必须的参数，请使用json格式的字符串
     * @return
     */
    String extParams();

    /**
     * cron表达式
     * @return
     */
    String cronExpression();

    /**
     * 任务优先级
     * @return
     */
    int priority();

    /**
     * 最大重试次数
     * @return
     */
    int maxRetryTimes();
}
