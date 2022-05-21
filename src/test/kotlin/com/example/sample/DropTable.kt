package com.example.sample

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.example.sample.models.ProductDocument

fun main() {

    // 테이블 삭제 스크립트
    val awsCredentials: AWSCredentials = BasicAWSCredentials("testAccessKey", "testSecretKey")
    val awsCredentialsProvider: AWSCredentialsProvider = AWSStaticCredentialsProvider(awsCredentials)
    val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(awsCredentialsProvider)
        .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2"))
        .build()
    var dynamoDB = DynamoDB(client)
    val ddbMapper = DynamoDBMapper(client)
    val request = ddbMapper.generateCreateTableRequest(ProductDocument::class.java)
        .withProvisionedThroughput(ProvisionedThroughput(1L, 1L))

    var table: Table = dynamoDB.getTable("local-" + request.tableName)

    try {
        table.delete()
        table.waitForDelete()
        println("Success.")
    } catch (e: Exception) {
        println("Unable to delete table: ");
        println(e.message);
    }

}
