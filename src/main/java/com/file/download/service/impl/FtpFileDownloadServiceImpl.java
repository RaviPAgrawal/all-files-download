package com.file.download.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.file.download.service.FileDownloadService;
import com.file.download.tasks.FileDownloadThread;
import com.file.download.tasks.FtpFileDownloadThread;
import com.file.download.utils.BeanNames;

@Service
public class FtpFileDownloadServiceImpl implements FileDownloadService {

    public static final String FTP = "ftp";

    @Autowired
    private ApplicationContext applicationContext;

    public boolean supports(String fileName) {
        return fileName.startsWith(FTP);
    }

    public void downloadFile(String fileName) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(BeanNames.taskExecutor);
        FileDownloadThread fileDownloadThread = (FtpFileDownloadThread) applicationContext.getBean(BeanNames.ftpFileDownloadThread);
        fileDownloadThread.setFileName(fileName);
        taskExecutor.execute(fileDownloadThread);
    }
}
