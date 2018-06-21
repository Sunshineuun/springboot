package com.qiushengming.mybatis.support;

import com.qiushengming.exception.MybatisException;
import com.qiushengming.mybatis.DynamicSqlDao;
import com.qiushengming.mybatis.builder.StatementKeyGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 动态执行SQL语句
 *
 * @author qiushengming
 * @date 2018年4月13日
 */
public class DynamicSqlDaoImpl
    implements DynamicSqlDao {
    private static final String SELECT_COUNT_STATEMENT = "selectCountStatement";
    private static final String SELECT_STATEMENT = "selectStatement";
    private static final String UPDATE_STATEMENT = "updateStatement";
    private static final String INSERT_STATEMENT = "insertStatement";
    private static final String DELETE_STATEMENT = "deleteStatement";
    static final String SQL = "_sql";

    private final SqlSession sqlSession;

    public DynamicSqlDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int executeSelectCountDynamic(String sql, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                Object obj =
                    sqlSession.selectOne(SELECT_COUNT_STATEMENT, params);
                return obj == null ? 0 : (Integer) obj;
            } else {
                return executeSelectCountDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public int executeSelectCountDynamic(String sql) {
        return executeSelectCountDynamic(sql, new HashMap<String, Object>(0));
    }

    /**
     * 查询返回map集合
     *
     * @param sql    脚本
     * @param params 参数
     * @param <K>    key
     * @param <V>    value
     * @return 返回map集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public <K, V> List<Map<K, V>> executeSelectListDynamic(String sql,
        Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.selectList(SELECT_STATEMENT, params);
            } else {
                return executeSelectListDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    /**
     * 查询返回clazz对应的实体
     *
     * @param clazz  Class
     * @param sql    sql
     * @param params 参数
     * @param <T>    实体
     * @return clazz实体的集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> executeSelectListDynamic(Class<?> clazz, String sql,
        Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.selectList(StatementKeyGenerator.generateSelectStatementKey(
                    clazz), params);
            } else {
                return executeSelectListDynamic(clazz,
                    sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public <K, V> List<Map<K, V>> executeSelectListDynamic(String sql) {
        return executeSelectListDynamic(sql, new HashMap<String, Object>(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> executeSelectOneDynamic(String sql, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.selectOne(SELECT_STATEMENT, params);
            } else {
                return executeSelectOneDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public <K, V> Map<K, V> executeSelectOneDynamic(String sql) {
        return executeSelectOneDynamic(sql, new HashMap<String, Object>(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public int executeUpdateDynamic(String sql, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.update(UPDATE_STATEMENT, params);
            } else {
                return executeUpdateDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public int executeUpdateDynamic(String sql) {
        return executeUpdateDynamic(sql, new HashMap<String, Object>(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public int executeInsertDynamic(String sql, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.insert(INSERT_STATEMENT, params);
            } else {
                return executeInsertDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public int executeInsertDynamic(String sql) {
        return executeInsertDynamic(sql, new HashMap<String, Object>(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public int executeDeleteDynamic(String sql, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.delete(DELETE_STATEMENT, params);
            } else {
                return executeDeleteDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public int executeDeleteDynamic(String sql) {
        return executeDeleteDynamic(sql, new HashMap<String, Object>(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> List<Map<K, V>> executeSelectListDynamic(String sql,
        int offset, int limit, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.selectList(SELECT_STATEMENT,
                    params,
                    new RowBounds(offset, limit));
            } else {
                return executeSelectListDynamic(sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> executeSelectListDynamic(Class<?> clazz, String sql,
        int offset, int limit, Object params) {
        try {
            if (params instanceof Map) {
                ((Map<String, Object>) params).put(SQL, sql);
                return sqlSession.selectList(StatementKeyGenerator.generateSelectStatementKey(
                    clazz), params, new RowBounds(offset, limit));
            } else {
                return executeSelectListDynamic(clazz,
                    sql,
                    PropertyUtils.describe(params));
            }
        } catch (Exception e) {
            throw new MybatisException(e.getMessage(), e);
        }
    }

    @Override
    public SqlSession getSqlSession() {
        return sqlSession;
    }
}
