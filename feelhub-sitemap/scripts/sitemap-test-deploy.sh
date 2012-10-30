#!/bin/sh
scp -P 22 target/feelhub-sitemap-0.0.1-SNAPSHOT.war root@10.0.0.7:/opt/tomcat/test.bytedojo.com/sitemaps.war
ssh root@10.0.0.7 'chmod +x /opt/tomcat/test.bytedojo.com/sitemaps.war'