package com.augmentum.onlineexamsystem.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.augmentum.onlineexamsystem.common.AppContext;

public class SessionUtil {
    private static Logger logger = Logger.getLogger(SessionUtil.class);

    private static Object getSessionInThread() {
        Object session = AppContext.getAppContext().getDataValue(Constants.SESSION);
        return session;
    }

    public static void addSessionAttr(String key, Object value) {
        Object session = getSessionInThread();
        if (session == null) {
            return;
        }

        try {
            Class<?>[] param = new Class[2];
            param[0] = String.class;
            param[1] = Object.class;

            Method method = session.getClass().getMethod("setAttribute", param);
            Object[] objects = new Object[2];
            objects[0] = key;
            objects[1] = value;
            method.invoke(session, objects);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static Object getSessionAttr(String key) {
        Object session = getSessionInThread();
        if (session == null) {
            return null;
        }

        try {
            Class<?>[] param = new Class[1];
            param[0] = String.class;

            Method method = session.getClass().getMethod("getAttribute", param);
            Object[] objects = new Object[1];
            objects[0] = key;
            return method.invoke(session, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeSessionAttr(String key) {
        Object session = getSessionInThread();
        if (session == null) {
            return;
        }

        try {
            Class<?>[] param = new Class[1];
            param[0] = String.class;

            Method method = session.getClass().getMethod("removeAttribute", param);
            Object[] objects = new Object[1];
            objects[0] = key;
            method.invoke(session, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
