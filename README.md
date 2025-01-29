# 🚧🚧正在施工🚧🚧
### （人手不够，速来增援（））
## 目标：
1. 将旧版VoidMei移植到Java 8以上版本。
2. 整理旧版本代码, 用Kotlin替换旧有Java代码。
3. (如果可以的话)使用Jetbrains Compose重写UI界面。



# VoidMei - 战争雷霆8111端口Java图形前端

# 工作原理
- 通过HTTP/GET请求读取127.0.01:8111端口中的飞行状态(state)以及飞行仪表(indicators)数据
- 解析离线拆包的气动模型文件(FM blkx)
- 处理/计算上述信息,以图形界面的形式呈现给用户


# 编译方式1: 命令行直接编译
**需确保JDK 1.8环境目录已配置**
- 下载release版本,将其他缺失的资源文件复制到本目录
```bash
# 创建目标文件目录
mkdir bin
# 编译
javac -encoding UTF-8 -d bin -classpath dep/* src/prog/* src/parser/* src/ui/*
# 打包
jar -cvfm VoidMei.jar MANIFEST.MF -C ./bin .
# 执行
java -jar VoidMei.jar
# 使用launch4j打包为exe, 确保launch4j环境目录已配置
launch4jc ./script/voidmeil4j.xml
# 在linux或者WSL下执行脚本打包发行版本
bash ./script/zip.sh
```

# 编译方式2: Eclipse IDE
- 使用eclipse导入工程,程序入口设置为app.java中的main函数
- 设置jdk/jre版本为1.8 (java 8)
- 导入外部UI库 weblaf-complete-1.29.jar
- 下载release版本,将其他缺失的资源文件复制到本目录
- 运行、调试或导出jar文件

# 编译方式3: VSCode IDE
- 安装java插件
- 下载release版本,将其他缺失的资源文件复制到本目录
- 打开本目录,选择app.java并点击运行或调试
- 在JAVA PROJECTS选项下点击export jar可导出可执行的jar文件

# 代码结构说明
由于编程过程比较随意,目前代码结构与变量命名比较混乱,后面有时间会调整
主要代码结构描述如下:
- src/prog/app.java - 程序入口
- src/prog/controller.java - 程序状态转换控制
- src/prog/service.java - HTTP数据请求与数据处理线程,
- src/prog/uiThread.java - UI绘制线程
- src/parser - state/indicator/blkx等解析器代码
- src/ui - 各ui界面的绘制代码
- src/ui/mainform.java - 主界面
- src/ui/minimalHUD.java - 最小HUD界面
- src/ui/flightInfo.java - 飞行状态信息界面
- src/ui/engineInfo.java - 引擎状态信息界面
- src/ui/stickValue.java - 飞行控制信息界面
- src/ui/engineControl.java - 发动机控制信息界面

# 执行环境
- 安装 Jave Runtime Environment 1.8(jre 1.8) 即可

## Windows命令行模式安装VoidMei
打开非管理员模式的终端[按下WIN+R-输入cmd-按下回车]，跳出终端窗口后输入以下命令

先安装scoop(如果已安装可跳过)
```
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
irm get.scoop.sh | iex
```

设置代理(如果网络无问题可以直接跳过)
```
scoop config proxy [ip:port]
```

安装git(如果已安装可跳过)
```
scoop install git
```

添加@Lustra-Fs大佬提供的bucket
```
scoop bucket add Lutra-Fs_scoop-bucket https://github.com/Lutra-Fs/scoop-bucket
```

安装Voidmei，安装完成后开始菜单中应该能看到VoidMei可执行文件
```
scoop install Lutra-Fs_scoop-bucket/voidmei
```

版本升级请用该命令
```
scoop update voidmei
```

## Linux执行环境配置 
VoidMei可使用Linux wine执行(测试环境Fedora 35, GNOME 41.7, Wine 7.10),执行步骤如下: 
- winecfg 兼容性设置为win10 
- 安装jre8, 执行wine jre-8uXXX-windows-x64.exe /s
- 运行VoidMei
