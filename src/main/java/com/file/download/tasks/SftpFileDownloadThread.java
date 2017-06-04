package com.file.download.tasks;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.file.download.utils.BeanNames;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Component(BeanNames.sftpFileDownloadThread)
@Scope("prototype")
public class SftpFileDownloadThread extends FileDownloadThread {

    static final Logger log = LogManager.getLogger(SftpFileDownloadThread.class.getName());
    private static Map<String, String> sftpCredentials;

    static {
        sftpCredentials = new HashMap<>();
        sftpCredentials.put("demo", "password");
    }

    @Value("${sftpFileDownloadFolder}")
    private String sftpFileDownloadFolder;

    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName()+" Start sftp. File name = " + fileName);

            URI uri = new URI(fileName);

            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(uri.getUserInfo(), uri.getHost(), uri.getPort());
            session.setPassword(sftpCredentials.get(uri.getUserInfo()));    //use db to get credentials
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftpChannel = (ChannelSftp) channel;
            String targetFilePath = sftpFileDownloadFolder + File.separator + FilenameUtils.getName(uri.getPath());
            sftpChannel.get(uri.getPath(), targetFilePath );
            sftpChannel.exit();
            session.disconnect();

            log.info(Thread.currentThread().getName()+" End sftp.");
        } catch (Exception e) {
            log.error("Error while downloading file from source {}", fileName, e);
            failedRecordsService.addToFailedRecords(fileName);
        }
    }
}
