package com.distribute.job.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author renwq
 * @date 2021/5/20 14:20
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static boolean deleteRecursively(File root) {
        if (null != root && root.exists()) {
            if (root.isDirectory()) {
                File[] childFiles = root.listFiles();
                if (null != childFiles) {
                    for (File file: childFiles) {
                        deleteRecursively(file);
                    }
                }
            } else {
                root.delete();
            }
        }
        return false;
    }

    public static void deleteFile(String fileName) {
        if (null != fileName && fileName.trim().length() != 0) {
            File file = new File(fileName);
            if (null != file) {
                file.delete();
            }
        }
    }

    public static void writeFileContent(File file, byte[] data) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    public static byte[] readFileContent(File file) {
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(fileContent);
            fis.close();
            return fileContent;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}
