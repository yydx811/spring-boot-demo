package com.bestjlb.demo.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by yydx811 on 2017/10/26.
 */
public class DemoVO {

    private long id;

    @NotNull
    @Size(max = 10)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
