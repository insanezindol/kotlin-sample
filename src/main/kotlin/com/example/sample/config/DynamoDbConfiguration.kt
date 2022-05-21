package com.example.sample.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.example.sample.repositories.dynamodb"])
class DynamoDbConfiguration(
    @Value("\${amazon.region}") val awsRegion: String,
    private val awsCredentialsProvider: AWSCredentialsProvider
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean(name = ["amazonDynamoDB"])
    fun amazonDynamoDBLocal(@Value("\${amazon.dynamodb.endpoint}") endpoint: String): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, awsRegion))
            .withCredentials(awsCredentialsProvider)
            .build()
    }

    @Bean("dynamoDBMapperConfig")
    @Primary
    fun dynamoDBMapperConfig(tableNameOverrider: DynamoDBMapperConfig.TableNameOverride): DynamoDBMapperConfig {
        return DynamoDBMapperConfig
            .Builder()
            .withTableNameOverride(tableNameOverrider)
            .build()
    }

    @Bean
    fun tableNameOverrider(
        @Value("\${amazon.dynamodb.tablePrefix}") tablePrefix: String
    ): DynamoDBMapperConfig.TableNameOverride {
        return DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(tablePrefix)
    }

}
