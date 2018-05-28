package com.qiushengming.mybatis.support;

import com.qiushengming.configuration.mybatis.MybatisCustomConfiguration;
import com.qiushengming.exception.MybatisException;
import com.qiushengming.mybatis.ObjectSession;
import com.qiushengming.mybatis.annotation.support.AnnotationConfiguration;
import com.qiushengming.mybatis.builder.StatementKeyGenerator;
import com.qiushengming.mybatis.support.ClassMap.Property;
import com.qiushengming.mybatis.support.Criteria.Condition;
import com.qiushengming.mybatis.support.Criteria.Order;
import com.qiushengming.utils.MapUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MinMin
 */
@Component("objectSession")
@AutoConfigureAfter(MybatisCustomConfiguration.class)
public class ObjectSessionImpl
    extends DynamicSqlDaoImpl
    implements ObjectSession {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ObjectSessionImpl(
        @Qualifier(value = "sqlSessionMap") Map<String, SqlSession> sqlSessionMap) {
        super(sqlSessionMap.get("mysql"));
        setSqlSessionMap(sqlSessionMap);
    }

    @Override
    public <T> T getById(Serializable id, Class<T> clazz) {
        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

        Map<String, Object> values = getValueById(id, classMap);
        values.put(SQL, getSelectSqlById(classMap));
        return this.getSqlSession()
            .selectOne(StatementKeyGenerator.generateSelectStatementKey(clazz),
                values);
    }

    private String getSelectSqlById(ClassMap classMap) {
        StringBuilder selectSql = new StringBuilder(classMap.getSelectSql());
        selectSql.append(" where ");
        Property property;
        for (int i = 0; i < classMap.getIdProperties().size(); i++) {
            property = classMap.getIdProperties().get(i);
            selectSql.append(" ")
                .append(property.getColumn())
                .append("=")
                .append("#{")
                .append(property.getName())
                .append(",jdbcType=")
                .append(property.getJdbcTypeName())
                .append("}");
            if (i < classMap.getIdProperties().size() - 1) {
                selectSql.append(" and ");
            }
        }
        return selectSql.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getValueById(Serializable id,
        ClassMap classMap) {
        if (classMap.getIdProperties().size() == 1) {
            Map<String, Object> values = new HashMap<String, Object>(1);
            values.put(classMap.getIdProperties().get(0).getName(), id);
            return values;
        } else {
            try {
                return PropertyUtils.describe(id);
            } catch (Exception e) {
                throw new MybatisException(e);
            }
        }
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);
        String statement =
            StatementKeyGenerator.generateSelectStatementKey(clazz);
        Map<Object, Object> params =
            MapUtils.newMap(SQL, classMap.getSelectSql());
        return this.getSqlSession().selectList(statement, params);
    }

    @Override
    public <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz) {
        if (criteria == null) { return null; }

        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

        if (classMap == null) {
            logger.error("对象{}缺少Table注解！", clazz.getName());
        }

        Map<String, Object> values = getValueByCriteria(criteria, classMap);
        values.put(SQL, getSelectSqlByCriteria(criteria, classMap));
        return this.getSqlSession()
            .selectList(StatementKeyGenerator.generateSelectStatementKey(clazz),
                values);
    }

    @Override
    public <T> int countByCriteria(Criteria criteria, Class<T> clazz) {
        if (criteria == null) { return 0; }

        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);
        return this.executeSelectCountDynamic(getCountSqlByCriteria(criteria,
            classMap), getValueByCriteria(criteria, classMap));
    }

    private <T> String getCountSqlByCriteria(Criteria criteria,
        ClassMap classMap) {
        StringBuilder countSql = new StringBuilder();
        countSql.append("select count(1) from (")
            .append(classMap.getSelectSql())
            .append(")")
            .append(" where 1=1 ");

        if (criteria.getConditions().isEmpty()) { return countSql.toString(); }

        resovleCriteria(countSql, criteria, classMap);
        return countSql.toString();
    }

    private <T> String getSelectSqlByCriteria(Criteria criteria,
        ClassMap classMap) {
        if (criteria.getConditions().isEmpty()) {
            return classMap.getSelectSql();
        }

        StringBuilder selectSql = new StringBuilder(classMap.getSelectSql());
        selectSql.append(" where 1=1 ");
        resovleCriteria(selectSql, criteria, classMap);
        return selectSql.toString();
    }

    private void resovleCriteria(StringBuilder sb, Criteria criteria,
        ClassMap classMap) {
        Condition condition;
        Property property;
        String fieldName;
        for (int i = 0; i < criteria.getConditions().size(); i++) {
            condition = criteria.getConditions().get(i);
            property = classMap.getPropertyByName(condition.getName());

            if (property == null) {
                throw new MybatisException(
                    "Criteria 中的条件" + condition.getName() + "不存在！");
            }

            if (condition.getCommonOper() == null) {
                fieldName = "field" + i;
                sb.append(" ")
                    .append(condition.getJoin())
                    .append(" ")
                    .append(property.getColumn())
                    .append(" ")
                    .append(condition.getOperator())
                    .append(" ")
                    .append("#{")
                    .append(fieldName)
                    .append(",jdbcType=")
                    .append(property.getJdbcTypeName())
                    .append("}");
            } else {
                sb.append(" ")
                    .append(condition.getJoin())
                    .append(" ")
                    .append(property.getColumn())
                    .append(" ")
                    .append(condition.getCommonOper())
                    .append(" ");
            }
        }

        if (criteria.getOrders().size() == 0) { return; }

        sb.append(" order by ");
        Order order;
        for (int i = 0; i < criteria.getOrders().size(); i++) {
            order = criteria.getOrders().get(i);
            property = classMap.getPropertyByName(order.getName());

            if (property == null) {
                throw new MybatisException(
                    "Criteria 中的条件" + order.getName() + "不存在！");
            }

            sb.append(" ")
                .append(property.getColumn())
                .append(" ")
                .append(order.getOrder());

            if (i < criteria.getOrders().size() - 1) { sb.append(","); }
        }
    }

    private <T> Map<String, Object> getValueByCriteria(Criteria criteria,
        ClassMap classMap) {
        if (criteria.getConditions().isEmpty()) {
            return new HashMap<>(0);
        }

        Map<String, Object> values =
            new HashMap<>(criteria.getConditions().size());
        Condition condition;
        String fieldName;
        for (int i = 0; i < criteria.getConditions().size(); i++) {
            condition = criteria.getConditions().get(i);
            if (condition.getCommonOper() == null) {
                fieldName = "field" + i;
                values.put(fieldName, condition.getValue());
            }
        }
        return values;
    }

    @Override
    public <T> int insert(T obj) {
        if (obj == null) { return 0; }

        return this.doInsert(obj);
    }

    @Override
    public <T> int deleteById(Serializable id, Class<T> clazz) {
        if (id == null) { return 0; }

        return this.doDeleteById(id, clazz);
    }

    @Override
    public <T> void deleteByCriteria(Criteria criteria, Class<?> clazz) {
        if (criteria == null) { return; }

        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);
        this.executeDeleteDynamic(getDeleteSqlByCriteria(criteria, classMap),
            this.getValueByCriteria(criteria, classMap));
    }

    private String getDeleteSqlByCriteria(Criteria criteria,
        ClassMap classMap) {
        if (criteria.getConditions().isEmpty()) {
            return classMap.getDeleteSql();
        }

        StringBuilder deleteSql = new StringBuilder(classMap.getDeleteSql());
        deleteSql.append(" where 1=1 ");
        resovleCriteria(deleteSql, criteria, classMap);
        return deleteSql.toString();
    }

    private String getDeleteSqlById(ClassMap classMap) {
        StringBuilder sb = new StringBuilder(classMap.getDeleteSql());
        sb.append(" where ");
        Property property;
        for (int i = 0; i < classMap.getIdProperties().size(); i++) {
            property = classMap.getIdProperties().get(i);
            sb.append(property.getColumn())
                .append("=")
                .append("#{")
                .append(property.getName())
                .append(",jdbcType=")
                .append(property.getJdbcTypeName())
                .append("}");
            if (i < classMap.getIdProperties().size() - 1) {
                sb.append(" and ");
            }
        }
        return sb.toString();
    }

    @Override
    public <T> int update(T obj) {
        if (obj == null) { return 0; }

        return this.doUpdate(obj);
    }

    private int doInsert(Object obj) {
        ClassMap classMap = AnnotationConfiguration.getClassMap(obj.getClass());
        if (classMap == null) {
            logger.error("对象{}缺少Table注解！", obj.getClass().getName());
            return 0;
        }

        return this.executeInsertDynamic(classMap.getInsertSql(),
            getValueMap(obj));
    }

    private int doUpdate(Object obj) {
        ClassMap classMap = AnnotationConfiguration.getClassMap(obj.getClass());
        if (classMap == null) {
            logger.error("对象{}缺少Table注解！", obj.getClass().getName());
            return 0;
        }
        return this.executeUpdateDynamic(classMap.getUpdateSql(),
            getValueMap(obj));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getValueMap(Object obj) {
        try {
            return PropertyUtils.describe(obj);
        } catch (Exception e) {
            throw new MybatisException(e);
        }
    }

    private int doDeleteById(Serializable id, Class<?> clazz) {
        ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);
        if (classMap == null) {
            logger.error("对象{}缺少Table注解！", clazz.getClass().getName());
            return 0;
        }

        return this.executeDeleteDynamic(getDeleteSqlById(classMap),
            getValueById(id, classMap));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> queryBySql(String sql, Class<T> clazz, Object params) {
        if (params instanceof Map) {
            ((Map) params).put(SQL, sql);
        } else {
            params = getValueMap(params);
            ((Map) params).put(SQL, sql);
        }
        return this.getSqlSession()
            .selectList(StatementKeyGenerator.generateSelectStatementKey(clazz),
                params);
    }
}
