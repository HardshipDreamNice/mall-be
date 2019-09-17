package com.torinosrc.commons.utils;

/**
 * Created by lvxin on 2018/5/18.
 */
public class FilePathUtils {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    //public static final String FILE_SEPARATOR = File.separator;

    public static String getRealFilePathgetRealFilePath(String path) {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    public static String getHttpURLPath(String path) {
        return path.replace("\\", "/");
    }
}
