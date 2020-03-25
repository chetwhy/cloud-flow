package cn.yccoding.blob.service.impl;

import cn.yccoding.blob.model.BlobUpload;
import cn.yccoding.blob.service.FileService;
import cn.yccoding.blob.util.BlobUtil;
import cn.yccoding.common.contants.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.yccoding.blob.config.ConstantPropertiesUtil.*;

/**
 * @Author YC
 * @create 2020/3/24
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String DEFAULT_CONTAINER_REFERENCE = "default";

    @Override
    public List<BlobUpload> uploadFile(List<MultipartFile> fileList) {
        // 文件是否有内容
        if (fileList == null || fileList.isEmpty()) {
            throw new CustomException(ResultCodeEnum.NO_FILE_CONTENT);
        }

        // 获取blob容器
        String containerReference = DEFAULT_CONTAINER_REFERENCE;  // TODO 可该根据业务需要修改，如id, type等
        CloudBlobContainer container = BlobUtil.getAzureContainer(getStorageConnectionString(), containerReference);
        if (container == null) {
            log.error("获取blob container异常");
            throw new CustomException(ResultCodeEnum.GET_BLOB_CONTAINER_ERROR);
        }

        // 区分文件类型, 如image/jpg
        Map<String, List<MultipartFile>> classFiles =
            fileList.stream().collect(Collectors.groupingBy(MultipartFile::getContentType));

        // 分类上传
        List<BlobUpload> blobUploadRespList = new ArrayList<>();
        Set<String> fileTypes = classFiles.keySet();
        try {
            for (String type : fileTypes) {
                List<MultipartFile> files = classFiles.get(type);

                for (MultipartFile file : files) {
                    // 组装文件上传名称
                    String oriFile = file.getOriginalFilename();
                    String fileDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    String fileNonStr = UUID.randomUUID().toString();
                    String fileType = oriFile.substring(oriFile.lastIndexOf("."));
                    String newFileName = fileDate + "-" + fileNonStr + fileType;

                    // 上传
                    CloudBlob blob = container.getBlockBlobReference(newFileName);
                    blob.getProperties().setContentType(file.getContentType());
                    blob.upload(file.getInputStream(), file.getSize());

                    // 返回图片URL
                    BlobUpload uploadResp = new BlobUpload();
                    uploadResp.setFileName(file.getOriginalFilename()).setFileUrl(blob.getUri().toString());

                    // TODO 如果是图片生成缩略图

                    // 添加到结果集合
                    blobUploadRespList.add(uploadResp);
                }
            }
        } catch (Exception e) {
            log.error("上传文件出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.UPLOAD_BLOB_FAILED);
        }

        return blobUploadRespList;
    }

    private String getStorageConnectionString() {
        String storageConnectionString =
            String.format("DefaultEndpointsProtocol=%s;AccountName=%s;AccountKey=%s;EndpointSuffix=%s",
                DEFAULT_ENDPOINTS_PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, ENDPOINT_SUFFIX);
        return storageConnectionString;
    }
}
