package com.example.sample.service

import com.example.sample.models.ProductDocument
import com.example.sample.repositories.dynamodb.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun getProduct(): ProductDocument {
        log.info("getProduct")
        return ProductDocument()
    }

    fun postProduct(name: String, price: Long): ProductDocument {
        log.info("postProduct : $name, $price")
        var productDocument = ProductDocument(
            name = name,
            price = price
        )
        return productRepository.save(productDocument)
    }

}
