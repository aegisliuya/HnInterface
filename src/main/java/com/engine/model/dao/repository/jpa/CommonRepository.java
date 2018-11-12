package com.engine.model.dao.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import wuhao.tools.beans.Pager;
import wuhao.tools.hibernate.Inquiry;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wuhao on 2016/5/6.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID>,JpaRepository<T,ID>{

	List<T> find(Inquiry inquiry, Pager pager);

	/**
	 * 保存或更新对象
	 * @param object
	 * @return
	 */
	@Modifying
	@Transactional
	boolean saveOrUpdate(T object);

	/**
	 * @param inquiry
	 * @return
	 */
	@Modifying
	@Transactional
	int executeUpdate(Inquiry inquiry);


	/**
	 * 批量更新对象
	 * @param list
	 * @return
	 */
	@Modifying
	@Transactional
	int updateAll(List<T> list);

	Map<? extends Object, Integer> countByGroupProperty(Inquiry inquiry);

	/**
	 * 执行SQL查询
	 * @param sql
	 *            sql语句
	 * @return
	 */
	List findBySql(String sql);

	/**
	 * 批量执行更新操作
	 * @param inquiries
	 * @return
	 */
	@Modifying
	@Transactional
	int executeUpdate(List<Inquiry> inquiries);




	/**
	 * 按某一属性统计该属性不同值时的数据条数 如按category1Id统计subinfo，则返回的Map中的key是category1Id，
	 * value是当category1Id等于key时subinfo的条数
	 * @param propertyName 要统计的字段名称 以数据库中的字段名为准
	 * @return
	 */
	Map<Integer, Integer> countByGroupProperty(String propertyName);



	List<T> findByProperty(String name, Object value);

	/**
	 * 保存多个对象到数据库
	 * @param list
	 * @return
	 */
	@Modifying
	@Transactional
	int saveAll(List<T> list);


	@Modifying
	@Transactional
	int saveOrUpdateAll(List<T> list);

	/**
	 * 更新对象在数据库中的记录
	 * @param object
	 * @return
	 */
	@Modifying
	@Transactional
	boolean update(T object);


	/**
	 * 根据ID列表获取对象列表
	 * @param idList
	 * @return
	 */
	List<T> findByIdList(List<ID> idList);


	List<T> findByIds(ID[] ids);
	/**
	 * 根据属性查找唯一的结果 如果根据属性查找的结果不唯一或不存在 则返回null
	 * @param property 属性名称
	 * @param value 属性的值
	 * @return
	 */
	<T> T uniqueResult(String property, Object value);

	/**
	 * 根据属性排除某个id查找唯一结果
	 * @param id 要排除的id
	 * @param property 属性名称
	 * @param value 属性的值
	 * @return
	 */
	<T> T uniqueResultExcludeId(ID id, String property, Object value);

	List find(Inquiry inquiry);

	int count(Inquiry inquiry);

	List find(Inquiry inquiry, int start, int limit);

	<T> T unique(Inquiry inquiry);

}
