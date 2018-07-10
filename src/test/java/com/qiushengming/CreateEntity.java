package com.qiushengming;

import com.qiushengming.utils.CustomStringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/7/9
 */
public class CreateEntity {

    private final static String IMPORT_COLUMN = "import com.qiushengming.annotation.Column;";
    private final static String IMPORT_TABLE = "import com.qiushengming.annotation.Table;\n";
    private final static String ZHU_SHI = "/**\n * @author qiushengming\n * @date 2018/7/6\n */";
    private final static String IMPORT_MANAGEMENTSERVICE = "import com.qiushengming.core.service.ManagementService;\n";

    public static void main(String[] args) throws IOException {
        String titleName = "疾病资料库";
        String className = "DiseaseInfo";
        String pageName = "com.qiushengming";
        // 二维数组，第一位是注释，第二位是属性名称
        String[][] attributes = {
            {"ICD10-卫计委版", "icd10"},
            {"疾病名称", "name"},
            {"疾病类别", "type"},
            {"来源说明", "sourceDsc"},
            {"英文名", "enName"},
            {"别名", "alias"},
            {"概述", "outline"},
            {"流行病学", "epidemiology"},
            {"疾病分期/分类", "diseaseStage"},
            {"病因", "etiology"},
            {"病理/发病机制", "pathogenesis"},
            {"临床表现", "clinicalFeature"},
            {"相关检查", "relevantExamination"},
            {"诊断标准", "diagnosticCriteria"},
            {"鉴别诊断", "differentialDiagnosis"},
            {"并发症", "complication"},
            {"治疗方案", "therapeuticSchedule"},
            {"预后", "prognosis"},
            {"预防", "precaution"},
            {"别名-来源于DT组提供", "aliasDt"}};

        createEntity(className, pageName, attributes);
        createController(className, pageName);
        createService(className, pageName);
        createServiceImpl(className, pageName);
        createSQL(titleName, className, attributes);
        createJSP(className, titleName);
        createJS(className, attributes);
    }

    private static void createJS(String className, String[][] attributes) {
        String serviceName =
            String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1, className.length());
        List<String> lines = new ArrayList<>();
        lines.add("Ext.onReady(function () {\n" + "  var grid = Ext.widget('uxgridview', {");
        lines.add(String.format("url: ctx + \"/query/%Service/list.do\"", serviceName));
        lines.add(String.format("editUrl: ctx + \"/%s/execute.do\"\n," + "reasonUrl: ctx + \"/%s/editLog.do\",",
            serviceName,
            serviceName));
        lines.add("enableColumnHide: true,");
        lines.add("iconCls: \"table\",");

        // 写入列
        lines.add("columns: [");
        lines.add("{header: 'ID',dataIndex: 'ID',editname: 'id',hidden: true, hideable: true},");
        for (String[] att : attributes) {
            lines.add(String.format(
                "{header: '%s',dataIndex: '%s',editname: '%s',hidden: false, hideable: false, editable: true, search: true, searchoptions: { sopt: ['cn', 'eq'] } },",
                att[0],
                humpToUnderline(att[1]),
                att[1]));
        }
        lines.add("],");

        lines.add("sortname: \"UPDATE_TIME\",\n   sortorder: 'desc', ");
        lines.add(
            "jsonReader: {\n" + "      root: 'result.result',\n" + "      id: \"ID\",\n" + "      idName: \"id\",\n"
                + "      totalProperty: \"result.totalCount\",\n" + "      successProperty: 'success'\n" + "    },");
        lines.add(
            "navGrid: {\n" + "      add: authc.add,\n" + "      edit: authc.edit,\n" + "      logicDel: authc.del,\n"
                + "      clinicalsubmit: authc.edit,\n" + "      search: false}");
        lines.add("}");

        writeFile(lines, serviceName + ".js");
    }

    /**
     * 创建jsp文件
     *
     * @param className 类名
     * @param titleName 中文含义title标签中的值
     */
    private static void createJSP(String className, String titleName) {
        String codeName =
            String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1, className.length());
        List<String> lines = new ArrayList<>();
        lines.add("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"");
        lines.add("         pageEncoding=\"UTF-8\" %>");
        lines.add("<%@page import=\"com.winning.kbms.core.utils.ContextUtils\" %>");
        lines.add(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        lines.add("<%@include file=\"/WEB-INF/jsp/commons/taglibs.jsp\" %>\n" + "<html >\n" + "<head >");
        lines.add(String.format("<title>%s</title>", titleName));
        lines.add("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" >");
        lines.add("    <script type=\"text/javascript\" >\n" + "      var authc =");
        lines.add("<%=ContextUtils.hasPermission(\"#:add\",\"#:edit\", \"#:del\")%>;".replaceAll("#", codeName));
        lines.add("    </script >");
        lines.add("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/ux/Combobox.js\" ></script >");
        lines.add("    <script type=\"text/javascript\" src=\"${ctx}/resources/js/ux/GridView.js\" ></script >");
        lines.add("<%--TODO--%>");
        lines.add("    <script type=\"text/javascript\"");
        lines.add(String.format("            src=\"${ctx}/resources/js/app/clinical/drug/%s.js\" ></script >",
            codeName));

        lines.add("</head >\n" + "<body >\n" + "\n" + "</body >\n" + "</html >\n");

        writeFile(lines, humpToUnderline(className).toLowerCase() + ".jsp");
    }

    /**
     * 创建建表脚本，包含注释
     *
     * @param className  类名
     * @param attributes 属性名称
     */
    private static void createSQL(String titleName, String className, String[][] attributes) {
        List<String> lines = new ArrayList<>();
        // CREATE TABLE
        lines.add(String.format("CREATE TABLE %s (\n", getTableName(className)));

        // 增加字段
        lines.add("  ID           VARCHAR2(36) PRIMARY KEY,");

        for (String[] att : attributes) {
            lines.add(String.format("  %s  VARCHAR2(2000),", humpToUnderline(att[1])));
        }

        lines.add("  UPDATE_BY    VARCHAR2(50),\n" + "  UPDATE_TIME  DATE,\n" + "  CREATE_TIME  DATE,\n"
            + "  IS_PUBLISH   VARCHAR2(1),\n" + "  IS_SUBMIT    VARCHAR2(1),\n" + "  IS_ENABLE    VARCHAR2(1)");
        lines.add(")");

        // 增加表注释
        lines.add(String.format("COMMENT ON TABLE %s IS '%s';", getTableName(className), titleName));
        // 增加列注释
        for (String[] att : attributes) {
            lines.add(String.format("COMMENT ON COLUMN %s.%s IS '%s';", getTableName(className), att[1], att[0]));
        }

        writeFile(lines, className + ".sql");
    }

    /**
     * 创建接口实现类
     *
     * @param className 类名
     * @param pageName  包名
     */
    private static void createServiceImpl(String className, String pageName) {
        List<String> lines = new ArrayList<>();
        // 写入包名
        lines.add(String.format("package %s.service.impl;\n", pageName));

        // 写入导入包
        lines.add("import com.qiushengming.core.service.impl.AbstractManagementService;\n" + String.format(
            "import %s.entity.%s;\n",
            pageName,
            className) + String.format("import %s.service.%sService;\n", pageName, className)
            + "import org.springframework.stereotype.Service;\n");

        // 写入注释，作者信息机器
        lines.add(ZHU_SHI);

        // 写入注解
        String serviceName =
            String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1, className.length());
        lines.add(String.format("@Service(value = \"%sService\")", serviceName));

        // 写入类定义
        lines.add(String.format("public class %sServiceImpl\n", className) + String.format(
            "    extends AbstractManagementService<%s>\n",
            className) + String.format("    implements %sService {", className));


        // 写入闭大括号
        lines.add("}");

        writeFile(lines, className + "ServiceImpl.java");
    }

    /**
     * 创建服务接口
     *
     * @param className 类名
     * @param pageName  包名
     */
    private static void createService(String className, String pageName) {
        List<String> lines = new ArrayList<>();
        // 写入包名
        lines.add(String.format("package %s.service;\n", pageName));
        // 写入导入包
        lines.add(IMPORT_MANAGEMENTSERVICE + String.format("import %s.entity.%s;\n", pageName, className));

        // 写入注释，作者信息机器
        lines.add(ZHU_SHI);

        // 写入类定义
        lines.add(String.format("public interface %sService\n" + "        extends ManagementService<%s> {",
            className,
            className));

        // 写入闭大括号
        lines.add("}");

        writeFile(lines, className + "Service.java");

    }

    /**
     * 创建controller
     *
     * @param className 类名
     * @param pageName  包名
     */
    private static void createController(String className, String pageName) {
        List<String> lines = new ArrayList<>();
        // 写入包名
        lines.add(String.format("package %s.controller;\n", pageName));

        // 写入导入包
        lines.add("import com.qiushengming.core.controller.BaseManagementController;\n" + IMPORT_MANAGEMENTSERVICE
            + String.format("import %s.entity.%s;\n", pageName, className) + String.format(
            "import %s.service.%sService;\n",
            pageName,
            className) + "import org.springframework.web.bind.annotation.RequestMapping;\n"
            + "import org.springframework.web.bind.annotation.RestController;\n"
            + "import javax.annotation.Resource;\n");

        // 写入注释，作者信息机器
        lines.add(ZHU_SHI);

        // 写入注解
        lines.add(String.format("@RestController\n" + "@RequestMapping(\"/%s\")", className));

        // 写入类声明
        lines.add(String.format("public class %sController\n" + "    extends BaseManagementController<%s> {",
            className,
            className));

        // 写入注入dao层
        String serviceName =
            String.valueOf(className.charAt(0)).toLowerCase() + className.substring(1, className.length());
        lines.add(String.format("    @Resource(name = \"%sService\")", serviceName));
        lines.add(String.format("    private %sService %sService;\n", className, serviceName));

        // 写入覆写方法
        lines.add(String.format("   @Override\n" + "    protected ManagementService<%s> getManagementService() {",
            className));
        lines.add(String.format("       return %sService;\n    }", serviceName));

        // 写入闭大括号
        lines.add("}");

        // 输出到文件
        writeFile(lines, className + "Controller.java");
    }

    /**
     * 创建实体
     *
     * @param className  类名
     * @param pageName   包名
     * @param attributes 属性
     */
    private static void createEntity(String className, String pageName, String[][] attributes) {
        List<String> lines = new ArrayList<>();
        // 写入包名
        lines.add(String.format("package %s.entity;\n", pageName));

        // 写入导入包
        lines.add(IMPORT_COLUMN);
        lines.add(IMPORT_TABLE);

        // 写入注释，作者信息机器
        lines.add(ZHU_SHI);

        // 写入@Table注解
        String tableName = getTableName(className);
        lines.add(String.format("@Table(value = \"%s\", resultMapId = \"%sMap\")", tableName, className));

        // 写入类定义
        lines.add(String.format("public class %s extends BaseEntity {", className));

        // 写入属性
        writeAttributes(lines, attributes);

        // 写入闭大括号
        lines.add("}");

        writeFile(lines, className + ".java");

    }

    /**
     * 向lines中写入属性信息，包含get/set方法
     *
     * @param lines      字符床队列
     * @param attributes 属性信息： 第0位属性中文含义，第1位属性名称。
     */
    private static void writeAttributes(List<String> lines, String[][] attributes) {
        // 写入属性
        for (String[] att : attributes) {
            lines.add(String.format("    /** %s */", att[0]));
            lines.add(String.format("    private String %s; \n", att[1]));
        }

        // 写入get\set方法
        for (String[] att : attributes) {
            String attName = att[1];
            String methodName =
                String.valueOf(attName.charAt(0)).toUpperCase() + attName.substring(1, attName.length());
            // 写入Column注解
            lines.add(String.format("   @Column(value = \"%s\", chineseName = \"%s\")", humpToUnderline(attName), att[0]));
            // 写入get方法
            lines.add(String.format("   public String get%s() {", methodName));
            lines.add(String.format("        return %s;\n    }\n", attName));
            // 写入set方法
            lines.add(String.format("   public void set%s(String %s) {", methodName, attName));
            lines.add(String.format("        this.%s=%s;\n    }\n", attName, attName));
        }

    }

    /**
     * 将 lines 写入到文件中
     *
     * @param lines    被写入的字符串
     * @param fileName 文件名
     */
    private static void writeFile(List<String> lines, String fileName) {
        // 输出到文件
        try {
            FileUtils.writeLines(new File(String.format("D:\\package\\%s", fileName)), lines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将类名按照格式转换为表名
     *
     * @param className 类名
     * @return 表名
     */
    private static String getTableName(String className) {
        return "KBMS_" + humpToUnderline(className);
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     */
    private static String humpToUnderline(String para) {
        return CustomStringUtils.humpToUnderline(para);
    }
}
