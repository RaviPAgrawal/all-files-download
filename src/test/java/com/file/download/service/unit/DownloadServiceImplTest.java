package com.file.download.service.unit;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.file.download.service.BeanService;
import com.file.download.service.FileDownloadService;
import com.file.download.service.impl.DownloadServiceImpl;
import com.file.download.service.impl.FtpFileDownloadServiceImpl;
import com.file.download.service.impl.HttpFileDownloadServiceImpl;
import com.file.download.service.impl.SftpFileDownloadServiceImpl;
import com.file.download.utils.BeanNames;
import com.file.download.utils.FailedRecords;

@RunWith(MockitoJUnitRunner.class)
public class DownloadServiceImplTest {

    @InjectMocks
    private DownloadServiceImpl downloadService = new DownloadServiceImpl();

    @Mock
    BeanService beanService;

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void downloadHttpFiles() {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("http://example.com/file1.txt");

        HttpFileDownloadServiceImpl httpFileDownloadService = Mockito.mock(HttpFileDownloadServiceImpl.class);
        doNothing().when(httpFileDownloadService).downloadFile(anyString());
        when(beanService.get(eq(FileDownloadService.class), anyString())).thenReturn(httpFileDownloadService);

        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        when(taskExecutor.getActiveCount()).thenReturn(0);
        doNothing().when(taskExecutor).shutdown();
        FailedRecords failedRecords = new FailedRecords();
        when(applicationContext.getBean(BeanNames.failedRecords)).thenReturn(failedRecords);

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);

        downloadService.downloadFiles(fileNames);
        verify(beanService, times(1)).get(eq(FileDownloadService.class), anyString());
    }

    @Test
    public void downloadFtpFiles() {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("ftp://example.com/file1.txt");

        FtpFileDownloadServiceImpl ftpFileDownloadService = Mockito.mock(FtpFileDownloadServiceImpl.class);
        doNothing().when(ftpFileDownloadService).downloadFile(anyString());
        when(beanService.get(eq(FileDownloadService.class), anyString())).thenReturn(ftpFileDownloadService);

        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        when(taskExecutor.getActiveCount()).thenReturn(0);
        doNothing().when(taskExecutor).shutdown();
        FailedRecords failedRecords = new FailedRecords();
        when(applicationContext.getBean(BeanNames.failedRecords)).thenReturn(failedRecords);

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);

        downloadService.downloadFiles(fileNames);
        verify(beanService, times(1)).get(eq(FileDownloadService.class), anyString());
    }

    @Test
    public void downloadSftpFiles() {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("sftp://example.com/file1.txt");

        SftpFileDownloadServiceImpl sftpFileDownloadService = Mockito.mock(SftpFileDownloadServiceImpl.class);
        doNothing().when(sftpFileDownloadService).downloadFile(anyString());
        when(beanService.get(eq(FileDownloadService.class), anyString())).thenReturn(sftpFileDownloadService);

        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        when(taskExecutor.getActiveCount()).thenReturn(0);
        doNothing().when(taskExecutor).shutdown();
        FailedRecords failedRecords = new FailedRecords();
        when(applicationContext.getBean(BeanNames.failedRecords)).thenReturn(failedRecords);

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);

        downloadService.downloadFiles(fileNames);
        verify(beanService, times(1)).get(eq(FileDownloadService.class), anyString());
    }

    @Test
    public void downloadHttpFilesWithFailedRecords() {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("http://example.com/file1.txt");

        HttpFileDownloadServiceImpl httpFileDownloadService = Mockito.mock(HttpFileDownloadServiceImpl.class);
        doNothing().when(httpFileDownloadService).downloadFile(anyString());
        when(beanService.get(eq(FileDownloadService.class), anyString())).thenReturn(httpFileDownloadService);

        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        when(taskExecutor.getActiveCount()).thenReturn(0);
        doNothing().when(taskExecutor).shutdown();
        FailedRecords failedRecords = new FailedRecords();
        failedRecords.addToFailedRecords("http://example.com/file1.txt");
        when(applicationContext.getBean(BeanNames.failedRecords)).thenReturn(failedRecords);

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);

        downloadService.downloadFiles(fileNames);
        verify(beanService, times(2)).get(eq(FileDownloadService.class), anyString());
    }
}
