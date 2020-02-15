# SFTP Mock Server

Emulate sftp server through java application.

**IMPORTANT. THIS IS ONLY FOR TESTING PURPOSES, PLEASE DON'T USE IT FOR PRODUCTION ENVIRONMENT.**

## Prerequisites

* Java 11 

## Build

1. Execute in the root project

```sh
mvn clean package docker:build
```

2. Run the container

```sh
docker run --rm --name sftp-mock-server-api -p 0.0.0.0:2222:2222 -p 0.0.0.0:2221:2221 -d sftp-mock-server-api:latest ; docker logs -f sftp-mock-server-api 
```

## Documentation

Endpoints documentation through swagger at [http://localhost:2221](http://localhost:2221)

## How to use it?

1. Get the `hostname` from the machine

```sh
[joalvarez@local ~]$ hostname
joalvarez.local
```

2. Starts the sftp server

```sh
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{ 
   "root_directory": "/", 
   "hostname": "${HOSTNAME}", 
   "paths_to_create": [ 
     "/test/inbound", 
     "/test/outbound/" 
   ] 
 }' -k 'http://${HOSTNAME}:2221/sftp/start'
```

3. Stops the sftp server

```sh
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -k 'http://${HOSTNAME}:2221/sftp/stop'
```

4. To verify the sftp server is running

    4.1 User/Password 
    
    ```sh
    sftp -P 2222 whaterver@${HOSTNAME}
    ```
   
   4.2 Public/Private key. **Note. You need to provide a valid ssh key or create a new one using the command `ssh-keygen`**
   
   ```sh
   ssh-keygen -t rsa -f ~/.ssh/test_key -q -N ""
   sftp -P 2222 -i  ~/.ssh/test_key whaterver@${HOSTNAME}
   ```
