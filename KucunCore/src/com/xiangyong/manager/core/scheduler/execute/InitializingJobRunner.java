package com.xiangyong.manager.core.scheduler.execute;

import com.xiangyong.manager.core.scheduler.EnableLTS;
import com.xiangyong.manager.core.scheduler.annotation.JobType;
import com.xiangyong.manager.core.scheduler.exception.JobExecuteException;
import com.xiangyong.manager.core.util.SpringContextHolder;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@ConditionalOnBean(annotation = EnableLTS.class)
@JobType("system")
@Component
public class InitializingJobRunner implements JobRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializingJobRunner.class);

    @Override
    @SuppressWarnings("unchecked")
    public Result run(JobContext jobContext) throws Throwable {
        long startTime = 0L;
        BizLogger bizLogger = jobContext.getBizLogger();
        try {
            LOGGER.info("我要执行：" + jobContext);
            Map<String, String> extParams = jobContext.getJob().getExtParams();
            //检查className是否存在
            String className = (String) extParams.get("className");
            if (StringUtils.isEmpty(className)) {
                String msg = "任务执行类className参数不存在";
                LOGGER.error(msg);
                bizLogger.error(msg);
                return new Result(Action.EXECUTE_FAILED, msg);
            }
            //检查类是否存在
            Class clazz = Class.forName(className);
            if (clazz == null) {
                String msg = "任务执行类路径不对，类路径：" + className;
                LOGGER.error(msg);
                bizLogger.error(msg);
                return new Result(Action.EXECUTE_FAILED, msg);
            }
            //检查类实例是否存在
            Object bean = SpringContextHolder.getBean(clazz);
            if (bean == null) {
                String msg = "不存在该类的实例，类路径：" + className;
                LOGGER.error(msg);
                bizLogger.error(msg);
                return new Result(Action.EXECUTE_FAILED, msg);
            }

            Method method = null;
            String methodName = (String) extParams.get("methodName");
            if (StringUtils.isNotEmpty(methodName))
                method = clazz.getMethod(methodName, new Class[]{Map.class});
            else {
                methodName = "run";
                method = clazz.getMethod("run", new Class[]{Map.class});
            }
            if (method == null) {
                String msg = "不存在任务执行方法，类路径：" + className + "，方法名称：" + methodName;
                LOGGER.error(msg);
                bizLogger.error(msg);
                return new Result(Action.EXECUTE_FAILED, msg);
            }

            startTime = System.currentTimeMillis();
            method.invoke(bean, new Object[]{extParams});
            long elapsedTime = System.currentTimeMillis() - startTime;

            String msg = "任务执行成功，耗时" + elapsedTime + "ms";
            LOGGER.info(msg);
            bizLogger.info(msg); // 会发送到 LTS (JobTracker上)
            return new Result(Action.EXECUTE_SUCCESS, "任务执行成功，耗时" + elapsedTime + "ms");

        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            String msg = "任务执行成功，耗时" + elapsedTime + "ms";
            LOGGER.error(msg, e);
            bizLogger.info(msg);
            if (e instanceof JobExecuteException) {
                // 执行异常, 会重试
                return new Result(Action.EXECUTE_EXCEPTION, e.getMessage());
            } else {
                //执行失败,这种情况,直接反馈给客户端,不重新执行
                return new Result(Action.EXECUTE_FAILED, e.getMessage());
            }
        }
    }
}
