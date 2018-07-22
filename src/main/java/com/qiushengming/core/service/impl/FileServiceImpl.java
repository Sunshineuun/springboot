package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.FileService;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.code.FileInfo;
import com.qiushengming.utils.DateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Slf4j
@Service("fileService")
public class FileServiceImpl
        extends AbstractManagementService<FileInfo>
        implements FileService {

    private String ROOT = ResourceUtils.getURL("classpath:").getPath();

    public FileServiceImpl() throws FileNotFoundException {
    }

    @Override
    public boolean addFile(MultipartFile multipart) throws IOException {
        log.debug("content type: {}", multipart.getContentType());

        FileInfo info = new FileInfo();
        info.setName(multipart.getName());
        info.setSize(multipart.getSize());

        // 文件写入到硬盘中
        String filePath = getFilePath(info.getName());
        FileOutputStream ou = new FileOutputStream(filePath);
        ou.write(multipart.getBytes());

        add(info);

        info.setSystemPath(getFilePath(filePath));


        return true;
    }

    private String getFilePath(String fileName) {
        // ROOT + "/" + date + "/" + multipart.getName()
        return String.format("%s/%s/%s", ROOT, DateFormat.dateToString(new Date()), fileName);
    }
}
