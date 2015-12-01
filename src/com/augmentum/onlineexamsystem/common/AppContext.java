package com.augmentum.onlineexamsystem.common;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private static ThreadLocal<AppContext> appContextMap = new ThreadLocal<AppContext>();

    private Map<String, Object> data = new HashMap<String, Object>();

    private static String contextPath;

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        if (AppContext.contextPath == null) {
            AppContext.contextPath = contextPath;
        }
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        this.data = data;
    }

    public void addDataKeyValue(String key, Object object) {
        data.put(key, object);
    }

    public Object getDataValue(String key) {
        return data.get(key);
    }

    public void clearData() {
        data.clear();
    }

    public void removeData(String key) {
        data.remove(key);
    }


    public static AppContext getAppContext() {
        AppContext appContext = appContextMap.get();
        if ( appContext == null) {
            appContext = new AppContext();
            appContextMap.set(appContext);
        }
        return appContext;
    }
}
