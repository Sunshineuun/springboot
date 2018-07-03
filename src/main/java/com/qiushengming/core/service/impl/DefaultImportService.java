package com.qiushengming.core.service.impl;

import com.qiushengming.entity.BaseEntity;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/7/3
 */
@Service("importService")
public class DefaultImportService
    extends AbstractImportService<BaseEntity> {

    @Override
    protected String getSheetName() {
        return super.getSheetName();
    }
}
