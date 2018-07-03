package com.qiushengming.core.controller;

import com.qiushengming.core.service.ImportService;
import com.qiushengming.entity.code.MinNieResponse;
import com.qiushengming.utils.ApplicationContextUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

/**
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
     * 上传导入文件
     * <p>
     * eturn com.winning.kbms.core.domain.JResult
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
            /*CommonsMultipartFile multipartFile =
                (CommonsMultipartFile) request.getFile(iterator.next());
            String fileName = multipartFile.getName();*/
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
     * 下载文件
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

    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
            new ByteArrayMultipartFileEditor());
    }
}

