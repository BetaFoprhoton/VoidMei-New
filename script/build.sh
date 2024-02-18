#!/bin/bash
# 确保JDK 1.8和launch4j环境目录已配置
# 编译
mkdir bin
javac -encoding UTF-8 -d bin -classpath dep/* src/prog/* src/parser/* src/ui/*
# 打包
jar -cvfm VoidMei.jar MANIFEST.MF -C ./bin .
# launch4j打包为exe
launch4jc ./script/voidmeil4j.xml