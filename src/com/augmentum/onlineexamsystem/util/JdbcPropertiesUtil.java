package com.augmentum.onlineexamsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcPropertiesUtil {

    private static Properties properties = null;
    private static final String FILE_NAME = "jdbc.properties";

    static{
        InputStream is = null;
        try {
            is = JdbcPropertiesUtil.class.getClassLoader().getResourceAsStream(FILE_NAME);
             properties = new Properties();
             properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getStaticUrl() {
        return properties.getProperty("static_url");
    }
}
