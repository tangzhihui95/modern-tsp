package com.modern.common.utils;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.*;

/**
 * <p>TODO</p>
 *
 * @author piaomiao
 * @version V1.0.0
 * @date 2022/8/15 17:32
 */

public class SFtpUtils {

    private String host;                        //主机名

    private int port;                           //端口

    private String userName;                    //ftp用户名

    private String passWord;                    //ftp密码

    private Session sshSession;                 //ssh会话

    private Channel channel;                    //通道

    private ChannelSftp csftp;                  //强转后的ftp通道

    public SFtpUtils(String host, int port, String userName, String passWord) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * 方法名:connect<br>
     * 作用：连接fth<br>
     *
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public boolean connect() {
        JSch jsch = new JSch();
        try {
            sshSession = jsch.getSession(userName, host, port);
            sshSession.setPassword(passWord);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);

            sshSession.connect();

            channel = sshSession.openChannel("sftp");
            channel.connect();

            csftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
            disconnect();
            return false;
        }
        return true;
    }

    /**
     * 方法名:disconnect<br>
     * 作用：断开ftp连接<br>
     *
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public void disconnect() {
        if (csftp != null && csftp.isConnected()) {
            csftp.disconnect();
        }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (sshSession != null && sshSession.isConnected()) {
            sshSession.disconnect();
        }
    }

    /**
     * 方法名:readFile<br>
     * 作用：获得读取文件的输入流<br>
     *
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public InputStream readFile(String filePath) {
        try {
            return csftp.get(filePath);
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();
            return null;
        }
    }

    /**
     * 获取ftp指定路径下所有文件的 文件路径
     */
    public List<String> listFilesUrl(String dirPath) {
        List<String> list = new ArrayList<String>();
        Vector<?> files = null;
        try {
            files = csftp.ls(dirPath);
            Iterator it = files.iterator();
            while (it.hasNext()) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                String fileName = entry.getFilename();
                if (".".equals(fileName.trim()) || "src/main".equals(fileName) || "..".equals(fileName)) {
                    it.remove();
                    continue;
                }
//              System.out.println(fileName);
                list.add(dirPath + "/" + fileName);
            }
        } catch (SftpException e) {
            e.printStackTrace();
            disconnect();
        } catch (Exception e1) {
            e1.printStackTrace();
            disconnect();
        }
        return list;
    }

    /**
     * 方法名:listFiles<br>
     * 作用：取得目录下的文件或目录列表<br>
     *
     * @param dirPath : 目录路径
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public Vector<?> listFiles(String dirPath) {
        Vector<?> files = null;
        try {
            files = csftp.ls(dirPath);
            Iterator it = files.iterator();
            while (it.hasNext()) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                String fileName = entry.getFilename();
                if (".".equals(fileName) || "src/main".equals(fileName)) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        }
        return files;
    }

    /**
     * 方法名:rename<br>
     * 作用：重命名文件<br>
     *
     * @param src : 源(修改前)
     * @param dst : 目标(修改后)
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public boolean rename(String src, String dst) {
        try {
            //这里要对目标文件的目录做一个校验，如果不存在要创建对应目录
            String dirPath = dst.substring(0, dst.lastIndexOf("/"));
            mkdirs(dirPath);
            csftp.rename(src, dst);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
            disconnect();
            return false;
        }
    }

    /**
     * 方法名:delete<br>
     * 作用：删除文件<br>
     *
     * @param path : 删除文件或目录的路径
     * @author lhong<br>
     * @date 2016-06-01<br>
     */
    public boolean delete(String path) {
        try {
            if (path.indexOf(".") > -1) {
                csftp.rm(path);
            } else {
                csftp.rmdir(path);
            }
        } catch (SftpException e) {
            disconnect();
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 方法名:download<br>
     * 作用：上传文件或目录到指定目录<br>
     *
     * @param dstDirPath : 本地存放目录的地址
     * @param srcPath    : 服务器上的文件或目录的地址
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public boolean download(String dstDirPath, String srcPath) {

        if (null == dstDirPath || "".equals(dstDirPath)) {
            return false;
        }
        if (null == srcPath || "".equals(srcPath)) {
            return false;
        }

        File dst = new File(dstDirPath);

        if (!dst.exists()) {
            dst.mkdirs();
        }

        FileOutputStream fos = null;

        try {
            if (srcPath.contains(".")) {
                //当源为文件时
                String fileName = srcPath.substring(srcPath.lastIndexOf("/"));
                String dstFileName = dstDirPath + "/" + fileName;
                fos = new FileOutputStream(new File(dstFileName));
                System.out.println(csftp);
                csftp.get(srcPath, fos);
            } else {
                //当源为目录时
                Vector<?> files = listFiles(srcPath);
                for (Object file : files) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) file;
                    String fileName = entry.getFilename();

                    String childSrcDirPath = srcPath + "/" + fileName;
                    if (fileName.indexOf(".") == -1) {
                        dstDirPath += "/" + fileName;
                    }
                    if (!fileName.equals("..")) {
                        download(dstDirPath, childSrcDirPath);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("下载的源目录或文件,不存在......下载失败!!!");
            e.printStackTrace();
            disconnect();
            return false;
        } catch (SftpException e) {
            e.printStackTrace();
            disconnect();
            return false;
        } finally {
            //回收流，防上泄露
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    /**
     * 方法名:upload<br>
     * 作用：上传文件或目录到指定目录<br>
     *
     * @param dstDirPath : 服务器上的目录地址
     * @param srcPath    : 本地上传文件或目录的路径
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public boolean upload(String dstDirPath, String srcPath) {

        if (null == dstDirPath || "".equals(dstDirPath)) {
            return false;
        }
        if (null == srcPath || "".equals(srcPath)) {
            return false;
        }

        File src = new File(srcPath);
        FileInputStream fis = null;
        try {
            //检索并创建目录树
            mkdirs(dstDirPath);

            if (src.isFile()) {
                //如果上传的是文件
                fis = new FileInputStream(src);
                csftp.put(fis, src.getName());
            } else {
                //如果上传的是目录
                String dirPath = dstDirPath + "/" + src.getName();
                //1.创建对应的目录,并进入
                mkdirs(dirPath);
                csftp.cd(dirPath);
                File[] files = src.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    upload(dirPath, file.getPath());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("上传的源目录或文件,不存在......上传失败!!!");
            e.printStackTrace();
            disconnect();
            return false;
        } catch (SftpException e) {
            e.printStackTrace();
            disconnect();
            return false;
        } finally {
            //回收流，防上泄露
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 方法名:mkdirs<br>
     * 作用：创建文件夹,如果是多层文件夹，则依次创建<br>
     *
     * @param path : 文件夹地址
     * @author lhong<br>
     * @date 2016-05-31<br>
     */
    public void mkdirs(String path) {
        //先文件目录格式标准化，"/"为分隔符
        path = path.replaceAll("/+|\\\\+", "/");
        //解析目录并创建
        String[] dirs = path.split("(?!^/)+/");

        for (int i = 0; i < dirs.length; i++) {
            try {
                csftp.cd(dirs[i]);
            } catch (SftpException e) {
                try {
                    csftp.mkdir(dirs[i]);
                    csftp.cd(dirs[i]);
                } catch (SftpException e1) {
                    e1.printStackTrace();
                    disconnect();
                }
            }
        }
    }


    public static void main(String[] args) {
//
        SFtpUtils sftp = new SFtpUtils("39.98.85.96", 22, "root", "bMz7Tccc");
        if (sftp.connect()) {
            sftp.upload("/mydata/TSP", "D:\\modern\\TSP\\admin\\target\\TSP-2.5.5.jar");
//            sftp.upload("/mydata/nginx/html/tsp", "D:\\modern\\TSP\\ui\\dist");
            //          sftp.download("static/2/", "/opt/nss/upload/policy/acf1b7ef-62ee-4343-8739-7464f5f6defb.pdf");
//          //sftp.rename("/home/GZ3A/Origin/123/Trojan_Controlled_20151120133020_update.xls", "/home/GZ3A/Origin/223/Trojan_Controlled_20151120133020_update.xls");
//          //sftp.delete("/home/GZ3A/Origin/223");
//          //System.out.println(sftp.listFiles("/home/GZ3A/Origin/123"));
//
            sftp.disconnect();
        }

    }
}
