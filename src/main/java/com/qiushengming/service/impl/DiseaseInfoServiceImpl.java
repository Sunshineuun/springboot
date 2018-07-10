package com.qiushengming.service.impl;

import com.qiushengming.core.service.LuceneService;
import com.qiushengming.core.service.impl.AbstractImportService;
import com.qiushengming.entity.DiseaseInfo;
import com.qiushengming.service.DiseaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
@Service(value = "diseaseInfoService")
public class DiseaseInfoServiceImpl
    extends AbstractImportService<DiseaseInfo>
    implements DiseaseInfoService {

    @Resource(name = "luceneService")
    private LuceneService luceneService;

    @Override
    protected String getSheetName() {
        return "疾病资料库";
    }

    @Override
    protected int getMinColumns() {
        return 22;
    }

    @Override
    public synchronized void importExcel(InputStream is, Map<String, Object> params) {
        List<Map<String, Object>> records =
            readImportRecordsFromExcel(is, params);


    }
}
