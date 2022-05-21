package com.example.sample

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table

fun main() {

    val awsCredentials: AWSCredentials = BasicAWSCredentials("testAccessKey", "testSecretKey")
    val awsCredentialsProvider: AWSCredentialsProvider = AWSStaticCredentialsProvider(awsCredentials)
    val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(awsCredentialsProvider)
        .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-1"))
        .build()
    var dynamoDB = DynamoDB(client)

    var table: Table = dynamoDB.getTable("product")

    try {
        table.delete()
        table.waitForDelete()
        println("Success.")
    } catch (e: Exception) {
        println("Unable to delete table: ");
        println(e.message);
    }

}
