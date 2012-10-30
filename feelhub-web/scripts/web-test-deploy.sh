#!/bin/sh
echo 'test-deploy script argument : ' $1
file=feelhub-$1.war
scp -P 22 target/$file root@10.0.0.7:/opt/tomcat/test.bytedojo.com/feelhub.war
scp -P 22 scripts/drop.js root@10.0.0.7:
ssh root@10.0.0.7 'chmod +x /opt/tomcat/test.bytedojo.com/feelhub.war'
ssh root@10.0.0.7 'mongo feelhub /root/drop.js'