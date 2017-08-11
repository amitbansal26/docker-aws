package com.docker.rest.api.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.BuildImageResultCallback;

import amazon.s3.AmazonS3Template;

@RestController
public class DockerRestController {
	
	@Autowired
	private DockerClient dockerClient;
	
	@Autowired
    private AmazonS3Template s3Template;
	@Value("${amazon.s3.default-bucket}")
    private String bucketName;

   
	@GetMapping("/")
	public String fetchDockerFile() throws FileNotFoundException {
		dockerClient.buildImageCmd(s3Template.get("amit"))
	     .exec(new BuildImageResultCallback()).awaitImageId();
	     return "success";
	}
}
