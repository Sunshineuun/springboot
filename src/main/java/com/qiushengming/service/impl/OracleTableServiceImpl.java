package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.entity.OracleTable;
import com.qiushengming.service.OracleTableService;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/6/26
 */
@Service("oracleTableService")
public class OracleTableServiceImpl
    extends AbstractManagementService<OracleTable>
    implements OracleTableService {
}
