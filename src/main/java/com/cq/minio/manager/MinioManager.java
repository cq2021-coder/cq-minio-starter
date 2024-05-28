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
     * todo 异常需要用 SDK 自定义异常兜住
     *
     * @param key  key
     * @param file 文件
     * @return {@link ObjectWriteResponse }
     * @throws IOException               IOException
     * @throws ServerException           服务器异常
     * @throws InsufficientDataException 数据不足异常
     * @throws ErrorResponseException    错误响应异常
     * @throws NoSuchAlgorithmException  无此算法异常
     * @throws InvalidKeyException       无效密钥异常
     * @throws InvalidResponseException  无效响应异常
     * @throws XmlParserException        xml分析器异常
     * @throws InternalException         内部异常
     */
    public ObjectWriteResponse uploadObject(String key, File file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(key)
                .filename(file.getAbsolutePath())
                .build();
        log.info("文件访问地址：{}", minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + key);
        return minioClient.uploadObject(uploadObjectArgs);
    }

    /**
     * 删除对象
     * todo 异常需要用 SDK 自定义异常兜住
     *
     * @param key key
     * @throws ServerException           服务器异常
     * @throws InsufficientDataException 数据不足异常
     * @throws ErrorResponseException    错误响应异常
     * @throws IOException               IOException
     * @throws NoSuchAlgorithmException  无此算法异常
     * @throws InvalidKeyException       无效密钥异常
     * @throws InvalidResponseException  无效响应异常
     * @throws XmlParserException        xml分析器异常
     * @throws InternalException         内部异常
     */
    public void removeObject(String key) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(key)
                .build();
        minioClient.removeObject(removeObjectArgs);
    }

}
