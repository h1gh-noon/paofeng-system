#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20230706.sql ./mysql/db
cp ../sql/ry_config_20220929.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../paofeng-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy paofeng-gateway "
cp ../paofeng-gateway/target/paofeng-gateway.jar ./ruoyi/gateway/jar

echo "begin copy paofeng-auth "
cp ../paofeng-auth/target/paofeng-auth.jar ./ruoyi/auth/jar

echo "begin copy paofeng-visual "
cp ../paofeng-visual/paofeng-monitor/target/paofeng-visual-monitor.jar  ./ruoyi/visual/monitor/jar

echo "begin copy paofeng-modules-system "
cp ../paofeng-modules/paofeng-system/target/paofeng-modules-system.jar ./ruoyi/modules/system/jar

echo "begin copy paofeng-modules-file "
cp ../paofeng-modules/paofeng-file/target/paofeng-modules-file.jar ./ruoyi/modules/file/jar

echo "begin copy paofeng-modules-job "
cp ../paofeng-modules/paofeng-job/target/paofeng-modules-job.jar ./ruoyi/modules/job/jar

echo "begin copy paofeng-modules-gen "
cp ../paofeng-modules/paofeng-gen/target/paofeng-modules-gen.jar ./ruoyi/modules/gen/jar

