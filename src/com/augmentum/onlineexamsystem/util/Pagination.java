package com.augmentum.onlineexamsystem.util;

import org.apache.log4j.Logger;

public class Pagination {

    private int totalPage;
    private int currentPage;
    private int pageSize = 15;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int totalCount;
    private int offSet;

    public int getTotalPage() {
        this.totalPage = (totalCount-1)/pageSize+1;
        return totalPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage <1) {
            this.currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getOffSet() {
        this.offSet = (currentPage - 1) * pageSize;
        return this.offSet;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static void pageconvert(String currentPage, Pagination pagination, Logger logger, String perPage) {
        if (StringUtils.isEmpty(currentPage)) {
            pagination.setCurrentPage(1);
        } else {
            try {
                int curPage = Integer.valueOf(currentPage);
                if (curPage < 0) {
                    curPage = Constants.LEAST_SIZE;
                }
                pagination.setCurrentPage(curPage);
            } catch (NumberFormatException e) {
                logger.error(e);
                pagination.setCurrentPage(Constants.LEAST_SIZE);
            }
        }

        if (StringUtils.isEmpty(perPage)) {
            pagination.setPageSize(Constants.PAGE_SIZE);
        } else if (!perPage.equals(Constants.PAGESIZE1) && !perPage.equals(Constants.PAGESIZE2) && !perPage.equals(Constants.PAGESIZE3)) {
            pagination.setPageSize(Constants.PAGE_SIZE);
        } else {
            pagination.setPageSize(Integer.valueOf(perPage));
        }
    }
}
