package com.example.sample.service

import com.example.sample.models.ProductDocument
import com.example.sample.repositories.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun findAll(): MutableIterable<ProductDocument> {
        log.info("getProduct")
        productRepository.findAll()
        return productRepository.findAll()
    }

    fun findOne(id: String): ProductDocument? {
        log.info("getProduct")
        return productRepository.findById(id).orElse(null)
    }

    fun add(param: ProductDocument): ProductDocument {
        log.info("add : $param")
        var productDocument = ProductDocument(
            name = param.name,
            price = param.price
        )
        return productRepository.save(productDocument)
    }

    fun modify(param: ProductDocument): ProductDocument? {
        log.info("modify : $param")

        var findProduct = param.id?.let { productRepository.findById(it).orElse(null) }

        if (findProduct != null) {
            findProduct.name = param.name
            findProduct.price = param.price
            findProduct.updatedAt = Instant.now()
            productRepository.save(findProduct)
        }

        return findProduct
    }

    fun remove(param: ProductDocument) {
        log.info("remove : $param")
        var productDocument = ProductDocument(
            id = param.id
        )
        productRepository.delete(productDocument)
    }

}
