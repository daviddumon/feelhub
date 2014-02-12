#!/bin/sh
echo 'production-deploy script argument : ' $1
file=feelhub-$1.war
cp target/$file /var/lib/tomcat7/feelhub/ROOT.war
chmod +x /var/lib/tomcat7/feelhub/ROOT.war
