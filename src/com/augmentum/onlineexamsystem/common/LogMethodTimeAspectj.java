package com.augmentum.onlineexamsystem.common;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.augmentum.onlineexamsystem.util.Constants;

public class LogMethodTimeAspectj {
    private final Logger logger = Logger.getLogger(LogMethodTime.class);

    public void doAfter(JoinPoint jp) {
        //System.out.println("log method end : " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object returnValue = pjp.proceed();

        long endTime = System.currentTimeMillis();
        String methodName = pjp.getSignature().getName();

        StringBuffer sb = new StringBuffer();
        sb.append(AppContext.getAppContext().getDataValue(Constants.USER))
        .append(" : ")
        .append(pjp.getTarget().getClass().getSimpleName())
        .append(" : ")
        .append(methodName)
        .append(" : ")
        .append((endTime - startTime));
        logger.info(sb.toString());

        return returnValue;
    }

    public void doBefore(JoinPoint jp) {
        //System.out.println("log method start : " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }
}
