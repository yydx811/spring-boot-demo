package com.bestjlb.demo.meta;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Entity
@Table(name = "demo")
public class Demo implements Serializable {

    private static final long serialVersionUID = 7182196543495298391L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long createTime;

    public Demo() {
    }

    public Demo(String name) {
        this.name = name;
        this.createTime = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
