package com.bestjlb.demo.service;

import com.bestjlb.demo.meta.Demo;

import java.util.Collection;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
public interface DemoService {

    Demo save(Demo demo);

    Demo getById(long id);

    List<Demo> getAll();

    int updateName(long id, String name);

    void deleteById(long id);

    List<Demo> findInSet(Collection<Long> ids);
}
