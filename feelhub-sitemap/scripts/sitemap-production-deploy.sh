#!/bin/sh
echo 'production-deploy script argument : ' $1

file=feelhub-sitemap-$1-jar-with-dependencies.jar
host=ubuntu@ec2-54-235-59-50.compute-1.amazonaws.com
identity_file=???/certifications/feelhub.pem

scp -i $identity_file target/$file $host:/home/ubuntu/sitemaps/feelhub-sitemap-jar-with-dependencies.jar