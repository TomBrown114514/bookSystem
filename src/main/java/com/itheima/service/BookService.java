package com.itheima.service;

import com.itheima.domain.Book;
import com.itheima.entity.PageResult;

public interface BookService {
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);
    Book findById(int id);
    Integer borrowBook(Book book);
    PageResult search(Book book, Integer pageNum, Integer pageSize);
}
