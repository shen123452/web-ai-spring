package com.hhh.service;

import com.hhh.pojo.Clazz;
import com.hhh.pojo.ClazzQueryParam;
import com.hhh.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    List<Clazz> list();

    Clazz getById(Integer id);

    void add(Clazz clazz);

    void update(Clazz clazz);

    void delete(Integer id);
}
