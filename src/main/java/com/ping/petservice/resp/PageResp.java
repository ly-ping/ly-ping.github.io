package com.ping.petservice.resp;

import java.util.List;

/**
 * 根据req获取的参数值响应得该文件定义的参数再返回给前端
 * @Author：Ping
 * @Date：2023/4/6
 **/
public class PageResp<T> {
//  总页数
    private long total;
//  数据
    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
