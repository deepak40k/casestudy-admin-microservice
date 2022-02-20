#!/bin/bash
cd /home/ec2-user/app
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -s -c file:amazon-cloudwatch-agent.json
sudo nohup java -jar -Dspring.rabbitmq.addresses=$mqUri -Dspring.rabbitmq.username=$mqUserName -Dspring.rabbitmq.password=$mqPwd -Dspring.data.mongodb.uri=$mongoDBUri -Dspring.data.mongodb.database=admin -Damazon.aws.accessKey=$accessKey -Damazon.dynamodb.endpoint=$secretKey -Damazon.dynamodb.region=$region -Dmemcached.port=$memcachedPort -Dmemcached.configEndpoint=$memcachedEndpoint -Dserver.port=80 *.jar 2> /dev/null &