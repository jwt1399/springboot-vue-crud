package com.jianjian.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jianjian.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author：简简
 * @createTime：[2022/9/8 00:55]
 **/
@SpringBootTest
public class BookDaoTests {
    @Autowired
    private BookDao bookDao;

    @Test
    void testGetById(){
        System.out.println(bookDao.selectById(1));
    }

    //分页
    @Test
    void testGetPage(){
        IPage page = new Page(2,5);
        bookDao.selectPage(page, null);
        System.out.println(page.getCurrent());		  //当前页码值
        System.out.println(page.getSize());			   //每页显示数
        System.out.println(page.getTotal());		  //数据总量
        System.out.println(page.getPages());		 //总页数
        System.out.println(page.getRecords());	//详细数据
    }

    //条件查询
    @Test
    void testGetBy(){
        //封装查询条件的对象
        QueryWrapper<Book> qw = new QueryWrapper<>();
        //查询条件
        qw.like("name","Spring");
        bookDao.selectList(qw);
    }
    @Test
    void testGetBy2(){
        String name = "1";
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
        lqw.like(Book::getName,"Spring");
        bookDao.selectList(lqw);
    }
    @Test
    void testGetBy3(){
        String name = "Spring";
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
        //if(name != null) lqw.like(Book::getName,name);		//方式一：JAVA代码控制
        lqw.like(name != null,Book::getName,name);				 //方式二：API接口提供控制开关
        bookDao.selectList(lqw);
    }

}
