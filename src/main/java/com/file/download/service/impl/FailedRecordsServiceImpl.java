package com.file.download.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.file.download.service.FailedRecordsService;
import com.file.download.utils.BeanNames;
import com.file.download.utils.FailedRecords;

@Service(BeanNames.failedRecordsService)
public class FailedRecordsServiceImpl implements FailedRecordsService {

    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    public void addToFailedRecords(String failedRecord) {
        FailedRecords failedRecords = (FailedRecords) applicationContext.getBean(BeanNames.failedRecords);
        failedRecords.addToFailedRecords(failedRecord);
    }
}
