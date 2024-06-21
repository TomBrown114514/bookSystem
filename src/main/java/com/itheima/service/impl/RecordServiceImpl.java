package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RecordMapper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记录服务实现类
 */
@Service
public class RecordServiceImpl implements RecordService {
    /**
     * 自动注入记录DAO接口
     */
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 添加记录
     *
     * @param record 需要添加的记录对象
     * @return 添加成功的记录条数
     */
    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    /**
     * 查询记录
     *
     * @param record   查询条件对象
     * @param user     当前操作用户
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        // 初始化分页插件
        PageHelper.startPage(pageNum, pageSize);

        // 非管理员用户查询时，自动设置查询条件为当前用户
        if (!"ADMIN".equals(user.getRole())) {
            record.setBorrower(user.getName());
        }

        // 执行查询
        Page<Record> page = recordMapper.searchRecords(record);

        // 构建分页查询结果
        return new PageResult(page.getTotal(), page.getResult());
    }

}

