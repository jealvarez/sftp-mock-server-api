FROM openjdk:11

LABEL maintainers="Jorge Alvarez <alvarez.jeap@gmail.com>"

COPY sftp-mock-server.jar /opt/apps/sftp-mock-server/sftp-mock-server.jar

EXPOSE 2221 2222

RUN useradd -m sftp-mock-server

USER sftp-mock-server

CMD ["java", "-jar", "-Xms128m", "-Xmx128m", "/opt/apps/sftp-mock-server/sftp-mock-server.jar"]
