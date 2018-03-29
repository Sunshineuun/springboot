## 0329
1. 项目创建
1.1 JDK-1.8;
1.2 springboot-2.0
2. HelloWorld演示
2.1 问题
```$xslt
1. springboot会把引入的组件都通过自动配置类进行配置，
所以刚刚启动一直报错的，故此没有用到的包就不用引用进来。
2. 部分自动配置类，可以通过以下的形式进行排除。
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
```