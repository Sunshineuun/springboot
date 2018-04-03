package com.qiushengming.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * 要缓存的 Java 对象必须实现 Serializable 接口，因为 Spring 会将对象先序列化再存入 Redis。
 * 如果不实现 Serializable 的话将会遇到类似这种错误
 * <code>
 *     DefaultSerializer requires a Serializable payload but received an
 *     object of type [com.qiushengming.entity.User]
 * </code>
 * @author MinMin
 * @date 18年03月31日
 */
public class BaseEntity implements Serializable {

    /**
     * 由UUID构成
     */
    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}