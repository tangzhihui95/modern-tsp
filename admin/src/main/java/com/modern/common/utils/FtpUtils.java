package com.modern.common.utils;

import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/15 12:44
 */
@Slf4j
public class FtpUtils {

    public static void main(String[] args) {
        // 部署后端jar
        uploadFileFTP("39.98.85.96", 22, "root", "bMz7Tccc","/mydata/TSP", "D:\\modern\\TSP\\admin\\target\\TSP-2.5.5.jar");
        // 部署vue
        uploadFileFTP("39.98.85.96", 22, "root", "bMz7Tccc","/mydata/TSP", "D:\\modern\\TSP\\admin\\target\\TSP-2.5.5.jar");
    }

    /**
     * 向FTP服务器上传文件
     *
     * @param host        FTP服务器ip
     * @param port        FTP服务器端口
     * @param username    FTP登录账号
     * @param password    FTP登录密码
     * @param basePath    FTP服务器基础目录,/home/data
     * @param filePath    FTP服务器文件存放路径
     * @param filename    上传到FTP服务器上的文件名
     * @param inputStream 输入流
     * @return 成功返回true，否则返回false
     */
    private static boolean uploadFileSFTP(String host, int port, String username, String password, String basePath,
                                          String filePath, String filename, InputStream inputStream) {
        boolean result;
        FTPClient ftp = new FTPClient();
        try {
            JSch jsch = new JSch();
            // 获取sshSession  账号-ip-端口
            Session sshSession = jsch.getSession(username, host, port);
            // 添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            // 严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            // 开启sshSession链接
            sshSession.connect();
            // 获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            // 开启
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            // 设置为被动模式
            ftp.enterLocalPassiveMode();
            // 设置上传文件的类型为二进制类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 进入到要上传的目录  然后上传文件
            sftp.cd(basePath + filePath);
            // sftp put为上传，get为下载，之后会出一篇下载的代码
            sftp.put(inputStream, filename);
            inputStream.close();
            result = true;
        } catch (Exception e) {
            log.error("FTP服务器 文件上传失败 失败原因：{}", e.getMessage(), e);
            result = false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    log.error("FTP服务器 关闭失败 失败原因：{}", e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     *
     * @param filePath 文件全路径
     * @param ftpPath 上传到目的端目录
     * @param username
     * @param password
     * @param url
     * @param port
     */
    private static void uploadFileFTP(String url, int port, String username, String password,
                                         String ftpPath, String filePath) {
        FileInputStream input = null;
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            //获取session  账号-ip-端口
            com.jcraft.jsch.Session sshSession = jsch.getSession(username, url, port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启session连接
            sshSession.connect();
            //获取sftp通道
            sftp = (ChannelSftp) sshSession.openChannel("sftp");
            //开启
            sftp.connect();
            //文件乱码处理
            /*Class<ChannelSftp> c = ChannelSftp.class;
            Field f = c.getDeclaredField("server_version");
            f.setAccessible(true);
            f.set(sftp, 2);
            sftp.setFilenameEncoding("GBK");*/
            //判断目录是否存在
            try {
                Vector ls = sftp.ls(ftpPath); //ls()得到指定目录下的文件列表
                /*if (ls == null) {   //ls不会为null，哪怕它是一个空目录
                    sftp.mkdir(ftpPath);
                }*/
            } catch (SftpException e) {
                sftp.mkdir(ftpPath);
            }
            sftp.cd(ftpPath);
            String filename = filePath.substring(filePath.lastIndexOf(File.separator) + 1); //附件名字
            //filename = new String(filename.getBytes("GBK"), StandardCharsets.ISO_8859_1);
            input = new FileInputStream(new File(filePath));
            log.info(">>>>>>>>>>>>>>>>>文件上传中:{}>>>>>>>>>>>>>>>>",filename);
            sftp.put(input, filename);
            //设定777权限，转为8进制放入chmod中
            //sftp.chmod(Integer.parseInt("777", 8), ftpPath + filename);
            input.close();
            sftp.disconnect();
            sshSession.disconnect();
            log.info("================上传成功:{}==================",filename);
        } catch (Exception e) {
            log.error("上传文件失败！", e);
            e.printStackTrace();
        }
    }
    /**
     * @param directory    SFTP服务器的文件路径
     * @param downloadFile SFTP服务器上的文件名
     * @param saveFile     保存到本地路径
     * @param username
     * @param password
     * @param host
     * @param port
     */
    public static void downloadFile(String directory, String downloadFile, String saveFile, String username, String password, String host, Integer port) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            //获取session  账号-ip-端口
            com.jcraft.jsch.Session sshSession = jsch.getSession(username, host, port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启session连接
            sshSession.connect();
            //获取sftp通道
            sftp = (ChannelSftp) sshSession.openChannel("sftp");
            //开启
            sftp.connect();
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            FileOutputStream output = new FileOutputStream(new File(saveFile));
            log.info(">>>>>>>>>>>>>>>>>文件下载中:{}>>>>>>>>>>>>>>>>",downloadFile);
            sftp.get(downloadFile, output);
            output.close();
            sftp.disconnect();
            sshSession.disconnect();
            log.info("================下载成功:{}==================",downloadFile);
//            System.out.println("================下载成功！==================");
        } catch (SftpException | FileNotFoundException | JSchException e) {
            log.error("文件下载异常！", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
