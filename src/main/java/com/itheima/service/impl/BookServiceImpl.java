package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.BookMapper;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Book findById(String id) {
        return bookMapper.findById(id);
    }

    @Override
    public Integer borrowBook(Book book) {
        Book b = bookMapper.findById(String.valueOf(book.getId()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        book.setBorrowTime(dateFormat.format(new Date()));
        book.setStatus("1");
        book.setPrice(b.getPrice());
        book.setUploadTime(b.getUploadTime());
        return bookMapper.editBook(book);
    }

    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        book.setUploadTime(dateFormat.format(new Date()));
        return bookMapper.addBook(book);
    }

    @Override
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }

    @Override
    public PageResult selectBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page;
        book.setBorrower(user.getName());
        if ("ADMIN".equals(user.getRole())) {
            page = bookMapper.selectBorrowed(book);
        } else {
            page = bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public boolean returnBook(String id, User user) {
        Book book = this.findById(id);
        boolean rb = book.getBorrower().equals(user.getName());
        if (rb) {
            book.setStatus("2");
            bookMapper.editBook(book);
        }
        return rb;
    }
}
