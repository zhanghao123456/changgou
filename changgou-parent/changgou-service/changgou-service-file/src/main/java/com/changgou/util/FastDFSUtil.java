package com.changgou.util;

import com.changgou.file.FastFDSFile;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class FastDFSUtil {

    /**
     * 加载Tracker链接信息
     */
    static {
        try {
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//            String filePath =  "D:\\Developer\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf";
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String[] upload(FastFDSFile fastFDSFile) throws Exception {

        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", "123");

        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClicent对象链接信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //通过连接实现文件上传
        String[] uploads = storageClient.upload_file(fastFDSFile.getContent(), fastFDSFile.getExt(), meta_list);
        return uploads;
    }

    //文件查询
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClicent对象链接信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //获取文件信息
        return storageClient.get_file_info(groupName,remoteFileName);
    }


    //文件下载
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClicent对象链接信息
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    //删除文件
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClicent对象链接信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }

    //获取storage信息
    public static StorageServer getStorages() throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    //获取Storaged的ip和端口信息
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    //tracker
    public static String getTrackerInfo() throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取信息
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
        String url = "http://" + ip + ":" + g_tracker_http_port;
        return url;
    }

    public static TrackerServer getTrackerServer() throws Exception {
        //创建Tracker访问客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    public static void main(String[] args)  throws Exception{
//        System.out.println(getTrackerInfo());
    }




}
