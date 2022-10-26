#!/bin/sh

echo "The application will start in ${PRELOAD_SLEEP}s..." && sleep ${PRELOAD_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.sergey.zhuravlev.social.SocialApplication"  "$@"
