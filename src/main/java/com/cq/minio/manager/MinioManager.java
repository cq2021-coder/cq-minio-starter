package com.cq.minio.manager;

import com.cq.minio.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
@RequiredArgsConstructor
public class MinioManager {

    private final MinioConfig minioConfig;

    private final MinioClient minioClient;

    /**
     * 上传对象
     *
     * @param key  key
     * @param file 文件
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadObject(String key, File file) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(key)
                    .filename(file.getAbsolutePath())
                    .build();
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除对象
     *
     * @param key key
     */
    public void removeObject(String key) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(key)
                    .build();
            minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

}
