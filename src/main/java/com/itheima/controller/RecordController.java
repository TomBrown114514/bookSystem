package com.itheima.controller;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录控制器，负责处理与记录相关的请求。
 */
@Controller
@RequestMapping("/record")
public class RecordController {

    /**
     * 自动注入记录服务，用于执行与记录相关的业务操作。
     */
    @Autowired
    private RecordService recordService;

    /**
     * 查询记录并返回搜索结果页面。
     *
     * @param record   搜索条件对象，包含可能的查询条件。
     * @param request  HTTP请求对象，用于获取会话信息和请求URI。
     * @param pageNum  当前页码，用于分页查询。
     * @param pageSize 每页记录数，用于分页查询。
     * @return 返回包含搜索结果和页面信息的ModelAndView对象。
     */
    private ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize) {
        // 如果页码或每页记录数未指定，则使用默认值
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        // 从会话中获取用户信息
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        // 执行搜索操作，获取分页结果
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        // 创建ModelAndView对象，设置返回页面和模型数据
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("record");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("record", record);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }
}
