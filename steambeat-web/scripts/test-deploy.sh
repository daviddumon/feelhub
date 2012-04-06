#!/bin/sh
echo 'test-deploy script argument : ' $1
file=steambeat-$1.war
scp -P 22 target/$file root@10.0.0.7:/opt/tomcat/test.bytedojo.com/steambeat.war
scp -P 22 delivery/drop.js root@10.0.0.7:
ssh root@10.0.0.7 'chmod +x /opt/tomcat/test.bytedojo.com/steambeat.war'
ssh root@10.0.0.7 'mongo steambeat /root/drop.js'