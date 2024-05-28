package com.cq.minio.manager;

import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class MinioManagerTest {

    @Autowired
    private MinioManager minioManager;

    @Test
    void uploadObject() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ObjectWriteResponse objectWriteResponse = minioManager.uploadObject("test/test.jpg", new File("D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\05abbd6f-562d-4c80-9d3d-5d3935563830.png"));
        System.out.println(objectWriteResponse);
    }

    @Test
    void removeObject() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioManager.removeObject("test/test.jpg");
    }
}
