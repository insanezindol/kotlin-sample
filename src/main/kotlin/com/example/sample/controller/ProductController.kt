package com.example.sample.controller

import com.example.sample.models.ProductDocument
import com.example.sample.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/product")
    fun getProduct(): ProductDocument {
        log.info("getProduct")
        return productService.getProduct();
    }

    @PostMapping("/product")
    fun postProduct(
        @RequestParam name: String,
        @RequestParam price: Long
    ): ProductDocument {
        log.info("postProduct")
        return productService.postProduct(name, price);
    }

}
