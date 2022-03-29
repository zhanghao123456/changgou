package com.changgou.util;

import com.changgou.file.FastFDSFile;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;


public class FastDFSUtil {

    /**
     * 加载Tracker链接信息
     */
    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
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
}
