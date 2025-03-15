#!/bin/sh

cd /home/springboot

echo ${DOMAIN}
echo ${JAVA_OPTS}

su - springboot -c "java ${JAVA_OPTS} -Dspring.aot.enabled=true -jar application.jar" &

sed "s/{{DOMAIN}}/$DOMAIN/" /etc/caddy/Caddyfile.template > /etc/caddy/Caddyfile

rm /etc/caddy/Caddyfile.template

caddy run --config /etc/caddy/Caddyfile