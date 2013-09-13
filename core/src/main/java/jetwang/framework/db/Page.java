package jetwang.framework.db;


import jetwang.framework.util.StringUtils;

import java.io.Serializable;


public class Page implements Serializable {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private int pageSize;
    private int pageNumber = DEFAULT_PAGE_NUMBER;
    private int rowCount;
    private String orderField;
    private boolean asc;
    private boolean calculateRowCount = true;

    public Page() {
        this(DEFAULT_PAGE_SIZE);
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(int pageSize, boolean calculateRowCount) {
        this.pageSize = pageSize;
        this.calculateRowCount = calculateRowCount;
    }

    public Page(int pageSize, String orderField, boolean asc) {
        this.pageSize = pageSize;
        this.orderField = orderField;
        this.asc = asc;
    }

    public boolean hasOrder() {
        return StringUtils.hasText(orderField);
    }

    public void order(String field, boolean asc) {
        this.orderField = field;
        this.asc = asc;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        if (calculateRowCount && pageNumber > getPageCount()) {
            pageNumber = getPageCount();
        } else if (pageNumber <= 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
    }

    public int getStartRowNumber() {
        return (pageNumber - 1) * pageSize;
    }

    public int getPageCount() {
        if (rowCount > 0L)
            return rowCount % pageSize <= 0 ? rowCount / pageSize : rowCount / pageSize + 1;
        else
            return 0;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean isFirstPage() {
        return pageNumber <= DEFAULT_PAGE_NUMBER;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public boolean isCalculateRowCount() {
        return calculateRowCount;
    }

    public void setCalculateRowCount(boolean calculateRowCount) {
        this.calculateRowCount = calculateRowCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "size=" + pageSize +
                ", number=" + pageNumber +
                ", rows=" + rowCount +
                ", order by " + orderField +
                ", asc=" + asc +
                '}';
    }
}
