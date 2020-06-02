package cn.yccoding.blob.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.yccoding.blob.model.BlobUpload;

/**
 * @Author YC
 * @create 2020/3/24
 * 文件工具类
 */
public interface FileService {

    /**
     * 文件上传
     * @param fileList
     * @return
     */
    List<BlobUpload> uploadFile(List<MultipartFile> fileList);

    /**
     * 查询指定容器的文件列表
     * @param containerName
     * @return
     */
    List<BlobUpload> listContainerFile(String containerName);

    /**
     * 按containerReference删除
     * @param containerName
     * @param blobName
     */
    void deleteFile(String containerName, String blobName);
    /**
     * 删除指定容器
     * @param containerName
     */
    void deleteContainer(String containerName);

    /**
     * 下载指定blob文件
     * @param containerName
     * @param blobName
     * @param request
     * @param response
     */
    void downloadFile(String containerName, String blobName, HttpServletRequest request, HttpServletResponse response);
}
