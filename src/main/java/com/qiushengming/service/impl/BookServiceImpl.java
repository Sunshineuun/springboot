package com.qiushengming.service.impl;

import com.qiushengming.entity.Book;
import com.qiushengming.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@Service(value = "bookService")
public class BookServiceImpl
    extends BaseServiceImpl<Book>
    implements BookService {

    @Override
    public List<Map<String, Object>> getHistoryBook() {
        String sql = "SELECT T1.id, T2.NAME bookName, borrower, IS_ENABLE isEnable, START_DATE startDate, END_DATE endDate FROM BOOK_HISTORY T1 LEFT JOIN book T2 ON T1.BOOK_ID = T2.ID";
        return this.getDao().queryBySql(sql);
    }
}
