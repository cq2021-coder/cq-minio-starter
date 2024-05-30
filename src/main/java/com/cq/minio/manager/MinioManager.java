package com.cq.minio.manager;

import com.cq.minio.config.MinioConfig;
import com.cq.minio.model.UploadObjectRequest;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class MinioManager {

    private final MinioConfig minioConfig;

    private final MinioClient minioClient;

    /**
     * 上传对象
     *
     * @param key      key
     * @param filePath 文件路径
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadObject(String key, String filePath) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(key)
                    .filename(filePath)
                    .build();
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传对象
     *
     * @param uploadObjectRequest 上传对象请求
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadObject(UploadObjectRequest uploadObjectRequest) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(uploadObjectRequest.getKey())
                    .filename(uploadObjectRequest.getFilePath())
                    .build();
            return minioClient.uploadObject(uploadObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传对象
     *
     * @param uploadObjectRequests 上传对象请求
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadObjectBatch(UploadObjectRequest... uploadObjectRequests) {
        List<SnowballObject> snowballObjectList = Arrays
                .stream(uploadObjectRequests)
                .map(uploadObjectRequest -> new SnowballObject(uploadObjectRequest.getKey(), uploadObjectRequest.getFilePath()))
                .toList();
        return uploadSnowballObjectBatch(snowballObjectList);
    }

    /**
     * 批量上传对象
     *
     * @param uploadObjectRequests 上传对象请求
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadObjectBatch(List<UploadObjectRequest> uploadObjectRequests) {
        List<SnowballObject> snowballObjectList = uploadObjectRequests
                .stream()
                .map(uploadObjectRequest -> new SnowballObject(uploadObjectRequest.getKey(), uploadObjectRequest.getFilePath()))
                .toList();
        return this.uploadSnowballObjectBatch(snowballObjectList);
    }

    /**
     * 批量上传对象
     *
     * @param snowballObjectList 批量对象列表
     * @return {@link ObjectWriteResponse }
     */
    public ObjectWriteResponse uploadSnowballObjectBatch(List<SnowballObject> snowballObjectList) {
        try {
            UploadSnowballObjectsArgs uploadObjectArgs = UploadSnowballObjectsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .objects(snowballObjectList)
                    .build();
            return minioClient.uploadSnowballObjects(uploadObjectArgs);
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

    /**
     * 批量删除对象
     *
     * @param keys keys
     */
    public void removeObjectBatch(String... keys) {
        List<DeleteObject> deleteObjectList = Arrays.stream(keys).map(DeleteObject::new).toList();
        RemoveObjectsArgs removeObjectArgs = RemoveObjectsArgs.builder()
                .bucket(minioConfig.getBucketName())
                .objects(deleteObjectList)
                .build();
        minioClient.removeObjects(removeObjectArgs);
    }

    /**
     * 批量删除对象
     *
     * @param keyList key 列表
     */
    public void removeObjectBatch(List<String> keyList) {
        List<DeleteObject> deleteObjectList = keyList.stream().map(DeleteObject::new).toList();
        RemoveObjectsArgs removeObjectArgs = RemoveObjectsArgs.builder()
                .bucket(minioConfig.getBucketName())
                .objects(deleteObjectList)
                .build();
        minioClient.removeObjects(removeObjectArgs);
    }

    /**
     * 获取临时访问 url（一分钟）
     * 当存储桶设置为私有读时会用到此方法
     *
     * @param key key
     * @return {@link String }
     */
    public String getTmpUrl(String key) {
        return this.getTmpUrl(key, 1, TimeUnit.MINUTES);
    }

    /**
     * 获取临时访问 url
     * 当存储桶设置为私有读时会用到此方法
     *
     * @param key      key
     * @param duration 时间
     * @param unit     时间单位
     * @return {@link String }
     */
    public String getTmpUrl(String key, int duration, TimeUnit unit) {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs
                .builder()
                .method(Method.GET)
                .bucket(minioConfig.getBucketName())
                .object(key)
                .expiry(duration, unit)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
    }

}
