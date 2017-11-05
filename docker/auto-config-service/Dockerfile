FROM java
WORKDIR .
ADD . /auto-config-service
COPY auto-config-service /auto-config-service/bin/
EXPOSE 8080
VOLUME [ "/etc/auto-config-service" ]
ENTRYPOINT [ "bash", "/auto-config-service/bin/auto-config-service" ]