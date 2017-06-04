package com.file.download.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.file.download.service.FileDownloadService;
import com.file.download.tasks.FileDownloadThread;
import com.file.download.tasks.SftpFileDownloadThread;
import com.file.download.utils.BeanNames;

@Service
public class SftpFileDownloadServiceImpl implements FileDownloadService {

    public static final String SFTP = "sftp";

    @Autowired
    private ApplicationContext applicationContext;

    public boolean supports(String fileName) {
        return fileName.startsWith(SFTP);
    }

    public void downloadFile(String fileName) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(BeanNames.taskExecutor);
        FileDownloadThread fileDownloadThread = (SftpFileDownloadThread) applicationContext.getBean(BeanNames.sftpFileDownloadThread);
        fileDownloadThread.setFileName(fileName);
        taskExecutor.execute(fileDownloadThread);
    }
}
