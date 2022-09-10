package com.jianjian.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jianjian.bean.Book;

import java.util.List;

/**
 * @author：简简
 * @createTime：[2022/9/9 23:19]
 **/
public interface BookService {
    Boolean save(Book book);
    Boolean update(Book book);
    Boolean delete(Integer id);
    Book getById(Integer id);
    List<Book> getAll();
    IPage<Book> getPage(int currentPage,int pageSize,Book book);
}