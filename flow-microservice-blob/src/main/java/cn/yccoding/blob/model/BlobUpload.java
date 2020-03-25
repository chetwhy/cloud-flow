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
    // 文件名
    private String fileName;

    // 原文件
    private String fileUrl;
}
