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

import com.file.download.service.impl.SftpFileDownloadServiceImpl;
import com.file.download.tasks.SftpFileDownloadThread;
import com.file.download.utils.BeanNames;

@RunWith(MockitoJUnitRunner.class)
public class SftpFileDownloadServiceImplTest {

    @InjectMocks
    private SftpFileDownloadServiceImpl sftpFileDownloadService = new SftpFileDownloadServiceImpl();

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void downloadFile() {
        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        SftpFileDownloadThread sftpFileDownloadThread = new SftpFileDownloadThread();

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);
        when(applicationContext.getBean(BeanNames.sftpFileDownloadThread)).thenReturn(sftpFileDownloadThread);

        doNothing().when(taskExecutor).execute(sftpFileDownloadThread);
        String fileName = "sftp://example.com/file3.txt";
        sftpFileDownloadService.downloadFile(fileName);

        assertThat(sftpFileDownloadThread.getFileName()).isEqualTo(fileName);
    }
}
