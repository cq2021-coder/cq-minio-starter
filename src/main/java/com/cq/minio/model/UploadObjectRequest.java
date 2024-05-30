package com.cq.minio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadObjectRequest {

    /**
     * key
     */
    private String key;

    /**
     * 文件路径
     */
    private String filePath;

}
