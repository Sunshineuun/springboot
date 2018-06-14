package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.entity.BookHistory;
import com.qiushengming.service.BookHistoryService;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@Service(value = "bookHistoryService")
public class BookHistoryServiceImpl
    extends AbstractManagementService<BookHistory>
    implements BookHistoryService {
}
