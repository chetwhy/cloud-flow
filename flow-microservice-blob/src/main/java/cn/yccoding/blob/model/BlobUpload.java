package cn.yccoding.blob.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author YC
 * @create 2020/3/25
 */
@Data
@Accessors(chain = true)
public class BlobUpload {
    // 文件原名称
    private String fileOriginName;

    // 文件上传后名称
    private String fileBlobName;

    // 文件访问路径
    private String fileUrl;
}
