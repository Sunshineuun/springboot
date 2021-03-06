package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.Book;
import com.qiushengming.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@RestController
@RequestMapping("/book")
public class BookController
    extends BaseManagementController<Book> {

    @Resource(name = "bookService")
    private BookService service;

    @Override
    protected ManagementService<Book> getManagementService() {
        return service;
    }
}
