package com.bestjlb.demo.repository.impl;

import com.bestjlb.demo.meta.Demo;
import com.bestjlb.demo.repository.DemoRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
public class DemoRepositoryImpl implements DemoRepositoryCustom {

    private final EntityManager em;

    @Autowired
    public DemoRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Demo.class);
    }

    @Override
    public List<Demo> findInSet(Collection<Long> ids) {
        StringBuilder builder = new StringBuilder(127);
        ids.stream().forEach(id -> {
            builder.append(id).append(",");
        });
        builder.deleteCharAt(builder.length() - 1);
        Query query = em.createNativeQuery("SELECT * FROM jlb_demo WHERE id IN ("
                + builder.toString() + ") ORDER BY FIND_IN_SET(id, '" + builder.toString() + "')", Demo.class);
        return query.getResultList();
    }
}
