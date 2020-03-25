package cn.yccoding.blob.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author YC
 * @create 2020/3/24
 */
@Data
@Accessors(chain = true)
public class FileUpload {
    // 文件名
    private String fileName;

    // 原文件
    private String fileUrl;

    // 缩略图
    private String thumbnailUrl;
}
