/**
 * 记录服务接口，提供添加记录和查询记录的功能。
 */
package com.itheima.service;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

public interface RecordService {

    /**
     * 添加记录。
     *
     * @param record 需要添加的记录对象。
     * @return 添加成功的记录条数。
     */
    Integer addRecord(Record record);

    /**
     * 查询记录。
     *
     * @param record   查询条件对象，包含可能的过滤条件。
     * @param user     查询的用户对象，用于权限判断或个性化查询。
     * @param pageNum  当前页码，用于分页查询。
     * @param pageSize 每页的记录数，用于分页查询。
     * @return 分页查询的结果，包含数据和分页信息。
     */
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}
