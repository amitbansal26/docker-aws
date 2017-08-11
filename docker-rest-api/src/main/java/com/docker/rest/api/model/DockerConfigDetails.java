package com.docker.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DockerConfigDetails {

	private String dockerHost;
	private String dockerCertPath;
	private String username;
	private String password;
	private String registryMail;
	private String registryUrl;
	
}
