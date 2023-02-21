package com.harera.hayat.framework.util;

import org.springframework.data.domain.PageRequest;

public class PageableUtils {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static PageRequest of(int pageNumber, int pageSize) {
        return PageRequest.of(getPageNumber(pageNumber), getPageSize(pageSize));
    }

    public static int getPageNumber(int pageNumber) {
        return Math.max(pageNumber, DEFAULT_PAGE_NUMBER);
    }

    public static int getPageSize(int pageSize) {
        return (pageSize > 100) ? 100 : pageSize;
    }
}
