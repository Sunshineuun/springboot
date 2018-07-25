package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.service.FileService;
import com.qiushengming.entity.FileInfo;
import com.qiushengming.entity.code.MinNieResponse;
import com.qiushengming.exception.SystemException;
import com.qiushengming.mybatis.support.Criteria;
import com.qiushengming.utils.DateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service("fileService")
public class FileServiceImpl
    extends AbstractManagementService<FileInfo>
    implements FileService {

  /**
   * . 获取classpath路径： ResourceUtils.getURL("classpath:").getPath()
   */
  private final String root = ResourceUtils.getURL("classpath:").getPath() + "file";

  public FileServiceImpl() throws FileNotFoundException {
  }

  @Override
  public MinNieResponse addFile(MultipartFile multipart) throws IOException {
    log.debug("content type: {}", multipart.getContentType());

    FileInfo info = new FileInfo();
    info.setName(multipart.getOriginalFilename());
    info.setSize(multipart.getSize());
    info.setSystemPath(getDirPath() + "/" + info.getName());

    if (isNotExit(info) && add(info) == 1) {
      // 文件写入到硬盘中
      FileOutputStream ou = new FileOutputStream(getDirPath() + "/" + info.getName());
      ou.write(multipart.getBytes());
      return new MinNieResponse(true, "", null);
    }
    return new MinNieResponse(false, "文件已存在", null);
  }

  private boolean isExit(FileInfo info) {
    Criteria criteria = Criteria.create().andEqualTo("name", info.getName());
    return countByCriteria(criteria) > 0;
  }

  private boolean isNotExit(FileInfo info) {
    return !isExit(info);
  }

  private String getDirPath() {
    // root + "/" + date + "/" + multipart.getName()
    String dirPath = String.format("%s%s%s", root, File.separator,
        DateFormat.dateToDayString(new Date()));
    File dir = new File(dirPath);
    if (!dir.exists()) {
      if (!dir.mkdirs()) {
        throw new SystemException("文件目录创建失败！");
      }
    }
    return dirPath;
  }
}
