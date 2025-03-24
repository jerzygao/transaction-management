#!/bin/bash

docker build -t transaction-management .
docker run -p 8080:8080 transaction-management