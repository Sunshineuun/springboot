package com.qiushengming.core.service;

import com.qiushengming.entity.BaseEntity;
import com.qiushengming.mybatis.support.Criteria;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/24
 * @param <T>
 */
public interface ManagementService<T extends BaseEntity>
        extends QueryService<T> {
    /**
     * 新增
     *
     * @param baseDomain 实体
     * @return 新增成功的数量
     */
    int add(T baseDomain);

    /**
     * 修改
     *
     * @param baseDomain 实体
     * @return 修改成功的数量
     */
    int update(T baseDomain);

    /**
     * 删除，根据ID删除
     *
     * @param id 实体的ID
     * @return 删除成功的数量
     */
    int deleteById(String id);

    /**
     * 根据条件删除,通过{@link Criteria}组装条件
     *
     * @param criteria {@link Criteria}
     */
    void deleteByCriteria(Criteria criteria);

    /**
     * 批量新增
     *
     * @param baseDomains 实体列表
     * @return 新增成功的数量
     */
    int add(List<T> baseDomains);

    /**
     * 批量更新
     *
     * @param baseDomains 实体列表
     * @return 修改成功的数量
     */
    int update(List<T> baseDomains);

    /**
     * 批量删除
     *
     * @param baseDomains 实体列表
     * @return 删除成功的数量
     */
    int delete(List<T> baseDomains);
}
