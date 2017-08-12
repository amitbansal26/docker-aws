package amazon.s3;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;


@Component
public class AmazonS3Template {

    private String defaultBucket;
    private String accessKeyId;
    private String accessKeySecret;
    private Credentials sessionCredentials;

    public AmazonS3Template(String defaultBucket, String accessKeyId, String accessKeySecret) {
        this.defaultBucket = defaultBucket;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

       public PutObjectResult save(String key, File file) {
        return getAmazonS3Client().putObject(new PutObjectRequest(defaultBucket, key, file));
    }

    
    public S3Object get(String key) {
        return getAmazonS3Client().getObject(defaultBucket, key);
    }

    
    public AmazonS3 getAmazonS3Client() {
        BasicSessionCredentials basicSessionCredentials = getBasicSessionCredentials();
       AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(basicSessionCredentials);
       return  AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider).build();

    }

    
    private BasicSessionCredentials getBasicSessionCredentials() {

        if (sessionCredentials == null || sessionCredentials.getExpiration().before(new Date()))
            sessionCredentials = getSessionCredentials();

        return new BasicSessionCredentials(sessionCredentials.getAccessKeyId(),
                sessionCredentials.getSecretAccessKey(),
                sessionCredentials.getSessionToken());
    }

  
    private Credentials getSessionCredentials() {
      
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, accessKeySecret));
        AWSSecurityTokenService awsSecurityTokenService = AWSSecurityTokenServiceClientBuilder.standard().withCredentials(awsCredentialsProvider).build();
        GetSessionTokenRequest getSessionTokenRequest =
                new GetSessionTokenRequest().withDurationSeconds(43200);
        sessionCredentials = awsSecurityTokenService.getSessionToken(getSessionTokenRequest).getCredentials();
        return sessionCredentials;
    }
}
