package com.qiushengming.dao;

import com.qiushengming.mybatis.ObjectSession;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
@Component
public class MinnieDaoImpl
    implements MinnieDao {

    @Resource(name = "objectSession")
    private ObjectSession session;


    /**
     * 依据ID查询
     *
     * @param id    id
     * @param clazz 类
     * @return clazz的实例
     */
    @Override
    public <T> T getById(Serializable id, Class<T> clazz) {
        return null;
    }

    /**
     * @param clazz class
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


    /**
     * 持久化对象
     *
     * @param o 被持久化的对象
     * @return 失败返回0;成功返回1
     */
    @Override
    public <T> int add(T o) {
        return session.insert(o);
    }

    /**
     * 更新对象
     *
     * @param o 被更新的对象
     * @return 失败返回0;成功返回1
     */
    @Override
    public <T> int update(T o) {
        return session.update(o);
    }

    /**
     * 删除对象
     *
     * @param o  被删除的对象
     * @param id id
     * @return 失败返回0;成功返回1
     */
    @Override
    public <T> int delete(Serializable id, T o) {
        return session.deleteById(id, o.getClass());
    }
}
