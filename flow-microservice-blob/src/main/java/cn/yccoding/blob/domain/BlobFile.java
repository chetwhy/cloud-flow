package cn.yccoding.blob.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author YC
 * @create 2020/3/27
 */
@Entity
@Data
@Table(name = "t_blob_file")
public class BlobFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private byte[] content;

}
