#/bin/sh

bucket=elasticbeanstalk-us-east-1-952638247896
file=steambeat-$0.war

# upload war to S3 bucket
s3cmd put $file  s3://$bucket/$file

# create a new application for elastic beanstalk
elastic-beanstalk-create-application-version -a Steambeat -d $file -l $file -s elasticbeanstalk-us-east-1-952638247896/$file