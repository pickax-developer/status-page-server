#!/bin/bash

scp -i $PEM_LOCATION ./build/libs/status-page-server-0.0.1-SNAPSHOT.jar ec2-user@ec2-3-39-235-105.ap-northeast-2.compute.amazonaws.com:/home/ec2-user


