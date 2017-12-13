package com.bestjlb.demo.repository;

import com.bestjlb.demo.meta.Demo;

import java.util.Collection;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
public interface DemoRepositoryCustom {

    List<Demo> findInSet(Collection<Long> ids);
}
