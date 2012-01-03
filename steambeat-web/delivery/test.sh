#/bin/sh
scp -P 22 target/steambeat-web-0.0.1-SNAPSHOT.war root@10.0.0.7:/opt/tomcat/test.bytedojo.com/steambeat.war
scp -P 22 delivery/drop.js root@10.0.0.7:
ssh root@10.0.0.7 'chmod +x /opt/tomcat/test.bytedojo.com/steambeat.war'
ssh root@10.0.0.7 'mongo /root/drop.js steambeat'