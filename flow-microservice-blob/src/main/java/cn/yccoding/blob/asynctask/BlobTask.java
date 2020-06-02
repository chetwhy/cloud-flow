package cn.yccoding.blob.asynctask;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import lombok.extern.slf4j.Slf4j;

/**
 * @author YC
 * @since 2020/5/6 14:38 使用blob的异步任务
 */
@Component
@Slf4j
public class BlobTask {

    @Async
    public Future<CloudBlockBlob> uploadSingleFile(CloudBlobContainer container, MultipartFile file, String newFileName) {
        Future<CloudBlockBlob> result = null;

        try {
            CloudBlockBlob blob = container.getBlockBlobReference(newFileName);
            blob.getProperties().setContentType(file.getContentType());
            blob.upload(file.getInputStream(), file.getSize());
            result = new AsyncResult<>(blob);
            log.info("文件-[{}]上传blob完毕-[{}]，执行成功", file.getOriginalFilename(), Thread.currentThread().getName());
        } catch (Exception e) {
            log.warn("文件-[{}]上传blob异常-[{}], 放弃执行", file.getOriginalFilename(), e.getMessage());
        }

        return result;
    }
}
