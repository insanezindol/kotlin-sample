package com.example.sample.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AWSCredentialsProviderConfig {

    @Bean
    @Primary
    fun defaultCredentials(
        @Value("\${amazon.accessKey}") accessKey: String,
        @Value("\${amazon.secretKey}") secretKey: String
    ): AWSCredentialsProvider {
        return AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
    }

}
