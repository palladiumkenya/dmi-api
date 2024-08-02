package com.kenyahmis.dmiapi.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageData<T> extends PageImpl<T> {
    List<T> data;

    public PageData(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public List<T> getData() {
        return super.getContent();
    }

}
