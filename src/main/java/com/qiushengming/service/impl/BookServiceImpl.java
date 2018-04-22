package com.qiushengming.service.impl;

import com.qiushengming.entity.Book;
import com.qiushengming.service.BookService;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@Service(value = "bookService")
public class BookServiceImpl
    extends BaseServiceImpl<Book>
    implements BookService {

}
