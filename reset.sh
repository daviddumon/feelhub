#!/bin/sh
mongo feelhub --eval "db.dropDatabase();db.copyDatabase('backup','feelhub');"