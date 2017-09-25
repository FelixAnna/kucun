package com.xiangyong.manager.core.util;

import com.xiangyong.manager.core.exception.ApplicationException;
import com.xiangyong.manager.core.exception.ResponseCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 业务异常工具类,语法类似junit断言语法
 */
public class AssertUtils {

    private AssertUtils() {

    }

    /**
     * 断定目标值为false.为true则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ApplicationException
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为true.为false则抛出业务异常
     *
     * @param expression
     * @param message
     * @throws ApplicationException
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为null.不为null则抛出业务异常
     *
     * @param obj
     * @param message
     * @throws ApplicationException
     */
    public static void isNull(Object obj, String message) {
        if (obj != null) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值不为null.为null则抛出业务异常
     *
     * @param obj
     * @param message
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标list不为空.为空则抛出业务异常
     *
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标list为空.不为空则抛出业务异常
     *
     * @param collection
     * @param message
     */
    public static void isEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标字符串不为空.为空则抛出业务异常
     *
     * @param string
     * @param message
     */
    public static void notEmpty(String string, String message) {
        if (StringUtils.isEmpty(string)) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为不为null.为null则抛出业务异常
     *
     * @param integer
     * @param message
     * @throws ApplicationException
     */
    public static void notNull(Integer integer, String message) {
        if (integer == null) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为不为null并且不等与0.为null则抛出业务异常
     *
     * @param integer
     * @param message
     * @throws ApplicationException
     */
    public static void notEmpty(Integer integer, String message) {
        if (integer == null || integer == 0) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为不为null.为null则抛出业务异常
     *
     * @param pLong
     * @param message
     * @throws ApplicationException
     */
    public static void notNull(Long pLong, String message) {
        if (pLong == null) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 断定目标值为不为null并且不等与0.为null则抛出业务异常
     *
     * @param pLong
     * @param message
     * @throws ApplicationException
     */
    public static void notEmpty(Long pLong, String message) {
        if (pLong == null || pLong == 0) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }


    /**
     * 断定目标字符串为空.不为空则抛出业务异常
     *
     * @param string
     * @param message
     */
    public static void empty(String string, String message) {
        if (StringUtils.isNotBlank(string)) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }


    /**
     * 断定目标字符串不为空.为空则抛出业务异常
     *
     * @param string
     * @param message
     */
    public static void NotEmpty(String string, String message) {
        if (StringUtils.isBlank(string)) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

    /**
     * 根据sql返回结果断定新增,修改,删除执行成功
     *
     * @param result
     * @param message
     */
    public static void executeSuccess(int result, String message) {
        if (result <= 0) {
            throw new ApplicationException(new ResponseCode(ResponseCode.INVALID_PARAM, message));
        }
    }

}
