package com.qiushengming.mybatis.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 存储实体的信息，包含表名，查询，更新等sql
 *
 * @author qiushengming
 */
public final class ClassMap {
    /**
     * 表名
     */
    private String tableName;
    /**
     * TODO
     */
    private String resultMapId;
    /**
     * 查询sql
     */
    private String selectSql;
    /**
     * 插入sql
     */
    private String insertSql;
    /**
     * 更新sql
     */
    private String updateSql;
    /**
     * 删除sql
     */
    private String deleteSql;
    /**
     * 创建表的SQL
     */
    private String createSql;
    /**
     * TODO
     */
    private Class<?> type;
    /**
     * 属性
     */
    private List<Property> properties = new ArrayList<>(5);
    /**
     * id属性
     */
    private List<Property> idProperties = new ArrayList<>(1);

    /**
     * @param type        实体的class
     * @param tableName   表名
     * @param resultMapId todo
     * @param selectSql   查询sql
     */
    public ClassMap(Class<?> type, String tableName, String resultMapId,
        String selectSql) {
        this.type = type;
        this.tableName = tableName;
        this.resultMapId = resultMapId;
        this.selectSql = selectSql;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Property getPropertyByName(String name) {
        for (Property property : properties) {
            if (StringUtils.equals(property.getName(), name)) {
                return property;
            }
        }

        for (Property property : idProperties) {
            if (StringUtils.equals(property.getName(), name)) {
                return property;
            }
        }
        return null;
    }

    public String getInsertSql() {
        if (insertSql == null) { insertSql = generateInsertSql(); }
        return insertSql;
    }

    public String getUpdateSql() {
        if (updateSql == null) { updateSql = generateUpdateSql(); }
        return updateSql;
    }

    public String getDeleteSql() {
        if (deleteSql == null) { deleteSql = generateDeleteSql(); }
        return deleteSql;
    }

    private String generateInsertSql() {
        List<Property> properties = this.getAllAddProperties();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(this.getTableName()).append(" (");
        Property property;
        for (int i = 0; i < properties.size(); i++) {
            property = properties.get(i);
            sb.append(property.getColumn());
            if (i < properties.size() - 1) { sb.append(","); }
        }
        sb.append(") VALUES (");
        for (int i = 0; i < properties.size(); i++) {
            property = properties.get(i);
            sb.append("#{")
                .append(property.getName())
                .append(",jdbcType=")
                .append(property.getJdbcTypeName())
                .append("}");
            if (i < properties.size() - 1) { sb.append(","); }
        }
        sb.append(")");
        return sb.toString();
    }

    private String generateUpdateSql() {
        List<Property> properties = this.getAllUpdateProperties();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(this.getTableName()).append(" SET ");
        Property property;
        for (int i = 0; i < properties.size(); i++) {
            property = properties.get(i);

            sb.append(property.getColumn())
                .append("=")
                .append("#{")
                .append(property.getName())
                .append(",jdbcType=")
                .append(property.getJdbcTypeName())
                .append("}");
            if (i < properties.size() - 1) { sb.append(","); }
        }
        sb.append(" WHERE ");
        for (int i = 0; i < this.getIdProperties().size(); i++) {
            property = this.getIdProperties().get(i);
            sb.append(property.getColumn())
                .append("=")
                .append("#{")
                .append(property.getName())
                .append(",jdbcType=")
                .append(property.getJdbcTypeName())
                .append("}");
            if (i < this.getIdProperties().size() - 1) { sb.append(" and "); }
        }
        return sb.toString();
    }

    private String generateDeleteSql() {
        return "DELETE FROM " + this.getTableName();
    }

    private List<Property> getAllAddProperties() {
        List<Property> list = new ArrayList<>();
        list.addAll(idProperties);
        for (Property property : properties) {
            if (property.isAdd) { list.add(property); }
        }
        return list;
    }

    public List<Property> getAllUpdateProperties() {
        List<Property> list = new ArrayList<Property>();
        for (Property property : properties) {
            if (property.isUpdate) { list.add(property); }
        }
        return list;
    }

    public String[] getColumns() {
        String[] columns = new String[properties.size() + idProperties.size()];
        for (int i = 0; i < idProperties.size(); i++) {
            columns[i] = idProperties.get(i).getColumn();
        }
        for (int i = 0; i < properties.size(); i++) {
            columns[i] = properties.get(i).getColumn();
        }
        return columns;
    }

    public String[] getPropertyNames() {
        String[] propertyNames =
            new String[properties.size() + idProperties.size()];
        for (int i = 0; i < idProperties.size(); i++) {
            propertyNames[i] = idProperties.get(i).getName();
        }
        for (int i = 0; i < properties.size(); i++) {
            propertyNames[i] = properties.get(i).getName();
        }
        return propertyNames;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getResultMapId() {
        return resultMapId;
    }

    public void setResultMapId(String resultMapId) {
        this.resultMapId = resultMapId;
    }

    public String getSelectSql() {
        if (StringUtils.isEmpty(selectSql)) {
            selectSql = "SELECT * FROM " + tableName;
        }
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public List<Property> getIdProperties() {
        return idProperties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void addIdProperty(Property property) {
        idProperties.add(property);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }

    public class Property {
        private boolean isAdd;
        private boolean isUpdate;
        private String column;
        private String name;
        private Class<?> type;

        public Property(String name, Class<?> type, String column,
            boolean isAdd, boolean isUpdate) {
            this.name = name;
            this.type = type;
            this.column = column;
            this.isAdd = isAdd;
            this.isUpdate = isUpdate;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public boolean isUpdate() {
            return isUpdate;
        }

        public void setUpdate(boolean isUpdate) {
            this.isUpdate = isUpdate;
        }

        JdbcType getJdbcType() {
            if (type == String.class) { return JdbcType.VARCHAR; } else if (
                type == boolean.class || type == Boolean.class) {
                return JdbcType.BOOLEAN;
            } else if (type == Date.class) { return JdbcType.TIMESTAMP; }

            return JdbcType.VARCHAR;
        }

        String getJdbcTypeName() {
            switch (getJdbcType()) {
                case VARCHAR:
                    return "VARCHAR";
                case BOOLEAN:
                    return "BOOLEAN";
                case TIMESTAMP:
                    return "TIMESTAMP";
                default:
                    return "VARCHAR";
            }
        }
    }
}
