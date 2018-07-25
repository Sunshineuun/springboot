package com.qiushengming.core.controller;

import com.qiushengming.service.FileService;
import com.qiushengming.core.service.ImportService;
import com.qiushengming.entity.code.MinNieResponse;
import com.qiushengming.exception.SystemException;
import com.qiushengming.utils.ApplicationContextUtils;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * .
 *
 * @author qiushengming
 * @date 2018/7/3
 */
@RestController
@RequestMapping("/importData")
public class ImportDataController
    extends BaseController {

  private static final Logger logger =
      LoggerFactory.getLogger(ImportDataController.class);

  /**
   * . 上传导入文件
   *
   * @throws IOException IO异常
   */
  @RequestMapping("/{beanName}/importFile")
  @SuppressWarnings({"unchecked", "rawtypes"})
  public MinNieResponse importFile(@PathVariable("beanName") String beanName,
      MultipartHttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Map<String, Object> params = this.getRequestParameters(request);

    ImportService importService = ApplicationContextUtils.getBean(beanName);
    Iterator<String> iterator = request.getFileNames();
    while (iterator.hasNext()) {
      MultipartFile multipart = request.getFile(iterator.next());
      String fileName = multipart.getOriginalFilename();

      if (StringUtils.endsWith(fileName, ".xls") || StringUtils.endsWith(
          fileName,
          ".xlsx")) {

        importService.importExcel(multipart.getInputStream(), params);
      } else if (StringUtils.endsWith(fileName, ".csv")) {

        importService.importCsv(multipart.getInputStream(), params);
      }
    }

    return new MinNieResponse(true, "", null);
  }

  /**
   * . 任一文件导入存储 <br> 1. 目前支持，单文件上传
   *
   * @param request request
   * @return {@link MinNieResponse}
   */
  @RequestMapping("/importFile")
  public MinNieResponse importFile(MultipartHttpServletRequest request) {
    Map<String, Object> params = this.getRequestParameters(request);
    Iterator<String> iterator = request.getFileNames();
    FileService fileService = ApplicationContextUtils.getBean("fileService");
    MultipartFile multipart = request.getFile(iterator.next());
    try {
      return fileService.addFile(multipart);
    } catch (IOException e) {
      logger.error("{}", e);
      throw new SystemException("IO异常，文件读取失败");
    }
  }


  /**
   * .下载文件
   */
  @RequestMapping("/downloadTemplete")
  @ResponseBody
  public void downloadTemplete(@RequestParam("fileName") String fileName,
      HttpServletResponse response) {
    ServletOutputStream out = null;
    try {
      fileName = URLDecoder.decode(fileName, "utf-8");
      response.setContentType("application/unknown;charset=GBK");
      response.setHeader("Content-Disposition",
          "attachment;filename=" + new String(fileName.getBytes("GBK"),
              "ISO8859-1"));
      response.setCharacterEncoding("GBK");

      Resource resource = ApplicationContextUtils.getResource(
          "classpath:/templete/" + fileName);
      out = response.getOutputStream();

      IOUtils.copy(resource.getInputStream(), out);
      out.flush();
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      IOUtils.closeQuietly(out);
    }
  }
}

