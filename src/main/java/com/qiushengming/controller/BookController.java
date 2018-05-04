package com.qiushengming.controller;

import com.qiushengming.entity.MinNieResponse;
import com.qiushengming.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@RestController(value = "book")
public class BookController {

    @Resource(name = "bookService")
    private BookService service;

    @RequestMapping("/getBooks")
    public MinNieResponse getBooks() {
        return new MinNieResponse(true, "success", service.getAll());
    }
}
