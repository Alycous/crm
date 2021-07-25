package com.bjpowernode.crm.workbench.vo;

import java.util.List;

public class PaginationVo<T> {

    private Integer total;
    private List<T> datalist;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<T> datalist) {
        this.datalist = datalist;
    }
}
