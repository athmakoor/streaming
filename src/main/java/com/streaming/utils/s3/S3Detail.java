package com.streaming.utils.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("aws")
public class S3Detail {
    private String region;
    private String accessKeyId;
    private String scretAcceessKey;
    private Boolean createBucketIfNotExists = true;

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(final String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getScretAcceessKey() {
        return scretAcceessKey;
    }

    public void setScretAcceessKey(final String scretAcceessKey) {
        this.scretAcceessKey = scretAcceessKey;
    }

    public Boolean getCreateBucketIfNotExists() {
        return createBucketIfNotExists;
    }

    public void setCreateBucketIfNotExists(final Boolean createBucketIfNotExists) {
        this.createBucketIfNotExists = createBucketIfNotExists;
    }
}
