package com.stone.elm.springboot.demo.basictech.common.requset;

public class QueryEntity {
    private Integer pageNo;
    private Integer pageSize;
    private Integer indexStart;
    private Integer indexEnd;
    private Integer pageStart;
    private Integer pageEnd;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIndexStart() {
        return indexStart;
    }

    public void setIndexStart(Integer indexStart) {
        this.indexStart = indexStart;
    }

    public Integer getIndexEnd() {
        return indexEnd;
    }

    public void setIndexEnd(Integer indexEnd) {
        this.indexEnd = indexEnd;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(Integer pageEnd) {
        this.pageEnd = pageEnd;
    }

    @Override
    public String toString() {
        return "QueryEntity{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", indexStart=" + indexStart +
                ", indexEnd=" + indexEnd +
                ", pageStart=" + pageStart +
                ", pageEnd=" + pageEnd +
                '}';
    }
}
