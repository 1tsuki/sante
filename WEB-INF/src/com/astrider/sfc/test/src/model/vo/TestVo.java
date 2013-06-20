package com.astrider.sfc.test.src.model.vo;


import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;

public class TestVo extends BaseVo {
    private static final long serialVersionUID = -9085422334962612284L;
    @Column(physic="a", logic="a")
    private String a;
    @Column(physic="B", logic="b")
    private String b;
    @Column(physic="c_boolean", logic="c")
    private boolean c;
    @Column(physic="d-Boolean", logic="d")
    private Boolean d;
    @Column(physic="e!", logic="e")
    private int e;
    @Column(physic="f123", logic="f")
    private Integer f;
    @Column(physic="5", logic="g")
    @Valid(isNotBlank = true)
    private String g;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public Boolean getD() {
        return d;
    }

    public void setD(Boolean d) {
        this.d = d;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

}
