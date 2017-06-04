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

import com.file.download.service.impl.HttpFileDownloadServiceImpl;
import com.file.download.tasks.HttpFileDownloadThread;
import com.file.download.utils.BeanNames;

@RunWith(MockitoJUnitRunner.class)
public class HttpFileDownloadServiceImplTest {

    @InjectMocks
    private HttpFileDownloadServiceImpl httpFileDownloadService = new HttpFileDownloadServiceImpl();

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void downloadFile() {
        ThreadPoolTaskExecutor taskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        HttpFileDownloadThread httpFileDownloadThread = new HttpFileDownloadThread();

        when(applicationContext.getBean(BeanNames.taskExecutor)).thenReturn(taskExecutor);
        when(applicationContext.getBean(BeanNames.httpFileDownloadThread)).thenReturn(httpFileDownloadThread);

        doNothing().when(taskExecutor).execute(httpFileDownloadThread);
        String fileName = "http://example.com/file3.txt";
        httpFileDownloadService.downloadFile(fileName);

        assertThat(httpFileDownloadThread.getFileName()).isEqualTo(fileName);
    }
}
