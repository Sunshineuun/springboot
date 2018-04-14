package com.qiushengming.mybatis.annotation.support;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Exclude;
import com.qiushengming.annotation.Id;
import com.qiushengming.annotation.Table;
import com.qiushengming.mybatis.support.ClassMap;
import com.qiushengming.utils.ReflectionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解配置信息
 *
 * @author qiushengming
 */
public final class AnnotationConfiguration {

    private final char A = 'A';
    private final char Z = 'Z';
    private final Configuration configuration;
    private static final Map<Class<?>, ClassMap> CLASS_MAP =
        new ConcurrentHashMap<>();

    AnnotationConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    void addAnnotatedClass(Class<?> clazz) {
        parseClassMap(clazz);
    }

    /**
     * 1. 如果属性的get or set 方法上没有增加<code>Column</code> or <code>Id</code>注解， <br>
     * 则会将属性本身的名字作为与表的映射名称
     * @param clazz 类
     */
    private void parseClassMap(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);

        List<Field> fields = ReflectionUtils.getFields(clazz);

        String readMethodName;
        String writeMethodName;
        Column column;
        Id id;

        ClassMap classMap = new ClassMap(clazz,
            table.value(),
            table.resultMapId(),
            table.selectSql());

        for (Field field : fields) {
            column = null;
            id = null;

            readMethodName =
                getReadMethodName(field.getName(), field.getType());
            Method readMethod =
                MethodUtils.getAccessibleMethod(clazz, readMethodName);

            if (readMethod == null) { continue; }

            if (readMethod.isAnnotationPresent(Exclude.class)) { continue; }

            if (readMethod.isAnnotationPresent(Column.class)) {
                column = readMethod.getAnnotation(Column.class);
            }

            if (readMethod.isAnnotationPresent(Id.class)) {
                id = readMethod.getAnnotation(Id.class);
            }

            writeMethodName = getWriteMethodName(field.getName());
            Method writeMethod = MethodUtils.getAccessibleMethod(clazz,
                writeMethodName,
                field.getType());

            if (writeMethod == null) { continue; }

            if (writeMethod.isAnnotationPresent(Exclude.class)) { continue; }

            if (writeMethod.isAnnotationPresent(Column.class)) {
                column = writeMethod.getAnnotation(Column.class);
            }

            if (writeMethod.isAnnotationPresent(Id.class)) {
                id = writeMethod.getAnnotation(Id.class);
            }

            if (id != null) {
                ClassMap.Property property = classMap.new Property(field.getName(),
                    field.getType(),
                    id.value().toUpperCase(),
                    true,
                    true);
                classMap.addIdProperty(property);
            }else if (column != null) {
                ClassMap.Property property = classMap.new Property(field.getName(),
                    field.getType(),
                    column.value().toUpperCase(),
                    column.isAdd(),
                    column.isUpdate());
                classMap.addProperty(property);
            }else {
                // column == null && id == null
                ClassMap.Property property = classMap.new Property(field.getName(),
                    field.getType(),
                    field.getName().toUpperCase(),
                    true,
                    true);
                classMap.addProperty(property);
            }
        }
        CLASS_MAP.put(clazz, classMap);
    }

    public static Map<Class<?>, ClassMap> getClassMap() {
        return CLASS_MAP;
    }

    public List<ClassMap> getClassMapList() {
        return new ArrayList<>(CLASS_MAP.values());
    }

    public static ClassMap getClassMap(Class<?> clazz) {
        return CLASS_MAP.get(clazz);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 获取所有属性的读方法 \n
     * 如果 type是 boolean类型，加上前缀<code>is</code>，否则加上<code>get</code>。
     *
     * @param propertyName 属性名称
     * @param type         属性类型
     * @return 读方法的名称
     */
    private String getReadMethodName(String propertyName, Class<?> type) {
        char c1 = propertyName.charAt(0);
        char c2 = propertyName.charAt(1);

        StringBuilder sb = new StringBuilder();
        if (type == boolean.class || type == Boolean.class) {
            sb.append("is");
        } else {
            sb.append("get");
        }
        /*
         * 如果c2是大写，则对c1不进行转换，否则将c1转成大写
         */
        if (c2 >= A && c2 <= Z) {
            sb.append(c1).append(propertyName.substring(1));
        } else {
            sb.append(Character.toUpperCase(c1))
                .append(propertyName.substring(1));
        }
        return sb.toString();
    }

    private String getWriteMethodName(String propertyName) {
        char c1 = propertyName.charAt(0);
        char c2 = propertyName.charAt(1);

        StringBuilder sb = new StringBuilder();

        if (c2 >= A && c2 <= Z) {
            sb.append("set").append(c1).append(propertyName.substring(1));
        } else {
            sb.append("set")
                .append(Character.toUpperCase(c1))
                .append(propertyName.substring(1));
        }
        return sb.toString();
    }
}
