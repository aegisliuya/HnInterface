//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.engine.model.dao.repository.elasticsearch;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.AbstractElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;

import java.io.Serializable;

public class SimpleElasticsearchRepository<T,ID extends Serializable> extends AbstractElasticsearchRepository<T, ID> {

	public SimpleElasticsearchRepository(ElasticsearchEntityInformation<T, ID> metadata, ElasticsearchOperations elasticsearchOperations) {
		super(metadata, elasticsearchOperations);
	}

	public SimpleElasticsearchRepository(ElasticsearchOperations elasticsearchOperations) {
		super(elasticsearchOperations);
	}

	@Override
	protected String stringIdRepresentation(ID id) {
		return id.toString();
	}
}
