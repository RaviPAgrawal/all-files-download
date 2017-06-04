package com.file.download.service;

import java.util.List;

public interface DownloadService {

    void downloadFiles(List<String> fileNames);

    void downloadFileToExternalService(String fileName);
}
