package cn.yccoding.blob.service;

import cn.yccoding.blob.model.BlobUpload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 按containerReference删除
     * @param containerName
     * @param blobName
     */
    void deleteContainer(String containerName,String blobName);
}
