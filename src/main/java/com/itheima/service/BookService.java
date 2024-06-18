package com.itheima.service;

import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

public interface BookService {
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);

    Book findById(int id);

    Integer borrowBook(Book book);

    PageResult search(Book book, Integer pageNum, Integer pageSize);

    Integer addBook(Book book);

    Integer editBook(Book book);

    PageResult selectBorrowed(Book book, User user, Integer pageNum, Integer pageSize);
}
