#!/bin/sh
mvn clean package && docker build -t edu.uchicago.cdis/indexd.server .
docker rm -f indexd.server || true && docker run -d -p 8080:8080 -p 4848:4848 --name indexd.server edu.uchicago.cdis/indexd.server 
