# Dictionary

#### 介绍
基于Java的英汉电子词典

#### Derby使用说明
##### 1.下载Derby
[下载Derby](http://db.apache.org/derby/derby_downloads.html)

选择适合你的版本

我用的是db-derby-10.15.1.3-bin

解压到D:\Derby

##### 2.设置环境变量

变量名: DERBY_HOME
路径: D:\Derby\db-derby-10.15.1.3-bin

变量名: CLASSPATH
路径: %DERBY_HOME%\lib \derby.jar;%DERBY_HOME%\lib\derbyclient.jar;%DERBY_HOME%\lib\derbytools.jar;%DERBY_HOME%\lib\derbynet.jar

##### 3.检查安装信息

java org.apache.derby.tools.sysinfo

##### 4.测试 ij工具

java org.apache.derby.tools.ij

##### 5.启动服务

click server_start.bat

##### 6.为eclipse添加库

1.Java Build Path

2.Libraries -->  Add Extern JARS

3.Classpath -->  Path(derby.jar && derbyclient.jar && derbynet.jar && derbytools.jar)