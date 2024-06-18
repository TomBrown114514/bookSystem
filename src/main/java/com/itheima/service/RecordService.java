package com.itheima.service;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

public interface RecordService {
    Integer addRecord(Record record);
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}
