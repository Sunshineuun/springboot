package com.qiushengming.mybatis.support;

import com.qiushengming.configuration.mybatis.MybatisCustomConfiguration;
import com.qiushengming.enums.SQLDialect;
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
import java.util.LinkedHashMap;
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
      @Qualifier(value = "sqlSessionTemplate") SqlSession sqlSession,
      @Qualifier(value = "oracleSqlSessionTemplate") SqlSession oracleSession) {
    super(getSqlSessionMap(sqlSession, oracleSession));
  }

  @Override
  public <T> T getById(Serializable id, Class<T> clazz) {
    return getById(id, clazz, SQLDialect.MYSQL);
  }

  @Override
  public <T> T getById(Serializable id, Class<T> clazz, SQLDialect dialect) {
    ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

    Map<String, Object> values = getValueById(id, classMap);
    values.put(SQL, getSelectSqlById(classMap));

    logger.debug("getById：{}", values);

    return this.getSqlSession(dialect)
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
      Map<String, Object> values = new HashMap<>(1);
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
    return getAll(clazz, SQLDialect.MYSQL);
  }

  @Override
  public <T> List<T> getAll(Class<T> clazz, SQLDialect dialect) {
    ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);
    String statement =
        StatementKeyGenerator.generateSelectStatementKey(clazz);
    Map<Object, Object> params =
        MapUtils.newMap(SQL, classMap.getSelectSql());

    logger.debug("getAll：{}", params);

    return this.getSqlSession(dialect).selectList(statement, params);
  }

  @Override
  public <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz) {
    if (criteria == null) {
      return null;
    }

    ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

    if (classMap == null) {
      logger.error("对象{}缺少Table注解！", clazz.getName());
    }

    Map<String, Object> values = getValueByCriteria(criteria, classMap);
    values.put(SQL, getSelectSqlByCriteria(criteria, classMap));

    logger.debug("queryByCriteria:{}", values);

    return this.getSqlSession(criteria.getDialect())
        .selectList(StatementKeyGenerator.generateSelectStatementKey(clazz),
            values);
  }

  @Override
  public <T> int countByCriteria(Criteria criteria, Class<T> clazz) {
    if (criteria == null) {
      return 0;
    }

    ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

    return this.executeSelectCountDynamic(getCountSqlByCriteria(criteria,
        classMap), getValueByCriteria(criteria, classMap));
  }

  private String getCountSqlByCriteria(Criteria criteria, ClassMap classMap) {
    StringBuilder countSql = new StringBuilder();
    countSql.append("SELECT COUNT(1) FROM (")
        .append(classMap.getSelectSql())
        .append(") T ")
        .append(" WHERE 1=1 ");

    if (criteria.getConditions().isEmpty()) {
      return countSql.toString();
    }

    resovleCriteria(countSql, criteria, classMap);

    return countSql.toString();
  }

  private String getSelectSqlByCriteria(Criteria criteria,
      ClassMap classMap) {
    if (criteria.getConditions().isEmpty()) {
      return classMap.getSelectSql();
    }

    StringBuilder selectSql = new StringBuilder(classMap.getSelectSql());
    selectSql.append(" WHERE 1=1 ");
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

    if (criteria.getOrders().size() == 0) {
      return;
    }

    sb.append(" ORDER BY ");
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

      if (i < criteria.getOrders().size() - 1) {
        sb.append(",");
      }
    }
  }

  private Map<String, Object> getValueByCriteria(Criteria criteria,
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
  public <T> int deleteById(Serializable id, Class<T> clazz) {
    if (id == null) {
      return 0;
    }

    logger.debug("删除对象为：" + clazz.getName() + "；删除ID为：" + id);

    return this.doDeleteById(id, clazz);
  }

  @Override
  public int deleteByCriteria(Criteria criteria, Class<?> clazz) {
    if (criteria == null) {
      return 0;
    }

    ClassMap classMap = AnnotationConfiguration.getClassMap(clazz);

    return this.executeDeleteDynamic(getDeleteSqlByCriteria(criteria, classMap),
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
  public <T> int insert(T obj) {
    if (obj == null) {
      return 0;
    }

    logger.debug("插入对象为：" + obj.toString());

    return this.doInsert(obj);
  }

  @Override
  public <T> int update(T obj) {
    if (obj == null) {
      return 0;
    }

    logger.debug("更新对象为：" + obj.toString());

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

    logger.debug("queryBySql >> " + params.toString());

    return this.getSqlSession(params)
        .selectList(StatementKeyGenerator.generateSelectStatementKey(clazz),
            params);
  }

  private static Map<String, SqlSession> getSqlSessionMap(SqlSession sqlSession,
      SqlSession oracleSession) {
    Map<String, SqlSession> map = new LinkedHashMap<>();
    map.put(SQLDialect.MYSQL.getValue(), sqlSession);
    map.put(SQLDialect.ORACLE.getValue(), oracleSession);
    return map;
  }
}
