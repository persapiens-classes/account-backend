FROM eclipse-temurin:24_36-jre-alpine AS updated

# upgrade libs
# add springboot user
# copy uber jar
# extract layers
RUN apk -U upgrade --no-cache \
    && adduser -D springboot

USER springboot


FROM updated AS builder

WORKDIR /home/springboot

# copy uber jar
COPY --chown=springboot target/*-SNAPSHOT.jar target/application.jar

# extract uber jar
# copy dependencies and application layers
# create appcds file
RUN java -Djarmode=tools -jar target/application.jar extract --layers --destination target/application \
    && cp -r target/application/dependencies/* ./ \
    && cp -r target/application/application/* ./ \
    && java -Dspring.aot.enabled=true -XX:ArchiveClassesAtExit=application.jsa -Dspring.context.exit=onRefresh -jar application.jar


FROM updated AS final

LABEL MANTAINER="persapiens@gmail.com"

# expose application port
EXPOSE 8080

# copy application libs
COPY --chown=springboot --from=builder /home/springboot/lib  ./lib/
# copy application jar and appcds
COPY --chown=springboot --from=builder /home/springboot/application.jar /home/springboot/*.jsa ./

# run app
ENTRYPOINT ["java", "-Dspring.aot.enabled=true", "-jar", "application.jar"]
