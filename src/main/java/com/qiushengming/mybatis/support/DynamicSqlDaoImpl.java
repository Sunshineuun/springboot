package com.qiushengming.mybatis.support;

import com.qiushengming.exception.MybatisException;
import com.qiushengming.mybatis.DynamicSqlDao;
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

    private Map<String, SqlSession> sqlSessionMap;

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
        return executeSelectCountDynamic(sql, new HashMap<String, Object>());
    }

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

    @Override
    public <K, V> List<Map<K, V>> executeSelectListDynamic(String sql) {
        return executeSelectListDynamic(sql, new HashMap<String, Object>());
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
        return executeSelectOneDynamic(sql, new HashMap<String, Object>());
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
        return executeUpdateDynamic(sql, new HashMap<String, Object>());
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
        return executeInsertDynamic(sql, new HashMap<String, Object>());
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
        return executeDeleteDynamic(sql, new HashMap<String, Object>());
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
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public SqlSession getSqlSession(String source) {
        return sqlSessionMap.get(source);
    }

    public void setSqlSessionMap(Map<String, SqlSession> sqlSessionMap) {
        this.sqlSessionMap = sqlSessionMap;
    }
}
