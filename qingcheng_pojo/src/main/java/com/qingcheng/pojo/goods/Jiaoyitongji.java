package com.qingcheng.pojo.goods;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_jiaoyitongji")
public class Jiaoyitongji implements Serializable {
    @Id
    private Integer id;
    private Integer liulanrenshu;
    private Integer xiadanrenshu;
    private Integer dingdanshu;
    private Integer tuikuanjine;
    private Integer fukuanrenshu;
    private Integer fukuanjine;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLiulanrenshu() {
        return liulanrenshu;
    }

    public void setLiulanrenshu(Integer liulanrenshu) {
        this.liulanrenshu = liulanrenshu;
    }

    public Integer getXiadanrenshu() {
        return xiadanrenshu;
    }

    public void setXiadanrenshu(Integer xiadanrenshu) {
        this.xiadanrenshu = xiadanrenshu;
    }

    public Integer getDingdanshu() {
        return dingdanshu;
    }

    public void setDingdanshu(Integer dingdanshu) {
        this.dingdanshu = dingdanshu;
    }

    public Integer getTuikuanjine() {
        return tuikuanjine;
    }

    public void setTuikuanjine(Integer tuikuanjine) {
        this.tuikuanjine = tuikuanjine;
    }

    public Integer getFukuanrenshu() {
        return fukuanrenshu;
    }

    public void setFukuanrenshu(Integer fukuanrenshu) {
        this.fukuanrenshu = fukuanrenshu;
    }

    public Integer getFukuanjine() {
        return fukuanjine;
    }

    public void setFukuanjine(Integer fukuanjine) {
        this.fukuanjine = fukuanjine;
    }
}
