#!/bin/sh
echo 'production-deploy script argument : ' $1

bucket=elasticbeanstalk-us-east-1-952638247896
file=feelhub-$1.war

# upload war to S3 bucket
s3cmd put target/$file  s3://$bucket/$file

# create a new application for elastic beanstalk
/opt/elasticbeanstalk-cli/api/bin/elastic-beanstalk-create-application-version -a feelhub -d $file -l $file -s elasticbeanstalk-us-east-1-952638247896/$file

# use the new version on elastic beanstalk
/opt/elasticbeanstalk-cli/api/bin/elastic-beanstalk-update-environment -e feelhub -l $file