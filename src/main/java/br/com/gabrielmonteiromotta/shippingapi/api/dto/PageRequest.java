package br.com.gabrielmonteiromotta.shippingapi.api.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageRequest implements Pageable {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int page;
    private int size;
    private String orderBy;
    private String orderDirection;

    public PageRequest() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_PAGE_SIZE;
        this.orderBy = "id";
        this.orderDirection = "ASC";
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public Sort getSort() {
        return orderBy == null ? Sort.unsorted() :
            Sort.by(Sort.Direction.fromString(this.orderDirection), this.orderBy);
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
