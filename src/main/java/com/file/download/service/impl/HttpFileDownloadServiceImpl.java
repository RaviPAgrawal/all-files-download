package com.file.download.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.file.download.service.FileDownloadService;
import com.file.download.tasks.FileDownloadThread;
import com.file.download.tasks.HttpFileDownloadThread;
import com.file.download.utils.BeanNames;

@Service
public class HttpFileDownloadServiceImpl implements FileDownloadService {

    public static final String HTTP = "http";

    @Autowired
    private ApplicationContext applicationContext;

    public boolean supports(String fileName) {
        return fileName.startsWith(HTTP);
    }

    public void downloadFile(String fileName) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(BeanNames.taskExecutor);
        FileDownloadThread fileDownloadThread = (HttpFileDownloadThread) applicationContext.getBean(BeanNames.httpFileDownloadThread);
        fileDownloadThread.setFileName(fileName);
        taskExecutor.execute(fileDownloadThread);
    }
}
