package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.BookMapper;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图书业务逻辑实现类
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RecordService recordService;

    /**
     * 查询新书列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID查询图书详情
     *
     * @param id 图书ID
     * @return 图书对象
     */
    @Override
    public Book findById(String id) {
        return bookMapper.findById(id);
    }

    /**
     * 借书操作
     *
     * @param book 图书对象
     * @return 更新后的图书数量
     */
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

    /**
     * 搜索图书
     *
     * @param book     搜索条件
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 添加图书
     *
     * @param book 图书对象
     * @return 添加的图书数量
     */
    @Override
    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        book.setUploadTime(dateFormat.format(new Date()));
        return bookMapper.addBook(book);
    }

    /**
     * 更新图书信息
     *
     * @param book 图书对象
     * @return 更新的图书数量
     */
    @Override
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }

    /**
     * 查询借阅记录
     *
     * @param book   图书对象
     * @param user   用户对象
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @return 分页结果
     */
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

    /**
     * 还书操作
     *
     * @param id     图书ID
     * @param user   用户对象
     * @return 是否成功还书
     */
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

    /**
     * 确认还书并记录借阅历史
     *
     * @param id 图书ID
     * @return 添加的借阅记录数量
     */
    @Override
    public Integer returnConfirm(String id) {
        Book book = this.findById(id);
        Record record = this.setRecord(book);
        book.setStatus("0");
        book.setBorrower("");
        book.setBorrowTime("");
        book.setReturnTime("");
        Integer count = bookMapper.editBook(book);
        if (count == 1) {
            return recordService.addRecord(record);
        }
        return 0;
    }

    /**
     * 设置借阅记录信息
     *
     * @param book 图书对象
     * @return 借阅记录对象
     */
    private Record setRecord(Book book) {
        Record record = new Record();
        record.setBookname(book.getName());
        record.setBookisbn(book.getIsbn());
        record.setBorrower(book.getBorrower());
        record.setBorrowTime(book.getBorrowTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        record.setRemandTime(dateFormat.format(new Date()));
        return record;
    }
}

