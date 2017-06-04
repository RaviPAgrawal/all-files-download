package com.file.download.service;

public interface BeanService {

    <E extends SupportService<T>, T> E get(Class<E> klass, T filterData);
}
