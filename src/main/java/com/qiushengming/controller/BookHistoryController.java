package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.BookHistory;
import com.qiushengming.service.BookHistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
@RestController
@RequestMapping("/bookHistory")
public class BookHistoryController
    extends BaseManagementController<BookHistory> {

    @Resource(name = "bookHistoryService")
    private BookHistoryService service;

    @Override
    protected ManagementService<BookHistory> getManagementService() {
        return service;
    }
}
