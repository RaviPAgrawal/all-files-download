package com.file.download.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CommonUtils {

    public static boolean isFileSizeMoreThanMaxAllowed(URL url, long maxFileSizeAllowed) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        long fileSize = urlConnection.getContentLengthLong();
        return fileSize > maxFileSizeAllowed;
    }
}
