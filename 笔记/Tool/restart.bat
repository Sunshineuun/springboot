rem 杀死springboot.exe进程，如果不存在报错，忽视即可。
taskkill /im springboot.exe /f
rem 启动jar包，路径自己指定
D:\qiushengming\java\jdk1.8.0_91\bin\springboot -jar "D:\Program Files (x86)\Jenkins\workspace\springboot-custom\target\springboot-0.0.1-SNAPSHOT.jar"
pause
