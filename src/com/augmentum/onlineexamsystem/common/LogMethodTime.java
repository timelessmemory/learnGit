package com.augmentum.onlineexamsystem.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.augmentum.onlineexamsystem.util.Constants;

public class LogMethodTime implements MethodInterceptor {
    private final Logger logger = Logger.getLogger(LogMethodTime.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object returnValue = methodInvocation.proceed();

        long endTime = System.currentTimeMillis();
        String methodName = methodInvocation.getMethod().getName();

        StringBuffer sb = new StringBuffer();
        sb.append(AppContext.getAppContext().getDataValue(Constants.USER))
        .append(" : ")
        .append(methodInvocation.getMethod().getDeclaringClass().getSimpleName())
        .append(" : ")
        .append(methodName)
        .append(" : ")
        .append((endTime - startTime));
        logger.info(sb.toString());

        return returnValue;
    }

}
