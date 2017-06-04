package com.file.download.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.file.download.service.DownloadService;
import com.file.download.utils.BeanNames;

public class Application {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        List<String> fileNames = new ArrayList<>();
        fileNames.add("http://test.talia.net/dl/5mb.pak");
        fileNames.add("http://test.talia.net/dl/1mb.pak");
        fileNames.add("ftp://speedtest.tele2.net/1MB.zip");
        fileNames.add("ftp://speedtest.tele2.net/1KB.zip");
        fileNames.add("sftp://demo@test.rebex.net:22/readme.txt");

        DownloadService downloadService = applicationContext.getBean(BeanNames.downloadService, DownloadService.class);
        downloadService.downloadFiles(fileNames);
    }
}
