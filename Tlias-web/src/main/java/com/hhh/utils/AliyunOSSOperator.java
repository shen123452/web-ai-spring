package com.hhh.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AliyunOSSOperator {

    private final AliyunOSSProperties properties;

    public AliyunOSSOperator(AliyunOSSProperties properties) {
        this.properties = properties;
    }

    public String upload(byte[] content, String originalFilename) throws Exception {
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String suffix = "";
        if (originalFilename != null) {
            int index = originalFilename.lastIndexOf(".");
            suffix = index >= 0 ? originalFilename.substring(index) : "";
        }
        String newFileName = UUID.randomUUID() + suffix;
        String objectName = dir + "/" + newFileName;

        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(properties.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(properties.getRegion())
                .build();

        try {
            ossClient.putObject(properties.getBucketName(), objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }

        return properties.getEndpoint().split("//")[0]
                + "//"
                + properties.getBucketName()
                + "."
                + properties.getEndpoint().split("//")[1]
                + "/"
                + objectName;
    }
}
