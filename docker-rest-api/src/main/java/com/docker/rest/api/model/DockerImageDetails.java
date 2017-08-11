package com.docker.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerImageDetails {

	private DockerConfigDetails config;
	private String bucketName;
	private String objectKey;
	private String credentialProviderType;
	
}
