FROM openjdk:17
WORKDIR /app
RUN ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime \
&& mkdir -p /logs
EXPOSE 8182
COPY target/auth-server.jar auth-server.jar
ENTRYPOINT ["java","-jar","dsmovie.jar"]