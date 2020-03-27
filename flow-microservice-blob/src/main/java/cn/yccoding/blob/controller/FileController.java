package cn.yccoding.blob.controller;

import cn.yccoding.blob.form.ReqFileSave;
import cn.yccoding.blob.model.BlobUpload;
import cn.yccoding.blob.service.FileService;
import cn.yccoding.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author YC
 * @create 2020/3/24
 * 文件上传控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/blob/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 批量文件上传
     * @param fileList
     * @return
     */
    @PostMapping("/upload")
    public R uploadFile(@RequestPart("file") List<MultipartFile> fileList) {
        List<BlobUpload> blobUploadList = fileService.uploadFile(fileList);
        return R.ok().message("文件上传成功").data("items", blobUploadList);
    }

    /**
     * 查询指定容器的文件列表
     * @param containerName
     * @return
     */
    @GetMapping("/{container-name}")
    public R uploadFile(@PathVariable(name = "container-name") String containerName) {
        List<BlobUpload> blobUploadList = fileService.listContainerFile(containerName);
        return R.ok().data("items", blobUploadList);
    }

    /**
     * 删除指定文件
     * @param containerName
     * @param blobName
     * @return
     */
    @DeleteMapping("/{container-name}/{blob-name}")
    public R deleteFile(
            @PathVariable(name = "container-name") String containerName,
            @PathVariable(name = "blob-name") String blobName
    ) {
        fileService.deleteFile(containerName,blobName);
        return R.ok().message("文件删除成功");
    }

    /**
     * 删除指定容器
     * @param containerName
     * @return
     */
    @DeleteMapping("/{container-name}")
    public R deleteContainer(
            @PathVariable(name = "container-name") String containerName
    ) {
        fileService.deleteContainer(containerName);
        return R.ok().message("文件删除成功");
    }

    /**
     * 下载blob文件到本地
     * @return
     */
    @PostMapping("/download")
    public R downloadFile(@RequestBody ReqFileSave reqFileSave) {
        String filePath = fileService.downloadFile(reqFileSave.getContainerName(), reqFileSave.getBlobName());
        return R.ok().message("文件下载成功").data("path",filePath);
    }

    /**
     * 数据库存储
     * @return
     */
    @PostMapping("/sql-server/save")
    public R saveFileToSqlServer(@RequestBody ReqFileSave reqFileSave) {
        String name = fileService.saveFile(reqFileSave.getContainerName(), reqFileSave.getBlobName());
        return R.ok().message("文件上传到sql server成功").data("file",name);
    }

    /**
     * 从sql server数据下载文件
     * 测试使用
     * @param reqFileSave
     * @return
     */
    @PostMapping("/sql-server/download")
    public R downloadSqlServerFile(@RequestBody ReqFileSave reqFileSave) {
        String name = fileService.downloadSqlServerFile(reqFileSave.getContainerName(), reqFileSave.getBlobName());
        return R.ok().message("文件从sql server下载成功").data("file",name);
    }
}
