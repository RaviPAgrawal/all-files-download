package com.file.download.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.file.download.utils.BeanNames;
import com.file.download.utils.CommonUtils;

@Component(BeanNames.ftpFileDownloadThread)
@Scope("prototype")
public class FtpFileDownloadThread extends FileDownloadThread {

    static final Logger log = LogManager.getLogger(FtpFileDownloadThread.class.getName());

    @Value("${ftpFileDownloadFolder}")
    private String ftpFileDownloadFolder;

    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName()+" Start ftp. File name = " + fileName);

            URL url = new URL(fileName);
            if (CommonUtils.isFileSizeMoreThanMaxAllowed(url, maxFileSizeAllowed)) {
                downloadService.downloadFileToExternalService(fileName);
            } else {
                String targetFilePath = ftpFileDownloadFolder + File.separator + FilenameUtils.getName(url.getPath());
                FileUtils.copyURLToFile(url, new File(targetFilePath));
            }

            log.info(Thread.currentThread().getName()+" End ftp.");
        } catch (IOException e) {
            log.error("Error while downloading file from source {}", fileName, e);
            failedRecordsService.addToFailedRecords(fileName);
        }
    }
}
