package com.cq.minio.manager;

import com.cq.minio.model.UploadObjectRequest;
import io.minio.ObjectWriteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class MinioManagerTest {

    @Autowired
    private MinioManager minioManager;

    @Test
    void uploadObject() {
        ObjectWriteResponse objectWriteResponse = minioManager.uploadObject("test/test.jpg", "D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\05abbd6f-562d-4c80-9d3d-5d3935563830.png");
        System.out.println(objectWriteResponse);
    }

    @Test
    void removeObject() {
        minioManager.removeObject("test/test.jpg");
    }

    @Test
    void getTmpUrl() {
        String tmpUrl = minioManager.getTmpUrl("test/test.jpg");
        System.out.println(tmpUrl);
    }

    @Test
    void testGetTmpUrl() {
        String tmpUrl = minioManager.getTmpUrl("test/test.jpg", 1, TimeUnit.SECONDS);
        System.out.println(tmpUrl);
    }

    @Test
    void uploadObjectBatch() {
        minioManager.uploadObjectBatch(
                UploadObjectRequest.builder().key("test/test1.jpg").filePath("D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\1d8fddaf-ae11-478c-92c1-7f30703a1e56.jpg").build(),
                UploadObjectRequest.builder().key("test/test2.jpg").filePath("D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\1fdbfbf3-1d86-4b29-a3fc-46345852f2f8.jpg").build(),
                UploadObjectRequest.builder().key("test/test3.jpg").filePath("D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\8dcfda14-5712-4d28-82f7-ae905b3c2308.jpg").build()
        );
    }

    @Test
    void testUploadObject() {
        minioManager.uploadObject(
                UploadObjectRequest
                        .builder()
                        .key("test/test4.jpg").filePath("D:\\OneDrive\\OneDrive - kylindemons\\图片\\StudyProcess\\583df4b7-a159-4cfc-9543-4f666120b25f.jpeg")
                        .build()
        );
    }
}
