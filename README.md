## 0329
1. 项目创建 <br>
1.1 JDK-1.8; <br>
1.2 springboot-2.0 <br>
2. HelloWorld演示
2.1 问题
```$xslt
1. springboot会把引入的组件都通过自动配置类进行配置，
所以刚刚启动一直报错的，故此没有用到的包就不用引用进来。
2. 部分自动配置类，可以通过以下的形式进行排除。
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
```

### 0330
1. 完成一个中国所有城市的查询 <br>
1.1 目标：建立城市信息查询系统 <br>
2. 集成mybatis：等待完成 <br> 
2.1 mybatis通过maven自动构建xml，mapper，bean插件安装 <br>
2.2 maven的mybatis插件安装。完成。
2.3 进行配置，配置文件最好引用properties文件。 ing
3. 集成阿里的数据源

### 0331
1. mybatis-generator运行错误
```
[ERROR] Failed to execute goal org.mybatis.generator:mybatis-generator-maven-plugin:1.3.2:generate 
(default-cli) on project springboot: CLIENT_PLUGIN_AUTH is required -> [Help 1]
```
原因未知；但是将generatorConfig.xml中的mysql驱动改为`5.1.30`就能正常使用了。
此处将`5.1.30`版本的mysql驱动放大`\resources\static`下
2. 配置mybatis
2.1 MybatisTransactionConfig，sqlsession创建
2.2 MyBatisMapperScannerConfig，提供扫描mapper接口
3. 完成用户新增
4. 集成redis
5. redis缓存更新策略。//TODO
总结：mybatis，redis需要进阶了解。


### 0402
1. 在使用springboot redis注解方式的缓存。使用`Cacheable`,`CachePut`时，缓存的都是return的结果。 <br>
1.1 @Cacheable：缓存切入点，key不存在，存储到缓存中；key存在则取出数据。缓存方法返回结果。<br> 
1.2 @CachePut：更新切入点，key不存在，存储到缓存中；key存在则更新数据。缓存方法返回结果。<br> 
1.3 @CacheEvict：清除切入点，根据key删除缓存。<br> 
1.3.1 allEntries：删除所有缓存 <br> 

2. redis分布式管理

### 0403
1. Mybatis简单级联查询处理
2. ActiveMQ集成 <br>
2.1 坑爹的是JMS的读取端口跟文本端口不一样，JSM上配置的端口是`61616` Web登陆的端口`8161` <br>

### 0409-0413
1. springboot是如何启动的

### 0414
1. Mybatis动态sql，基于注解。
2. 学习了springboot自动配置的机制

### 0416
1. AOP
2. 想实现数据跟踪

### 0427
1. Mybatis优化，将原有的配置信息，写到数据库中去。

### 0515
1. 统一异常处理`ExceptionHandle.java`

### 0528
1. 多数据源配置

### 0530
1. GridView，增加行编辑模式


### 0609
1. 前端新增修改功能
    1.1 右键菜单栏
        1.1.1 菜单栏按钮添加
            新增按钮
            删除按钮
        1.1.2 菜单栏按钮触发
            新增按钮：
                逻辑：在首行插入空行。怎么标识是否新增，当ID为空的情况，认为是新增。
            删除按钮：
                逻辑：删除选中的当前行；选中记录为空进行错误提示。
        1.1.3 菜单栏权限控制

### 0614
1. Ext JS组件的ID不能已数字开头
2. 后端排序，后端筛选，后端分组
3. Ext分组插件无法使用调试
