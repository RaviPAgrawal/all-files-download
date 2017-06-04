package com.file.download.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.file.download.service.BeanService;
import com.file.download.service.SupportService;

@Service
public class BeanServiceImpl implements BeanService {

    @Autowired
    private ApplicationContext applicationContext;

    public <E extends SupportService<T>, T> E get(Class<E> klass, final T filterData) {
        Collection<E> beans = applicationContext.getBeansOfType(klass).values();
        E bean = beans.stream()
                .filter(b -> b.supports(filterData))
                .collect(Collectors.<E>toList())
                .get(0);
        return bean;
    }
}
