package com.file.download.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.file.download.service.DownloadService;
import com.file.download.service.FailedRecordsService;

public abstract class FileDownloadThread implements Runnable {

    @Autowired
    protected FailedRecordsService failedRecordsService;

    @Autowired
    protected DownloadService downloadService;

    @Value("${maxFileSizeAllowed}")
    protected long maxFileSizeAllowed;

    protected String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
