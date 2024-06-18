package com.itheima.service;

import com.itheima.entity.PageResult;

public interface BookService {
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);
}
