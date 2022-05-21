package com.example.sample.controller

import com.example.sample.models.ProductDocument
import com.example.sample.service.ProductService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/findAll")
    fun findAll(): ResponseEntity<MutableIterable<ProductDocument>> {
        log.info("findAll")
        return ResponseEntity.ok().body(productService.findAll())
    }

    @GetMapping("/findOne")
    fun findOne(
        @RequestParam id: String
    ): ResponseEntity<ProductDocument> {
        log.info("findOne")
        return ResponseEntity.ok().body(productService.findOne(id))
    }

    @PostMapping("/add")
    fun add(
        @RequestBody productDocument: ProductDocument
    ): ResponseEntity<ProductDocument> {
        log.info("add")
        return ResponseEntity.ok().body(productService.add(productDocument))
    }

    @PostMapping("/modify")
    fun modify(
        @RequestBody productDocument: ProductDocument
    ): ResponseEntity<ProductDocument> {
        log.info("modify")
        return ResponseEntity.ok().body(productService.modify(productDocument));
    }

    @DeleteMapping("/remove")
    fun remove(
        @RequestBody productDocument: ProductDocument
    ): ResponseEntity<Unit> {
        log.info("remove")
        return ResponseEntity.ok().body(productService.remove(productDocument))
    }

}
