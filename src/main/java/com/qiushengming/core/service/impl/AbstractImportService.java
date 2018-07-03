package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ImportService;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.exception.SystemException;
import com.qiushengming.utils.XlsxCovertCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/7/3
 */
public abstract class AbstractImportService<T extends BaseEntity>
    extends AbstractManagementService<T>
    implements ImportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public synchronized void importExcel(InputStream is, Map<String, Object> params) {
        List<Map<String, Object>> records =
            readImportRecordsFromExcel(is, params);
    }

    @Override
    public void importCsv(InputStream is, Map<String, Object> params) {

    }

    protected List<Map<String, Object>> readImportRecordsFromExcel(
        InputStream is, Map<String, Object> params) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String[]> csvData;
        try {
            csvData = XlsxCovertCsvReader.readerExcel(is,
                getMinColumns(),
                getSheetName());
        } catch (Exception e) {
            logger.error("{}", e);
            throw new SystemException("导入模版错误，请点击‘模版下载’下载导入模版！");
        }

        if (csvData.size() == 0) {
            logger.error("数据读取为空！！！！ >> 导入模版错误，请点击‘模版下载’下载导入模版！");
            throw new SystemException("导入模版错误，请点击‘模版下载’下载导入模版！");
        }

        logger.debug("数据共计{}条", csvData.size());
        for (String[] s : csvData) {
            for (String s1 : s) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("{}", e);
                }
                logger.debug(s1);
            }
        }
        return result;
    }

    protected int getMinColumns() {
        return 25;
    }

    protected String getSheetName() {
        return "Sheet1";
    }
}
