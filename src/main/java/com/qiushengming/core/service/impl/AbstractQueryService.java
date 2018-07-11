package com.qiushengming.core.service.impl;

import com.qiushengming.annotation.Table;
import com.qiushengming.core.service.PagingService;
import com.qiushengming.core.service.QueryService;
import com.qiushengming.dao.MinnieDao;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.entity.code.Page;
import com.qiushengming.exception.SystemException;
import com.qiushengming.mybatis.support.Criteria;
import com.qiushengming.utils.GenericsUtils;
import com.qiushengming.utils.GridSQLBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.qiushengming.utils.ReflectionUtils.wrapProperty;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public abstract class AbstractQueryService<T extends BaseEntity>
    implements QueryService<T>, PagingService {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MinnieDao dao;

    private Class entityClass;

    MinnieDao getDao() {
        return dao;
    }

    protected Class<T> getEntityClass() {
        if (entityClass == null) {
            entityClass =
                GenericsUtils.getSuperClassGenricType(this.getClass());
        }
        return entityClass;
    }


    /**
     * @return T的集合
     */
    @Override
    public List<T> getAll() {
        return getDao().getAll(getEntityClass());
    }

    /**
     * 通过ID进行查询
     *
     * @param id 实体ID
     * @return 实体
     */
    @Override
    public T getById(Serializable id) {
        return getDao().getById(id, getEntityClass());
    }

    @Override
    public List<T> queryByCriteria(Criteria criteria) {
        return getDao().queryByCriteria(criteria, getEntityClass());
    }

    @Override
    public T queryOneByCriteria(Criteria criteria) {
        return getDao().queryOneByCriteria(criteria, getEntityClass());
    }

    @Override
    public int countByCriteria(Criteria criteria) {
        return getDao().countByCriteria(criteria, getEntityClass());
    }

    @Override
    public Page findOnPage(Map<String, Object> params, Page<?> page)
        throws SystemException {
        return findOnPage(getCountSql(params),
            getSearchSql(params),
            params,
            page);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Page<?> findOnPage(String countSql, String searchSql,
        Map<String, Object> params, Page page) {
        GridSQLBuilder.buildSqlFragmentAndParams(params, page, entityClass);

        int totalCount;
        if (!StringUtils.isEmpty(countSql)) {
            countSql = addConditions(countSql, params);

            // 查询数据库条数
            totalCount = getDao().countBySql(countSql, params);
            page.setTotalCount(totalCount);

            // 如果查询的条数为0,则无需再查询数据，直接返回
            if (totalCount == 0) {
                logger.debug("查询结果数量为空，不在进行下一步查询！");
                return page;
            }
        }

        // 查询数据
        StringBuilder searchSqlBuilder =
            new StringBuilder(addConditions(searchSql, params));

        if (StringUtils.isNotEmpty(page.getOrderBy())) {
            if (StringUtils.isNotEmpty(page.getOrder())) {
                searchSqlBuilder.append(" order by ")
                    .append(wrapProperty(page.getOrderBy(), entityClass))
                    .append(" ")
                    .append(page.getOrder())
                    .append(",1,2");
            } else {
                searchSqlBuilder.append(" order by ")
                    .append(wrapProperty(page.getOrderBy(), entityClass))
                    .append(" asc ")
                    .append(",1,2");
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug(searchSqlBuilder.toString());
        }

        page.setResult(getDao().queryBySql(getEntityClass(),
            searchSqlBuilder.toString(),
            params,
            page.getPageSize() * (page.getPageNo() - 1),
            page.getPageSize()));

        // 对查询结果进行加工
        findResultWarped(page);

        return page;
    }

    /**
     * 加工查询出的数据
     *
     * @param page 结果
     */
    protected void findResultWarped(Page<?> page) {
    }

    /**
     * 获取查询语句
     *
     * @param params 参数
     * @return search sql
     * @throws SystemException 系统错误
     */
    protected String getSearchSql(Map<String, Object> params)
        throws SystemException {
        Class<T> clazz = getEntityClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (StringUtils.isEmpty(table.selectSql())) {
                return "SELECT * FROM " + table.value();
            } else {
                return table.selectSql();
            }
        } else {
            throw new SystemException(clazz.getName() + " 没有添加  Table 注解");
        }
    }

    /**
     * 获取总数查询语句
     *
     * @param params 阐述
     * @return count sql
     * @throws SystemException 系统错误
     */
    protected String getCountSql(Map<String, Object> params)
        throws SystemException {
        Class<T> clazz = getEntityClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (StringUtils.isEmpty(table.selectSql())) {
                return "SELECT COUNT(1) FROM " + table.value();
            } else {
                return "SELECT COUNT(1) FROM (" + table.selectSql() + ")";
            }
        } else {
            throw new SystemException(clazz.getName() + " 没有添加  Table 注解");
        }
    }

    private String addConditions(String sql, Map<String, Object> params) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        String sqlFragment = (String) params.get(GridSQLBuilder.SQLFRAGMENT);
        if (StringUtils.isNotEmpty(sqlFragment)) {
            sqlBuilder.append(" WHERE ").append(sqlFragment);
        }
        return sqlBuilder.toString();
    }
}
