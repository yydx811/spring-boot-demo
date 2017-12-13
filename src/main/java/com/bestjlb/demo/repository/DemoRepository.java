package com.bestjlb.demo.repository;

import com.bestjlb.demo.meta.Demo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yydx811 on 2017/10/26.
 */
public interface DemoRepository extends CrudRepository<Demo, Long>, DemoRepositoryCustom {

    List<Demo> findAllByOrderByCreateTimeDesc();

    @Modifying
    @Query("update Demo d set d.name = ?2 where d.id = ?1")
    int updateNameById(long id, String name);
}
