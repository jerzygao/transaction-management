package priv.gaozhe.transactionmanagement.model;

import java.util.List;

public class PageResult<T> {
    private final List<T> data;
    private final int total;

    public PageResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    // Getters
    public List<T> getData() { 
        return data; 
    }
    
    public int getTotal() { 
        return total; 
    }
}