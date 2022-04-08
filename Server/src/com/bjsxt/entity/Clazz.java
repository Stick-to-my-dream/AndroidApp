package com.bjsxt.entity;

import java.io.Serializable;
import java.util.List;

public class Clazz implements Serializable {
    private Integer clazzno;
    private String cname;

    private List<Student> list;

    public Clazz() {
    }

    public Clazz(Integer clazzno, String cname) {
        this.clazzno = clazzno;
        this.cname = cname;
    }

    public Integer getClazzno() {
        return clazzno;
    }

    public void setClazzno(Integer clazzno) {
        this.clazzno = clazzno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "clazzno=" + clazzno +
                ", cname='" + cname + '\'' +
                ", list=" + list +
                '}';
    }
}
