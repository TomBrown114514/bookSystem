package com.itheima.service;

import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

/**
 * 图书服务接口，提供关于图书的各类操作，包括查询、借阅、归还等。
 */
public interface BookService {

    /**
     * 查询新书列表。
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果对象，包含新书列表和分页信息
     */
    PageResult selectNewBooks(Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询图书详情。
     *
     * @param id 图书ID
     * @return 图书对象
     */
    Book findById(String id);

    /**
     * 用户借阅图书。
     *
     * @param book 图书对象
     * @return 借阅成功返回1，失败返回0
     */
    Integer borrowBook(Book book);

    /**
     * 搜索图书。
     *
     * @param book 搜索条件的图书对象，包含书名、作者等信息
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果对象，包含搜索结果列表和分页信息
     */
    PageResult search(Book book, Integer pageNum, Integer pageSize);

    /**
     * 添加图书。
     *
     * @param book 待添加的图书对象
     * @return 添加成功返回1，失败返回0
     */
    Integer addBook(Book book);

    /**
     * 修改图书信息。
     *
     * @param book 待修改的图书对象
     * @return 修改成功返回1，失败返回0
     */
    Integer editBook(Book book);

    /**
     * 查询用户借阅的图书列表。
     *
     * @param book 搜索条件的图书对象，用于筛选特定类型的图书
     * @param user 用户对象，用于查询特定用户的借阅情况
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果对象，包含用户借阅的图书列表和分页信息
     */
    PageResult selectBorrowed(Book book, User user, Integer pageNum, Integer pageSize);

    /**
     * 用户归还图书。
     *
     * @param id 图书ID
     * @param user 用户对象，用于记录归还操作的用户信息
     * @return 归还成功返回true，失败返回false
     */
    boolean returnBook(String id, User user);

    /**
     * 确认图书归还。
     *
     * @param id 图书ID
     * @return 确认归还成功返回1，失败返回0
     */
    Integer returnConfirm(String id);
}

