package com.file.download.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.file.download.service.FileDownloadService;
import com.file.download.service.impl.BeanServiceImpl;
import com.file.download.service.impl.FtpFileDownloadServiceImpl;
import com.file.download.service.impl.HttpFileDownloadServiceImpl;
import com.file.download.service.impl.SftpFileDownloadServiceImpl;
import com.file.download.utils.BeanNames;

@RunWith(MockitoJUnitRunner.class)
public class BeanServiceImplTest {

    @InjectMocks
    private BeanServiceImpl beanService = new BeanServiceImpl();

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void getBean() {
        FileDownloadService httpFileDownloadService = new HttpFileDownloadServiceImpl();
        FileDownloadService ftpFileDownloadService = new FtpFileDownloadServiceImpl();
        FileDownloadService sftpFileDownloadService = new SftpFileDownloadServiceImpl();

        Map<String, FileDownloadService> serviceMap = new HashMap<>();
        serviceMap.put("httpFileDownloadService", httpFileDownloadService);
        serviceMap.put("ftpFileDownloadService", ftpFileDownloadService);
        serviceMap.put("sftpFileDownloadService", sftpFileDownloadService);

        when(applicationContext.getBeansOfType(FileDownloadService.class)).thenReturn(serviceMap);

        FileDownloadService fileDownloadService = beanService.get(FileDownloadService.class, "http://example.com/file.txt");
        assertThat(fileDownloadService).isInstanceOf(HttpFileDownloadServiceImpl.class);

        fileDownloadService = beanService.get(FileDownloadService.class, "ftp://example.com/file.txt");
        assertThat(fileDownloadService).isInstanceOf(FtpFileDownloadServiceImpl.class);


        fileDownloadService = beanService.get(FileDownloadService.class, "sftp://example.com/file.txt");
        assertThat(fileDownloadService).isInstanceOf(SftpFileDownloadServiceImpl.class);
    }

}
