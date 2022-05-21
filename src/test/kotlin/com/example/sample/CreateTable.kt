package com.example.sample

import com.amazonaws.AmazonServiceException
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
import com.amazonaws.services.dynamodbv2.model.*
import com.example.sample.models.ProductDocument

fun main() {

    val awsCredentials: AWSCredentials = BasicAWSCredentials("testAccessKey", "testSecretKey")
    val awsCredentialsProvider: AWSCredentialsProvider = AWSStaticCredentialsProvider(awsCredentials)
    val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(awsCredentialsProvider)
        .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-1"))
        .build()
    var dynamoDB = DynamoDB(client)
    val ddbMapper = DynamoDBMapper(client)

    val request = ddbMapper.generateCreateTableRequest(ProductDocument::class.java)
        .withProvisionedThroughput(ProvisionedThroughput(10, 10))
    try {
        val table: Table = dynamoDB.createTable(request)
        println("create event table success")
        println("Success.  Table status: ${table.description.tableStatus}")
    } catch (e: AmazonServiceException) {
        println(e.errorMessage)
    } catch (e: Exception) {
        println(e.message)
    }

}
