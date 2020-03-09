package org.jealvarez.sftpmockserver.service;

import com.jcraft.jsch.SftpException;
import org.jealvarez.sftpmockserver.sftp.SftpClient;
import org.jealvarez.sftpmockserver.sftp.SftpServer;
import org.jealvarez.sftpmockserver.web.model.Sftp;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.jealvarez.sftpmockserver.sftp.SftpServer.DEFAULT_SFTP_PORT;

@Service
public class SftpService {

    private static final String REMOTE_ROOT_DIRECTORY = "sftp-mock-server";

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
                .rootRemoteDirectoryPath(REMOTE_ROOT_DIRECTORY)
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

    public boolean isStarted() {
        return sftpServer.isStarted();
    }

    public List<String> getRemoteDirectories() throws SftpException {
        final List<String> directories = newArrayList();
        sftpClient.getRemoteDirectories(REMOTE_ROOT_DIRECTORY, directories);

        return directories;
    }

}
