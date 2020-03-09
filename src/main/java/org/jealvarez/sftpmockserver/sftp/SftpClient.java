package org.jealvarez.sftpmockserver.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.jcraft.jsch.ChannelSftp.SSH_FX_NO_SUCH_FILE;

@Data
@Builder
public class SftpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClient.class);

    private final String rootRemoteDirectoryPath;
    private final String server;
    private final int port;
    private final String username;
    private final String password;
    private final String privateKey;
    private final String privateKeyPassphrase;

    private JSch jsch;
    private Session session;
    private Channel channel;
    private ChannelSftp sftpChannel;

    public void connect() throws JSchException {
        jsch = new JSch();

        final Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        properties.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
        properties.put("compression.s2c", "zlib@openssh.com,zlib,none");
        properties.put("compression.c2s", "zlib@openssh.com,zlib,none");

        session = jsch.getSession(username, server, port);
        session.setConfig(properties);
        session.setPassword(password);
        session.connect();

        channel = session.openChannel("sftp");
        channel.connect();

        sftpChannel = (ChannelSftp) channel;
    }

    public void uploadFile(final String sourcePath, final String destinationPath) throws Exception {
        if (sftpChannel == null || session == null || !session.isConnected() || !sftpChannel.isConnected()) {
            throw new Exception("Connection to server is closed. Open it first.");
        }

        sftpChannel.put(sourcePath, destinationPath);
    }

    public void retrieveFile(final String sourcePath, final String destinationPath) throws Exception {
        if (sftpChannel == null || session == null || !session.isConnected() || !sftpChannel.isConnected()) {
            throw new Exception("Connection to server is closed. Open it first.");
        }

        sftpChannel.get(sourcePath, destinationPath);
    }

    public boolean existsFile(final String path) {
        List<String> files = null;
        try {
            files = sftpChannel.ls(path);
        } catch (final SftpException sftpException) {
            if (sftpException.id == SSH_FX_NO_SUCH_FILE) {
                return false;
            }
        }
        return files != null && !files.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public void cleanRemoteRootDirectory(final String remoteDirectoryPath) throws Exception {
        if (sftpChannel == null || session == null || !session.isConnected() || !sftpChannel.isConnected()) {
            throw new Exception("Connection to server is closed. Open it first.");
        }

        final List<ChannelSftp.LsEntry> entries = sftpChannel.ls(remoteDirectoryPath);
        for (final ChannelSftp.LsEntry entry : entries) {
            if (!(entry.getFilename().equals(".") || entry.getFilename().equals(".."))) {
                if (entry.getAttrs().isDir()) {
                    cleanRemoteRootDirectory(remoteDirectoryPath + entry.getFilename() + File.separator);
                } else {
                    sftpChannel.rm(remoteDirectoryPath + entry.getFilename());
                }
            }
        }
    }

    public void getRemoteDirectories(final String path, final List<String> directories) throws SftpException {
        final List<ChannelSftp.LsEntry> entries = sftpChannel.ls(path);
        for (final ChannelSftp.LsEntry entry : entries) {
            if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                directories.add(path + "/" + entry.getFilename());
                getRemoteDirectories(path + "/" + entry.getFilename(), directories);
            }
        }
    }

    public void createRemoteDirectory(final String pathStructureToCreate) {
        final String[] rootPathStructure = StringUtils.split(rootRemoteDirectoryPath + pathStructureToCreate, "/");
        final StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(rootPathStructure).forEach(path -> {
            stringBuilder.append("/").append(path);
            final String pathToCreate = stringBuilder.toString();
            try {
                sftpChannel.stat(pathToCreate);
            } catch (final SftpException sftpException) {
                try {
                    sftpChannel.mkdir(pathToCreate);
                } catch (final SftpException sftpExceptionMkdir) {
                    LOGGER.error("Unable to create directories={}", pathToCreate, sftpExceptionMkdir);
                }
            }
        });
    }

    public void disconnect() {
        if (sftpChannel != null) {
            sftpChannel.disconnect();
        }

        if (channel != null) {
            channel.disconnect();
        }

        if (session != null) {
            session.disconnect();
        }
    }

}
