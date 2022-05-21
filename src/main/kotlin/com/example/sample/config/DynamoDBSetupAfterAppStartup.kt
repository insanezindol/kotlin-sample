package com.example.sample.config

import com.example.sample.models.ProductDocument
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class DynamoDBSetupAfterAppStartup(
    private val dynamoDBTableManager: DynamoDBTableManager
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        dynamoDBTableManager.createAndWaitForBeingActive(ProductDocument::class)
    }
}
