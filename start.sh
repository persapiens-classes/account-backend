#!/bin/sh

cd /home/springboot

echo ${1}
echo ${2}

export JAVA_OPTS="${2}"
su - springboot -c "java ${JAVA_OPTS} -Dspring.aot.enabled=true -jar application.jar" &

export DOMAIN="${1}"
sed "s/{{DOMAIN}}/$DOMAIN/" /etc/caddy/Caddyfile.template > /etc/caddy/Caddyfile

rm /etc/caddy/Caddyfile.template

caddy run --config /etc/caddy/Caddyfile