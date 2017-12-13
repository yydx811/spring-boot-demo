package com.bestjlb.demo.service.impl;

import com.bestjlb.demo.meta.Demo;
import com.bestjlb.demo.repository.DemoRepository;
import com.bestjlb.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Caching(evict = {
            @CacheEvict(value = "Demo", key = "'Demo'+#result.id", condition = "#result != null && #result.id > 0"),
            @CacheEvict(value = "DemoList", key = "'DemoList'", condition = "#result != null")
    })
    @Override
    public Demo save(Demo demo) {
        return demoRepository.save(demo);
    }

    @Cacheable(value = "Demo", key = "'Demo'+#id", unless = "#result == null || #result.id < 1")
    @Override
    public Demo getById(long id) {
        return demoRepository.findOne(id);
    }

    @Cacheable(value = "DemoList", key = "'DemoList'", unless = "#result == null")
    @Override
    public List<Demo> getAll() {
        return demoRepository.findAllByOrderByCreateTimeDesc();
    }

    @Caching(evict = {
            @CacheEvict(value = "Demo", key = "'Demo'+#id", condition = "#result > 0"),
            @CacheEvict(value = "DemoList", key = "'DemoList'", condition = "#result > 0")
    })
    @Transactional
    @Override
    public int updateName(long id, String name) {
        return demoRepository.updateNameById(id, name);
    }

    @Caching(evict = {
            @CacheEvict(value = "Demo", key = "'Demo'+#id"),
            @CacheEvict(value = "DemoList", key = "'DemoList'")
    })
    @Override
    public void deleteById(long id) {
        demoRepository.delete(id);
    }

    @Override
    public List<Demo> findInSet(Collection<Long> ids) {
        return demoRepository.findInSet(ids);
    }
}
