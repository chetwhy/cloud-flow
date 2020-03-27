package cn.yccoding.blob.service.impl;

import cn.yccoding.blob.domain.BlobFile;
import cn.yccoding.blob.model.BlobUpload;
import cn.yccoding.blob.repository.BlobFileRepository;
import cn.yccoding.blob.service.FileService;
import cn.yccoding.blob.util.BlobUtil;
import cn.yccoding.common.contants.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Autowired
    private BlobFileRepository blobFileRepository;

    @Override
    public List<BlobUpload> uploadFile(List<MultipartFile> fileList) {
        // 文件是否有内容
        if (fileList == null || fileList.isEmpty()) {
            throw new CustomException(ResultCodeEnum.NO_FILE_CONTENT);
        }

        // 获取blob容器
        String containerReference = DEFAULT_CONTAINER_REFERENCE; // TODO 可该根据业务需要修改，如id, type等
        CloudBlobContainer container = getCloudBlobContainer(containerReference);

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
                    BlobUpload uploadResp = new BlobUpload().setFileOriginName(file.getOriginalFilename()).setFileBlobName(blob.getName())
                            .setFileUrl(blob.getUri().toString());

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

    @Override
    public List<BlobUpload> listContainerFile(String containerName) {
        CloudBlobContainer container = getCloudBlobContainer(containerName);
        List<BlobUpload> result = new ArrayList<>();
        try {
            Iterable<ListBlobItem> items = container.listBlobs();
            for (ListBlobItem item : items) {
                if (item instanceof CloudBlockBlob) {
                    CloudBlockBlob blob = (CloudBlockBlob) item;
                    result.add(new BlobUpload().setFileBlobName(blob.getName()).setFileUrl(blob.getUri().toString()));
                }
            }
        } catch (Exception e) {
            log.error("查询文件出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.QUERY_BLOB_FAILED);
        }
        return result;
    }

    @Override
    public void deleteFile(String containerName, String blobName) {
        CloudBlobContainer container = getCloudBlobContainer(containerName);
        try {
            CloudBlob blob = container.getBlobReferenceFromServer(blobName);
            blob.deleteIfExists();
        } catch (Exception e) {
            log.error("删除文件出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.DELETE_BLOB_FAILED);
        }
    }

    @Override
    public void deleteContainer(String containerName) {
        CloudBlobContainer container = getCloudBlobContainer(containerName);
        try {
            container.deleteIfExists();
        } catch (Exception e) {
            log.error("删除容器出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.DELETE_BLOB_FAILED);
        }
    }

    @Override
    public String downloadFile(String containerName, String blobName) {
        log.info("开始文件下载...");
        CloudBlobContainer container = getCloudBlobContainer(containerName);
        String filePath = containerName + "/" + blobName;
        try {
            CloudBlob blob = container.getBlobReferenceFromServer(blobName);

            File tempDir = new File(containerName);
            if (!tempDir.exists()) {
                log.info("创建临时目录:[{}]",containerName);
                tempDir.mkdirs();
            }else {
                String[] list = tempDir.list();
                boolean sameMatch = Arrays.asList(list).stream().anyMatch(i -> blobName.equals(i.toString()));
                if (sameMatch) {
                    log.info("已有相同文件，跳过下载..");
                    return filePath;
                }
            }
            File downloadFile = new File(containerName+"/"+blobName);

            System.out.println(downloadFile.getPath());
            blob.downloadToFile(downloadFile.getPath());
            log.info("文件下载本地完成...");
        } catch (Exception e) {
            log.error("下载文件出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.DOWNLOAD_BLOB_FAILED);
        }
        return filePath;
    }

    @Override
    public String saveFile(String containerName, String blobName) {
        String path = containerName + "/" + blobName;
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            BlobFile blobFile = new BlobFile();
            blobFile.setFileName(path);
            blobFile.setContent(bytes);
            BlobFile save = blobFileRepository.save(blobFile);
            return path;
        } catch (IOException e) {
            log.info("保存文件到数据库异常:[{}]",e.getMessage());
            throw new CustomException(ResultCodeEnum.SAVE_SQLSERVER_FAILED);
        }
    }

    @Override
    public String downloadSqlServerFile(String containerName, String blobName) {
        String fileName = containerName + "/" + blobName;
        String outPath = "down_" + blobName;
        BlobFile file = blobFileRepository.findByFileName(fileName);
        byte[] bytes = file.getContent();
        try {
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            fos.write(bytes);
            fos.close();
            return outPath;
        } catch (IOException e) {
            log.info("从sql server数据库下载异常:[{}]",e.getMessage());
            throw new CustomException(ResultCodeEnum.DOWNLOAD_SQLSERVER_FAILED);
        }
    }

    private CloudBlobContainer getCloudBlobContainer(String containerName) {
        String storageConnectionString =
            String.format("DefaultEndpointsProtocol=%s;AccountName=%s;AccountKey=%s;EndpointSuffix=%s",
                DEFAULT_ENDPOINTS_PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, ENDPOINT_SUFFIX);
        CloudBlobContainer container = BlobUtil.getAzureContainer(storageConnectionString, containerName);
        if (container == null) {
            log.error("获取blob container异常");
            throw new CustomException(ResultCodeEnum.GET_BLOB_CONTAINER_ERROR);
        }
        return container;
    }

    public static void main(String[] args) {
        File file = new File("aaa/bbb.txt");
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println(".....");
    }
}
