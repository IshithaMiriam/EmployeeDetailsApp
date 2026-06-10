package com.employee.EmployeeDetails.EmployeeDTO;

public class MetaDataDTO {

    private int page;
    private int size;
    private long total;

    public MetaDataDTO(int page, int size, long total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }
}