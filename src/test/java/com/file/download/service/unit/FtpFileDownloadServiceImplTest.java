package com.file.download.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.file.download.service.impl.FtpFileDownloadServiceImpl;
import com.file.download.tasks.FtpFileDownloadThread;
import com.file.download.utils.BeanNames;

@RunWith(MockitoJUnitRunner.class)
public class FtpFileDownloadServiceImplTest {

    @InjectMocks
    private FtpFileDownloadServiceImpl ftpFileDownloadService = new FtpFileDownloadServiceImpl();

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void downloadFile() {
        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        FtpFileDownloadThread ftpFileDownloadThread = new FtpFileDownloadThread();

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);
        when(applicationContext.getBean(BeanNames.ftpFileDownloadThread)).thenReturn(ftpFileDownloadThread);

        doNothing().when(taskExecutor).execute(ftpFileDownloadThread);
        String fileName = "ftp://example.com/file2.txt";
        ftpFileDownloadService.downloadFile(fileName);

        assertThat(ftpFileDownloadThread.getFileName()).isEqualTo(fileName);
    }
}
