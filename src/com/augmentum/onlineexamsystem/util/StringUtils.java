package com.augmentum.onlineexamsystem.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtils {
    public static final String FORMAL_EXAM = "The formal papers";
    public static final String DRAFT_EXAM = "The draft papers";

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str.trim()));
    }

    public static String htmlEncode(String text) {
        if (text != null) {
            text = text.replace("<", "&lt;");
            text = text.replace(">", "&gt;");
            text = text.replace("\"", "&quot;");
            text = text.replace("&", "&amp;");

            text = text.replace("&amp;amp;", "&amp;");
            text = text.replace("&amp;quot;", "&quot;");
            text = text.replace("&amp;lt;", "&lt;");
            text = text.replace("&amp;gt;", "&gt;");
            text = text.replace("&amp;nbsp;", "&nbsp;");
        }
        return text;
    }

    public static int[] toIntArray(String[] strArray) {
        int[] intArray = new int[strArray.length];
        if (strArray != null && strArray.length > 0) {
            for (int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.valueOf(strArray[i]);
            }
        }
        return intArray;
    }

    public static String toNotNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String dateConvertToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.format(date);
    }

    public static String examDraft(int isDraft) {
        if (isDraft == 0) {
            return FORMAL_EXAM;
        } else {
            return DRAFT_EXAM;
        }
    }

    public static String convertCoding(String orgin) throws UnsupportedEncodingException {
        if (!isEmpty(orgin)) {
            orgin = new String(orgin.getBytes(Constants.ISO), Constants.UTF);
        }
        return orgin;
    }
}
