package com.file.download.service;

public interface FileDownloadService extends SupportService<String> {

    void downloadFile(String fileName);
}
