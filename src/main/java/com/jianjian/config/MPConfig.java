package com.jianjian.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：简简
 * @createTime：[2022/9/9 23:12]
 **/
@Configuration
public class MPConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //创建MP的拦截器栈
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //初始化了分页拦截器，并添加到拦截器栈中
        //如果后期开发其他功能，需要添加全新的拦截器，按照此行的格式继续add进去新的拦截器就可以了。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
