package org.jealvarez.sftpmockserver.sftp;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Collections.singletonList;

@Service
public class SftpServer {

    public static final int DEFAULT_SFTP_PORT = 2222;

    private final SshServer sshServer;

    public SftpServer() {
        this.sshServer = SshServer.setUpDefaultServer();
    }

    public void start() throws IOException {
        final Path publicKey = Files.createTempFile("publicKey", "pub");
        final Path sftpRemoteDirectoryPath = Paths.get(System.getProperty("java.io.tmpdir"));
        Files.createDirectories(sftpRemoteDirectoryPath);

        sshServer.setPort(DEFAULT_SFTP_PORT);
        sshServer.setPasswordAuthenticator((username, password, session) -> true);
        sshServer.setPublickeyAuthenticator((username, key, session) -> true);
        sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(publicKey));
        sshServer.setFileSystemFactory(new VirtualFileSystemFactory(sftpRemoteDirectoryPath.toAbsolutePath()));
        sshServer.setSubsystemFactories(singletonList(new SftpSubsystemFactory()));

        sshServer.stop();
        sshServer.start();
    }

    public void stop() throws Exception {
        sshServer.stop();
    }

    public boolean isStarted() {
        return sshServer.isStarted();
    }

}
