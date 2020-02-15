FROM openjdk:11

LABEL maintainers="Jorge Alvarez <alvarez.jeap@gmail.com>"

COPY sftp-mock-server-api.jar /opt/apps/sftp-mock-server/sftp-mock-server-api.jar

EXPOSE 2221 2222

RUN useradd -m sftp-mock-server-api

USER sftp-mock-server-api

CMD ["java", "-jar", "-Xms128m", "-Xmx128m", "/opt/apps/sftp-mock-server/sftp-mock-server-api.jar"]
