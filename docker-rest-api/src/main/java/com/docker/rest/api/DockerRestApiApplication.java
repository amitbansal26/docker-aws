package com.docker.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class DockerRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerRestApiApplication.class, args);
	}
	
	@Bean
	public DockerClient dockerClient() {
		DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
				  .withDockerHost("tcp://localhost:2375")
				  .withRegistryUsername("")
				  .withRegistryPassword("")
				  .withRegistryEmail("")
				  .withRegistryUrl("")
				  .build();
		DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
				  .withReadTimeout(1000)
				  .withConnectTimeout(1000)
				  .withMaxTotalConnections(100)
				  .withMaxPerRouteConnections(10);	
		
		
	DockerClient dockerClient = DockerClientBuilder.getInstance(config)
				  .withDockerCmdExecFactory(dockerCmdExecFactory)
				  .build();
     return dockerClient;
	}
	
	
}
