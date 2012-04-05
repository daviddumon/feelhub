#!/bin/sh
echo 'deploy script argument : ' $1

bucket=elasticbeanstalk-us-east-1-952638247896
file=steambeat-$1.war

# upload war to S3 bucket
s3cmd put target/$file  s3://$bucket/$file

# create a new application for elastic beanstalk
/opt/elasticbeanstalk-cli/bin/elastic-beanstalk-create-application-version -a Steambeat -d $file -l $file -s elasticbeanstalk-us-east-1-952638247896/$file