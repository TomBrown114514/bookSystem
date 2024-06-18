package com.itheima.controller;

import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/selectNewBooks")
    public ModelAndView selectNewBooks() {
        int pageNum = 1;
        int pageSize = 5;
        PageResult pageResult =
                bookService.selectNewBooks(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books_new");
        modelAndView.addObject("pageResult", pageResult);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Result<Book> findById(String id) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                return new Result<>(false, "查询图书失败");
            }
            return new Result<>(true, "查询图书成功", book);
        } catch (Exception e) {
            return new Result<>(false, "查询图书失败");
        }
    }

    public Result borrowBook(Book book, HttpSession session) {
        String pname = ((User) session.getAttribute("USER_SESSION")).getName();
        book.setBorrower(pname);
        try {
            Integer count = bookService.borrowBook(book);
            if (count != 1) {
                return new Result(false, "借阅图书失败");
            }
            return new Result(true, "借阅成功，请到行政中心取书");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "借阅图书失败");
        }
    }

    @RequestMapping("/search")
    public ModelAndView search(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = bookService.search(book, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("book", book);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/addBook")
    public Result addBook(Book book) {
        try {
            Integer count = bookService.addBook(book);
            if (count != 1) {
                return new Result(false, "添加图书失败");
            }
            return new Result(true, "添加图书成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加图书失败");
        }
    }

    @ResponseBody
    @RequestMapping("/editBook")
    public Result editBook(Book book) {
        try {
            Integer count = bookService.editBook(book);
            if (count != 1) {
                return new Result(false, "修改图书失败");
            }
            return new Result(true, "修改图书成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改图书失败");
        }
    }

    @RequestMapping("/selectBorrowed")
    public ModelAndView selectBorrowed(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        PageResult pageResult = bookService.selectBorrowed(book, user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("books_borrowed");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", book);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/returnBook")
    public Result returnBook(String id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        try {
            boolean flag = bookService.returnBook(id, user);
            if (!flag) {
                return new Result(false, "还书失败");
            }
            return new Result(true, "还书确认中，请先到行政中心还书！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "还书失败");
        }
    }

    @ResponseBody
    @RequestMapping("/returnConfirm")
    public Result returnConfirm(String id) {
        try {
            Integer count = bookService.returnConfirm(id);
            if (count != 1) {
                return new Result(false, "确认失败");
            }
            return new Result(true, "确认成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "确认失败");
        }
    }
}
