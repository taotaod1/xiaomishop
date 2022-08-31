package com.bt.entity;

import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:41
 **/
public class PageBean<T> {
    private int pageNum;
    private int pageSize;
//    总数据
    private long totalSize;
//    总页数
    private int pageCount;
//    每页数据
    private List<T> data;
//    开始页码
    private int startPage;
//    结束页码
    private int endPage;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public PageBean(int pageNum, int pageSize, long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;
//        计算总页数
        this.pageCount=totalSize%pageSize==0?(int)totalSize/pageSize:(int)totalSize/pageSize+1;
//        计算开始页码
        this.startPage=this.pageNum-4;
        this.endPage=this.pageNum+5;
//        最后
        if(this.pageNum>this.pageCount-5){
            this.startPage=this.pageCount-9;
            this.endPage=this.pageCount;
        }
//        开始
        if(this.pageNum<5){
            this.startPage=1;
            this.endPage=10;
        }
//        总页数小于10页码
        if(this.pageCount<10){
            this.startPage=1;
            this.endPage=this.pageCount;
        }
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", pageCount=" + pageCount +
                ", data=" + data +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                '}';
    }
}
