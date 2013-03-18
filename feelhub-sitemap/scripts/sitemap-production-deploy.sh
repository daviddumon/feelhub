#!/bin/sh
echo 'production-deploy script argument : ' $1

file=feelhub-sitemap-$1-jar-with-dependencies.jar

cp target/$file /home/ubuntu/sitemaps/feelhub-sitemap-jar-with-dependencies.jar