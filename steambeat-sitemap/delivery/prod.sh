#/bin/sh
scp -P 22 -i aws/certifications/steambeat.pem target/steambeat-sitemap-0.0.1-SNAPSHOT.war ubuntu@23.21.113.241:/var/lib/tomcat7/webapps/root.war
ssh -i aws/certifications/steambeat.pem ubuntu@23.21.113.241 'chmod +x /var/lib/tomcat7/webapps/root.war'