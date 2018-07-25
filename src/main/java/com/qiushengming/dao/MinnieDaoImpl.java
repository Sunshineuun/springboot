package com.qiushengming.dao;

import com.qiushengming.enums.SQLDialect;
import com.qiushengming.mybatis.ObjectSession;
import com.qiushengming.mybatis.support.Criteria;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * .
 *
 * @author qiushengming
 * @date 2018/4/14
 */
@Component
public class MinnieDaoImpl
    implements MinnieDao {

  @Resource(name = "objectSession")
  private ObjectSession session;

  private SqlSession getSqlSession(Object param) {
    return session.getSqlSession(param);
  }

  private SqlSession getSqlSession() {
    return session.getSqlSession(SQLDialect.MYSQL);
  }

  /**
   * .依据ID查询
   *
   * @param id id
   * @param clazz 类
   * @return clazz的实例
   */
  @Override
  public <T> T getById(Serializable id, Class<T> clazz) {
    return session.getById(id, clazz);
  }

  /**
   * .@param clazz class
   * @return 结果集
   */
  @Override
  public <T> List<T> getAll(Class<T> clazz) {
    return session.getAll(clazz);
  }

  @Override
  public <K, V> List<Map<K, V>> queryBySql(String sql) {
    return session.executeSelectListDynamic(sql);
  }

  @Override
  public <K, V> List<Map<K, V>> queryBySql(String sql,
      Map<String, Object> params) {
    return session.executeSelectListDynamic(sql, params);
  }

  @Override
  public <T> List<T> queryBySql(String sql, Class<T> clazz,
      Map<String, Object> params) {
    return session.queryBySql(sql, clazz, params);
  }

  @Override
  public <K, V> List<Map<K, V>> queryBySql(String sql,
      Map<String, Object> params, int offset, int limit) {
    return session.executeSelectListDynamic(sql, offset, limit, params);
  }

  @Override
  public <T> List<T> queryBySql(Class clazz, String sql,
      Map<String, Object> params, int offset, int limit) {
    return session.executeSelectListDynamic(clazz, sql, offset, limit, params);
  }

  @Override
  public int countBySql(String sql, Map<String, Object> params) {
    return session.executeSelectCountDynamic(sql, params);
  }

  @Override
  public <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz) {
    return session.queryByCriteria(criteria, clazz);
  }

  @Override
  public <T> T queryOneByCriteria(Criteria criteria, Class<T> clazz) {
    List<T> list = this.queryByCriteria(criteria, clazz);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public <T> List<T> query(String mybaitsId) {
    return getSqlSession().selectList(mybaitsId);
  }

  @Override
  public <T> List<T> query(String mybaitsId, Object params) {
    return getSqlSession(params).selectList(mybaitsId, params);
  }

  @Override
  public <T> List<T> query(String mybaitsId, Object params, int offset,
      int limit) {
    return getSqlSession(params).selectList(mybaitsId,
        params,
        new RowBounds(offset, limit));
  }

  @Override
  public <T> int countByCriteria(Criteria criteria, Class<T> clazz) {
    return session.countByCriteria(criteria, clazz);
  }

  /**
   * .持久化对象
   *
   * @param o 被持久化的对象
   * @return 失败返回0;成功返回1
   */
  @Override
  public <T> int add(T o) {
    return session.insert(o);
  }

  /**
   * .更新对象
   *
   * @param o 被更新的对象
   * @return 失败返回0;成功返回1
   */
  @Override
  public <T> int update(T o) {
    return session.update(o);
  }

  /**
   * .删除对象
   *
   * @param o 被删除的对象
   * @param id id
   * @return 失败返回0;成功返回1
   */
  @Override
  public int delete(Serializable id, Class<?> o) {
    return session.deleteById(id, o);
  }
}
