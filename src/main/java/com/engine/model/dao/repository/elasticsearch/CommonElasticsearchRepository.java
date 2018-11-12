package com.engine.model.dao.repository.elasticsearch;

import com.engine.model.beans.common.ElasticsearchResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuhao on 2016/5/25.
 */
@NoRepositoryBean
public interface CommonElasticsearchRepository<T, ID extends Serializable> extends ElasticsearchRepository<T, ID> {

	Page<T> query(SearchQuery query);

	void saveAll(Page<T> documents);

	String update(T t);

	void updateAll(List<T> list);

	ElasticsearchResult<T> search(SearchQuery query, String[] includes, String[] excludes);

	ElasticsearchResult scroll(String scrollId);

	SearchResponse scrollResponse(QueryBuilder queryBuilder, String[] includes, int limit, String sort, SortOrder sortOrder);

	SearchResponse scrollResponse(String scrollId);
}
