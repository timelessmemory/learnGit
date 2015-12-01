package com.augmentum.onlineexamsystem.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.augmentum.onlineexamsystem.controller.UserController;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.service.UserService;

public class FileUploadUtils {
    private static Logger logger = Logger.getLogger(FileUploadUtils.class);
    private static final String WEB_INF = "WEB-INF";
    private static final String CLASSES = "classes";
    private static final String STATIC = "static";
    private static final String UPLOAD = "upload";
    private static final String RM_STR = "%20";
    private static final String WHITE_STR = " ";
    private static final String IMAGES = "images/";
    private static final String DOT = ".";

    public static void upload(CommonsMultipartFile file, UserService userService, int userId, UserController userController ) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(DOT));
            String filename = System.currentTimeMillis() + type;
            String classPath = userController.getClass().getClassLoader().getResource(Constants.EMPTY_STR).getPath();
            classPath = classPath.replace(WEB_INF, STATIC);
            classPath = classPath.replace(CLASSES, UPLOAD);
            classPath = classPath.replace(RM_STR, WHITE_STR);
            String picPath = classPath + IMAGES + filename;
            picPath = picPath.substring(1);
            File destFile = new File(picPath);
            String dbPath = Constants.DB_PATH + filename;
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                User originUser = userService.getUserInfo(userId);
                String originPath = originUser.getPic();
                originPath = originPath.substring(originPath.lastIndexOf(Constants.SLASH));
                originPath = classPath + IMAGES + originPath;
                originPath = originPath.substring(1);
                File originFile = new File(originPath);
                originFile.delete();
                userService.changePhoto(userId, dbPath);
                User user = userService.getUserInfo(userId);
                user.setPassword(null);
                SessionUtil.addSessionAttr(Constants.USER, user);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
                SessionUtil.addSessionAttr(Constants.TIP_MESSAGE, Constants.UPLOAD_ERROR);
            }
         }
    }
}
