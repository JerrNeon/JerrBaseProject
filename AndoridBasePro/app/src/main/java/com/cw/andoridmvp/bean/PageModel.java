package com.cw.andoridmvp.bean;

import java.io.Serializable;

/**
 * Created by tang on 2015/12/11.
 * 分页
 *
 */
public class PageModel implements Serializable {
    private int total;//	int	总记录数
    private int pages;//	int	总页数

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
