package com.example.sample.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model.Projection
import com.amazonaws.services.dynamodbv2.model.ProjectionType
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class DynamoDBTableManager(
    private val amazonDynamoDB: AmazonDynamoDB,
    private val dynamoDBMapperConfig: DynamoDBMapperConfig
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun createAndWaitForBeingActive(entity: KClass<out Any>) {
        try {
            val table = createTableForEntity(entity.java)
            log.info("Creating table ${table.tableName}.")
            table.waitForActive()
            log.info("Table ${table.tableName} created.")
        } catch (e: ResourceInUseException) {
            log.info("Table already exists - skip creation!.")
        } catch (e: Exception) {
            log.error("Error on create table operation on DynamoDb", e)
            throw e
        }
    }

    private fun createTableForEntity(entity: Class<*>): Table {
        val provisionedThroughput = ProvisionedThroughput(1L, 1L)

        val tableRequest = DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig)
            .generateCreateTableRequest(entity)
            .withProvisionedThroughput(provisionedThroughput)

        if (tableRequest.globalSecondaryIndexes != null) {
            tableRequest.globalSecondaryIndexes.forEach { globalSecondaryIndex ->
                globalSecondaryIndex
                    .withProvisionedThroughput(provisionedThroughput)
                    .withProjection(Projection().withProjectionType(ProjectionType.ALL))
            }
        }
        return DynamoDB(amazonDynamoDB).createTable(tableRequest)
    }

    fun deleteTableForEntity(entity: KClass<*>) {
        val tableRequest = DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig)
            .generateDeleteTableRequest(entity.java)

        DynamoDB(amazonDynamoDB).getTable(tableRequest.tableName).delete()
    }
}
