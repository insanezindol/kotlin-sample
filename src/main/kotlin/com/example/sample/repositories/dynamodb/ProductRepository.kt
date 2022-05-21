package com.example.sample.repositories.dynamodb

import com.example.sample.models.ProductDocument
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ProductRepository : CrudRepository<ProductDocument, String>
