package com.es.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "pms", type="product", shards = 1, replicas = 0)
public class EsProduct implements Serializable {


}
