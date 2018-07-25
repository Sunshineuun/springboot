package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.FileInfo;
import com.qiushengming.exception.SystemException;
import com.qiushengming.service.FileService;
import com.qiushengming.utils.ApplicationContextUtils;
import java.io.File;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController extends BaseManagementController<FileInfo> {

  @Resource(name = "fileService")
  private FileService fileService;

  @Override
  protected ManagementService<FileInfo> getManagementService() {
    return fileService;
  }

  /**
   * .文件下载
   *
   * @param id id
   * @param response response
   */
  @RequestMapping("/downloadFile")
  public void downloadFile(@RequestParam("id") String id,
      HttpServletResponse response) {
    ServletOutputStream out = null;

    FileInfo fileInfo = fileService.getById(id);

    if (fileInfo == null) {
      throw new SystemException("查询不到文件.");
    }

    File file = new File(fileInfo.getSystemPath());

    if (!file.exists()) {
      throw new SystemException("文件不存在.");
    }

    String fileName = fileInfo.getName();
    try {
      fileName = URLDecoder.decode(fileName, "utf-8");
      response.setContentType("application/unknown;charset=GBK");
      response.setHeader("Content-Disposition",
          "attachment;filename=" + new String(fileName.getBytes("GBK"),
              "ISO8859-1"));
      response.setCharacterEncoding("GBK");

      org.springframework.core.io.Resource resource = ApplicationContextUtils
          .getResource(fileInfo.getSystemPath());
      out = response.getOutputStream();

      IOUtils.copy(resource.getInputStream(), out);
      out.flush();
    } catch (Exception e) {
      log.error("{}", e);
    } finally {
      IOUtils.closeQuietly(out);
    }
  }
}
