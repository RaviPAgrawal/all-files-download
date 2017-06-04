package com.file.download.service.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.file.download.service.DownloadService;

public class DownloadServiceImplIT extends BaseIntegration {

    @Autowired
    DownloadService downloadService;

    @Value("${httpFileDownloadFolder}")
    private String httpFileDownloadFolder;

    @Value("${ftpFileDownloadFolder}")
    private String ftpFileDownloadFolder;

    @Value("${sftpFileDownloadFolder}")
    private String sftpFileDownloadFolder;

    @Test
    public void downloadFiles() {

        List<String> fileNames = new ArrayList<>();
        fileNames.add("http://test.talia.net/dl/5mb.pak");
        fileNames.add("http://test.talia.net/dl/1mb.pak");
        fileNames.add("ftp://speedtest.tele2.net/1MB.zip");
        fileNames.add("ftp://speedtest.tele2.net/1KB.zip");
        fileNames.add("sftp://demo@test.rebex.net:22/readme.txt");

        downloadService.downloadFiles(fileNames);

        List<String> httpFileNames = new ArrayList<>();
        httpFileNames.add("5mb.pak");
        httpFileNames.add("1mb.pak");

        List<String> ftpFileNames = new ArrayList<>();
        ftpFileNames.add("1MB.zip");
        ftpFileNames.add("1KB.zip");

        List<String> sftpFileNames = new ArrayList<>();
        sftpFileNames.add("readme.txt");

        checkIfFilesArePresentInTargetFolder(new File(httpFileDownloadFolder).listFiles(), httpFileNames);
        checkIfFilesArePresentInTargetFolder(new File(ftpFileDownloadFolder).listFiles(), ftpFileNames);
        checkIfFilesArePresentInTargetFolder(new File(sftpFileDownloadFolder).listFiles(), sftpFileNames);

    }

    public static void checkIfFilesArePresentInTargetFolder(File[] folderFiles, List<String> targetFolderFiles) {
        for (File file : folderFiles) {
            if (!file.isHidden()) {
                assertThat(targetFolderFiles.contains(file.getName())).isTrue();
            }
        }
    }

}
