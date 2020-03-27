package cn.yccoding.blob.repository;

import cn.yccoding.blob.domain.BlobFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author YC
 * @create 2020/3/27
 */
@Repository
public interface BlobFileRepository extends JpaRepository<BlobFile,Long> {
    BlobFile findByFileName(String fileName);
}
