package com.qiushengming.service;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.FileInfo;
import com.qiushengming.entity.code.MinNieResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 针对系统中文件的处理
 * 1. 文件上传存储
 */
public interface FileService
        extends ManagementService<FileInfo> {
    /**
     * 文件存储
     * 1. 如果定义的存储成功/失败。后续确定。TODO
     * @param multipart {@link MultipartFile}
     * @return 存储成功-true；存储失败-false。
     */
    MinNieResponse addFile(MultipartFile multipart) throws IOException;
}
