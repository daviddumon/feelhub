#!/bin/sh
scp -i ../certifications/steambeat.pem -P 22 ../../steambeat-application/migrations/008.js ubuntu@ec2-50-16-1-248.compute-1.amazonaws.com:

