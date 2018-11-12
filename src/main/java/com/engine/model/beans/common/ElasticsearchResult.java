package com.engine.model.beans.common;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.domain.Page;

/**
 * Created by wuhao on 2016/6/16.
 */
public class ElasticsearchResult<T>{

	private Page<T> page;
	private Aggregations aggregations;
	private TimeValue took;
	private String scrollId;

	public ElasticsearchResult(Page<T> page){
		this.page = page;
	}

	public ElasticsearchResult(Page<T> page, Aggregations aggregations) {
		this.page = page;
		this.aggregations = aggregations;
	}

	public Page<T> getPage() {
		return page;
	}

	public Aggregations getAggregations() {
		return aggregations;
	}

	public <A extends Aggregation> A getAggregation(String name) {
		return aggregations.get(name);
	}

	public <T> ElasticsearchResult<T> took(TimeValue took) {
		this.took = took;
		return (ElasticsearchResult<T>) this;
	}

	public <T> ElasticsearchResult<T> scrollId(String scrollId) {
		this.scrollId = scrollId;
		return (ElasticsearchResult<T>) this;
	}

	public TimeValue getTook() {
		return took;
	}

	public String getScrollId() {
		return scrollId;
	}
}
