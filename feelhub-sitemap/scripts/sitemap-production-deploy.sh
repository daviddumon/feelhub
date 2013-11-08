#!/bin/sh
echo 'production-deploy script argument : ' $1
echo $(pwd)

file=feelhub-sitemap-$1-jar-with-dependencies.jar

cp feelhub-sitemap/target/$file /opt/sitemaps/sitemaps-builder.jar