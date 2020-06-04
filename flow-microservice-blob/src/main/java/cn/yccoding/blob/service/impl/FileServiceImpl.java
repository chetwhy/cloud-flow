package cn.yccoding.blob.service.impl;

import static cn.yccoding.blob.util.ConstantPropertyUtils.*;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import cn.yccoding.blob.asynctask.BlobTask;
import cn.yccoding.blob.model.BlobUpload;
import cn.yccoding.blob.service.FileService;
import cn.yccoding.blob.util.BlobUtils;
import cn.yccoding.common.contants.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author YC
 * @create 2020/3/24
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String DEFAULT_CONTAINER_REFERENCE = "default";

    private static final long TASK_TIME_OUT = 30000;

    @Autowired
    private BlobTask blobTask;

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

        List<BlobUpload> blobUploadRespList = new ArrayList<>();
        // TODO 建议用数据库关联原文件信息，或者封装future的bean时携带信息
        // 多线程上传文件到blob
        List<CloudBlockBlob> blobUploadedList = uploadBlobAsync(container, fileList);
        try {
            for (CloudBlockBlob blob : blobUploadedList) {
                // 封装前端返回
                BlobUpload uploadResp =
                    new BlobUpload().setFileBlobName(blob.getName()).setFileUrl(blob.getUri().toString());
                blobUploadRespList.add(uploadResp);
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
                    CloudBlockBlob blob = (CloudBlockBlob)item;
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
    public void downloadFile(String containerName, String blobName, HttpServletRequest request,
        HttpServletResponse response) {
        log.info("开始文件下载...");
        CloudBlobContainer container = getCloudBlobContainer(containerName);
        try {
            CloudBlob blob = container.getBlobReferenceFromServer(blobName);

            // 下载的文件使用原名称
            OutputStream out = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(blobName, "utf-8"));
            blob.download(out);
            out.flush();
            out.close();
            log.info("文件下载本地完成...");
        } catch (Exception e) {
            log.error("下载文件出现异常:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.DOWNLOAD_BLOB_FAILED);
        }
    }

    private CloudBlobContainer getCloudBlobContainer(String containerName) {
        String storageConnectionString =
            String.format("DefaultEndpointsProtocol=%s;AccountName=%s;AccountKey=%s;EndpointSuffix=%s",
                DEFAULT_ENDPOINTS_PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, ENDPOINT_SUFFIX);
        CloudBlobContainer container = BlobUtils.getAzureContainer(storageConnectionString, containerName);
        if (container == null) {
            log.error("获取blob container异常");
            throw new CustomException(ResultCodeEnum.GET_BLOB_CONTAINER_ERROR);
        }
        return container;
    }

    // 多线程上传文件到blob
    private List<CloudBlockBlob> uploadBlobAsync(CloudBlobContainer container, List<MultipartFile> fileList) {
        // 多线程开始执行
        Long threadStart = System.currentTimeMillis();
        List<Future<CloudBlockBlob>> blobFutureList = new ArrayList<>();
        try {
            for (MultipartFile file : fileList) {
                // 组装文件上传名称
                String oriFile = file.getOriginalFilename();
                String newFileName = generateFileName(oriFile);

                blobFutureList.add(blobTask.uploadSingleFile(container, file, newFileName));
            }
        } catch (Exception e) {
            log.warn("线程池队列已满，放弃剩余任务:{}", e.getMessage());
        }

        List<CloudBlockBlob> blobResultList = new ArrayList<>();
        for (Future<CloudBlockBlob> future : blobFutureList) {
            try {
                CloudBlockBlob blob = future.get(TASK_TIME_OUT, TimeUnit.MILLISECONDS);
                blobResultList.add(blob);
            } catch (Exception e) {
                log.error("blob线程运行异常，放弃执行：[{}]", e.getMessage());
            } finally {
                future.cancel(true);
            }
        }

        log.info("blob上传的多线程执行结束，耗时：[{}]ms", System.currentTimeMillis() - threadStart);
        blobResultList = blobResultList.stream().filter(i -> i != null && i.getUri() != null && i.getName() != null)
                .collect(Collectors.toList());
        return blobResultList;
    }

    /**
     * 随机文件名
     */
    private String generateFileName(String oriFile) {
        String fileDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileNonStr = UUID.randomUUID().toString();
        String fileType = oriFile.substring(oriFile.lastIndexOf("."));
        return fileDate + "-" + fileNonStr + fileType;
    }
}
