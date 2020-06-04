package cn.yccoding.blob.util;

import lombok.extern.slf4j.Slf4j;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

/**
 * @author : Chet
 * @description :
 * @date : 2020/1/3
 */
@Slf4j
public class BlobUtils {

    /**
     * 获取blob container
     * 
     * @param storageConnectionString
     * @param containerReference
     * @return
     */
    public static CloudBlobContainer getAzureContainer(String storageConnectionString, String containerReference) {
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container = null;
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerReference);

            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
                new OperationContext());
            return container;
        } catch (Exception e) {
            log.error("获取azure container异常: [{}]" + e.getMessage());
        }
        return null;
    }
}
