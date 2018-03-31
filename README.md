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
