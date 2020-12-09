package diagnostics;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class SSHHandler
{
    private JSch jsch = new JSch();

    private String user;
    private String host;
    private String password;

    private int port = 22;
    private int timeOut = 60000;


    public SSHHandler(String user, String host, String password)
    {
        this.user = user;
        this.host = host;
        this.password = password;
    }

    private Session session;

    private static final Logger logger = LoggerFactory.getLogger(SSHHandler.class);

    public String sendOverSSH(String cmd) throws JSchException, IOException
    {
        Channel channel = session.openChannel("exec");

        StringBuilder cmdOutput = new StringBuilder();

        ((ChannelExec) channel).setCommand(cmd);

        InputStream cmdStream = channel.getInputStream();

        channel.connect();

        int readByte = cmdStream.read();

        while (readByte != 0xffffffff)
        {
            cmdOutput.append((char) readByte);
            readByte = cmdStream.read();
        }
        channel.disconnect();

        return cmdOutput.toString();
    }

    public void connect() throws JSchException
    {
        session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect(timeOut);

        logger.info("Successfully connected to " + user + "@" + host + ":" + port);
    }

    public void disconnect()
    {
        session.disconnect();

        logger.info("Disconnected from " + user + "@" + host + ":" + port);
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
