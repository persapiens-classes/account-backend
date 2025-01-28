#!/bin/sh

cd /home/springboot

su - springboot -c "java -Dspring.aot.enabled=true -jar application.jar" &

caddy run --config /etc/caddy/Caddyfile