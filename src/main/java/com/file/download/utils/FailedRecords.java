package com.file.download.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component(BeanNames.failedRecords)
public class FailedRecords {
    private List<String> failedRecords;

    public FailedRecords() {
        failedRecords = new ArrayList<>();
    }

    public void addToFailedRecords(String fileName) {
        this.failedRecords.add(fileName);
    }

    public List<String> getFailedRecords() {
        return failedRecords;
    }
}
