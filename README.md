## SDK 功能
1. 上传文件
2. 获取临时访问链接
3. 删除文件
4. 批量上传文件
5. 批量删除文件


## 使用方法
### 1、引入依赖
```xml
<dependency>
    <groupId>com.cq</groupId>
    <artifactId>cq-minio-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2、使用示例
#### 上传文件
```java
@Resource
private MinioManager minioManager;

public String fileUpload(MultipartFile file) {
    File tempFile = null;
    try {
        // 创建OSSClient实例。
        String fileName = file.getOriginalFilename();
        //生成随机唯一值，使用uuid，添加到文件名称里面
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;
        tempFile = File.createTempFile(fileName, null);
        file.transferTo(tempFile);
        //按照当前日期，创建文件夹，上传到创建文件夹里面
        //  2021/02/02/01.jpg
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String timeUrl = df.format(new Date());
        fileName = timeUrl + "/" + System.currentTimeMillis() + "-" + fileName;
        //调用方法实现上传
        minioManager.uploadObject(fileName, tempFile.getAbsolutePath());
        return fileName;
    } catch (IOException e) {
        log.info("upload error", e);
        return null;
    } finally {
        if (tempFile != null) {
            tempFile.deleteOnExit();
        }
    }
}
```

#### 获取临时访问链接
```java
@Resource
private MinioManager minioManager;

public String getTmpAccess(String key) {
    if (StringUtils.isBlank(key)) {
        return "";
    }
    if (key.startsWith("http")) {
        return key;
    }
    return minioManager.getTmpUrl(key);
}
```


## 注意事项
- 该 starter 是由 jdk21 + SpringBoot3.3.0 实现的，因此使用此 SDK 的项目也建议使用 SpringBoot3
