package org.jealvarez.sftpmockserver.service;

import org.jealvarez.sftpmockserver.sftp.SftpClient;
import org.jealvarez.sftpmockserver.sftp.SftpServer;
import org.jealvarez.sftpmockserver.web.model.Sftp;
import org.springframework.stereotype.Service;

import static org.jealvarez.sftpmockserver.sftp.SftpServer.DEFAULT_SFTP_PORT;

@Service
public class SftpService {

    private final SftpServer sftpServer;
    private SftpClient sftpClient;

    public SftpService(final SftpServer sftpServer) {
        this.sftpServer = sftpServer;
    }

    public void start(final Sftp sftp) throws Exception {
        if (!sftpServer.isStarted()) {
            sftpServer.start();
        }

        sftpClient = SftpClient
                .builder()
                .rootRemoteDirectoryPath(sftp.getRootDirectory())
                .server(sftp.getHostname())
                .port(DEFAULT_SFTP_PORT)
                .username("fakeUsername")
                .password("fakePassword")
                .build();

        sftpClient.connect();
        sftp.getPathsToCreate().forEach(sftpClient::createRemoteDirectory);
    }

    public void stop() throws Exception {
        sftpServer.stop();
    }

}
