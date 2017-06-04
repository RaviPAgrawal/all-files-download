package com.file.download.service.unit;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.file.download.service.impl.FailedRecordsServiceImpl;
import com.file.download.utils.BeanNames;
import com.file.download.utils.FailedRecords;

@RunWith(MockitoJUnitRunner.class)
public class FailedRecordsServiceImplTest {

    @InjectMocks
    private FailedRecordsServiceImpl failedRecordsService = new FailedRecordsServiceImpl();

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void addToFailedRecords() {
        FailedRecords failedRecords = new FailedRecords();
        when(applicationContext.getBean(BeanNames.failedRecords)).thenReturn(failedRecords);
        failedRecordsService.addToFailedRecords("failed record 1");
        assertThat(failedRecords.getFailedRecords().size()).isEqualTo(1);
    }

}
