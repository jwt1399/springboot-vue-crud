package com.jianjian.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianjian.bean.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<Book> {
}
