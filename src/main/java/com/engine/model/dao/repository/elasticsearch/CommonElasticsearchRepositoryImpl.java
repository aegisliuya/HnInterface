package com.engine.model.dao.repository.elasticsearch;

import com.engine.model.beans.common.ElasticsearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsMapper;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.repository.support.MappingElasticsearchEntityInformation;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhao on 2016/5/25.
 */
public class CommonElasticsearchRepositoryImpl<T, ID extends Serializable>
		extends SimpleElasticsearchRepository<T, ID> implements CommonElasticsearchRepository<T, ID> {


	public CommonElasticsearchRepositoryImpl(MappingElasticsearchEntityInformation ei, ElasticsearchTemplate template) {
		super(ei, template);
	}


	public Page<T> query(SearchQuery query) {
		return elasticsearchOperations.queryForPage(query, entityInformation.getJavaType());
	}

	public void saveAll(Page<T> documents) {
		List<IndexQuery> indexQueries = new ArrayList<>();
		for (T document : documents) {
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setObject(document);
			indexQueries.add(indexQuery);
		}
		elasticsearchOperations.bulkIndex(indexQueries);
	}

	@Override
	public String update(T t) {
		UpdateQuery updateQuery = new UpdateQuery();
		UpdateRequest updateRequest = new UpdateRequest();
		try {
			String json = new ObjectMapper().writeValueAsString(t);
			updateRequest.doc(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		updateQuery.setUpdateRequest(updateRequest);
		updateQuery.setClazz(t.getClass());
		updateQuery.setId(entityInformation.getId(t).toString());
		UpdateResponse response = elasticsearchOperations.update(updateQuery);
		return response.getId();
	}

	@Override
	public void updateAll(List<T> list) {
		List<UpdateQuery> updateQueries = new ArrayList<>();
		for(T t:list){
			UpdateQuery updateQuery = new UpdateQuery();
			UpdateRequest updateRequest = new UpdateRequest();
			try {
				String json = new ObjectMapper().writeValueAsString(t);
				updateRequest.doc(json);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			updateQuery.setUpdateRequest(updateRequest);
			updateQuery.setClazz(t.getClass());
			updateQuery.setId(entityInformation.getId(t).toString());
			updateQueries.add(updateQuery);
		}
		elasticsearchOperations.bulkUpdate(updateQueries);
	}

	public ElasticsearchResult<T> search(SearchQuery query, String[] includes, String[] excludes) {
		return search(query, includes, excludes, query.getPageable());
	}

	@Override
	public ElasticsearchResult scroll(String scrollId) {
		return new ElasticsearchResult<>(elasticsearchOperations.scroll(scrollId,1000*60L*5,entityInformation.getJavaType()));
	}

	public SearchResponse scrollResponse(QueryBuilder queryBuilder, String[] includes, int limit, String sort, SortOrder sortOrder){
		SearchRequestBuilder builder = elasticsearchOperations.getClient().prepareSearch(entityInformation.getIndexName()).setTypes(entityInformation.getType());
		if (sort != null) {
			if (sortOrder == null) {
				sortOrder = SortOrder.ASC;
			}
			builder.addSort(sort, sortOrder);
		}
		if(limit > 0){
			builder.setSize(limit);
		}
		if (includes != null) {
			builder.setFetchSource(includes, null);
		}
		builder.setScroll(new TimeValue(600000));
		builder.setQuery(queryBuilder);
		SearchResponse response = builder.execute().actionGet();
		return response;
	}

	public SearchResponse scrollResponse(String scrollId){
		SearchResponse scrollResponse = elasticsearchOperations.getClient().prepareSearchScroll(scrollId)
				.setScroll(new TimeValue(600000))
				.execute()
				.actionGet();
		return scrollResponse;
	}

	private ElasticsearchResult<T> search(SearchQuery query, String[] includes, String[] excludes, Pageable pageable) {
		SearchRequestBuilder builder = elasticsearchOperations.getClient().prepareSearch(entityInformation.getIndexName()).setTypes(entityInformation.getType());
		if (pageable != null) {
			builder.setSize(pageable.getPageSize());
			builder.setFrom(pageable.getOffset());
		}
		if (query.getQuery() == null) {
			builder.setQuery(QueryBuilders.matchAllQuery());
		} else {
			builder.setQuery(query.getQuery());
		}
		if (query.getAggregations() != null) {
			for (AbstractAggregationBuilder agg : query.getAggregations()) {
				builder.addAggregation(agg);
			}
		}
		if (query.getHighlightFields() != null) {
			for (HighlightBuilder.Field field : query.getHighlightFields()) {
				builder.addHighlightedField(field.name());
			}
		}
		if (includes != null || excludes != null) {
			builder.setFetchSource(includes, excludes);
		}
		if (query.getPageable() != null) {
			builder.setFrom(query.getPageable().getPageNumber());
			builder.setSize(query.getPageable().getPageSize());
		}
		if (query.getElasticsearchSorts() != null) {
			for (SortBuilder sortBuilder : query.getElasticsearchSorts()) {
				builder.addSort(sortBuilder);
			}
		}
		SearchResponse response = builder.get();
		Page<T> page = getResultMapper().mapResults(response, entityInformation.getJavaType(), query.getPageable());
		return new ElasticsearchResult<T>(page, response.getAggregations()).took(response.getTook()).scrollId(response.getScrollId());
	}

	private ResultsMapper getResultMapper() {
		try {
			Field field = elasticsearchOperations.getClass().getDeclaredField("resultsMapper");
			field.setAccessible(true);
			return (ResultsMapper) field.get(elasticsearchOperations);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected String stringIdRepresentation(ID id) {
		return id.toString();
	}
}
