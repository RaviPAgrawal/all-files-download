package com.file.download.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.file.download.service.BeanService;
import com.file.download.service.DownloadService;
import com.file.download.service.FileDownloadService;
import com.file.download.utils.BeanNames;
import com.file.download.utils.FailedRecords;

@Service(BeanNames.downloadService)
public class DownloadServiceImpl implements DownloadService {

    static final Logger log = LogManager.getLogger(DownloadServiceImpl.class.getName());

    @Autowired
    private BeanService beanService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void downloadFiles(List<String> fileNames) {
        log.info("Starting all threads");
        passAllFileNamesToDownload(fileNames);
        tryOnceForFailedRecordsAndShutDownTaskExecutor();
        log.info("Finished all threads");
    }

    @Override
    public void downloadFileToExternalService(String fileName) {
        log.info("Saving the file {} to external resource", fileName);
        //code to save file to external resource
    }

    private void tryOnceForFailedRecordsAndShutDownTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean(BeanNames.taskExecutor);
        boolean isTriedOnceForFailedRecords = Boolean.FALSE;

        while(true) {
            if(taskExecutor.getActiveCount() == 0) {

                //get all the broken operations here and submit them once more before shutting down the taskExecutor
                if (!isTriedOnceForFailedRecords) {
                    isTriedOnceForFailedRecords = Boolean.TRUE;
                    FailedRecords failedRecords = (FailedRecords) applicationContext.getBean(BeanNames.failedRecords);
                    log.info("Processing failed records now");
                    passAllFileNamesToDownload(new ArrayList<>(failedRecords.getFailedRecords()));
                    continue;
                }
                taskExecutor.shutdown();
                break;
            }
        }
    }

    private void passAllFileNamesToDownload(List<String> fileNames) {
        for (String fileName : fileNames) {
            beanService.get(FileDownloadService.class, fileName).downloadFile(fileName);
        }
    }
}
