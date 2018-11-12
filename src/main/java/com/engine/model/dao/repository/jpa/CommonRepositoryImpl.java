package com.engine.model.dao.repository.jpa;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.EntityInformation;
import wuhao.tools.beans.Pager;
import wuhao.tools.hibernate.Inquiry;
import wuhao.tools.utils.StringUtil;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuhao on 2016/5/6.
 */
public class CommonRepositoryImpl<T, ID extends Serializable>
		extends SimpleJpaRepository<T, ID> implements CommonRepository<T, ID> {

	protected EntityManager em;
	protected EntityInformation ei;

	public CommonRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
		this.ei = entityInformation;
	}


	protected static Logger log = Logger.getLogger(CommonRepository.class);

	private int count(String hql) {
		long currentTime = System.currentTimeMillis();
		try {
			if (hql.trim().toLowerCase().startsWith("from")) {
				hql = "select count (*) " + hql.trim();
			} else if (hql.trim().toLowerCase().startsWith("where")) {
				hql = "select count(*) from " + ei.getJavaType().getSimpleName() + " " + hql.trim();
			} else if (!hql.trim().toLowerCase().contains("from") && !hql.trim().toLowerCase().contains("where")) {
				hql = "select count(*) from " + ei.getJavaType().getSimpleName() + " where " + hql.trim();
			}
			if (hql.toLowerCase().contains("order") && hql.toLowerCase().contains("by")) {
				hql = hql.substring(0, hql.toLowerCase().lastIndexOf("order"));
			}
			int result = Integer.parseInt(find(hql).get(0).toString());
			log(System.currentTimeMillis() - currentTime, "执行HQL查询用时：" + (System.currentTimeMillis() - currentTime) + "毫秒 -->执行结果：" + result + "|" + hql);
			return result;
		} catch (Exception e) {
			log.error(hql,e);
			return -1;
		}
	}

	@Override
	public int count(Inquiry inquiry) {
		return count(inquiry.getHql());
	}

	public Map<Integer, Integer> countByGroupProperty(String propertyName) {
		try {
			long currentTime = System.currentTimeMillis();
			String queryString = "select " + propertyName + ",count(" + propertyName + ") from " + ei.getJavaType().getSimpleName() + " ";
			queryString += " group by " + propertyName;
			List list = find(queryString);
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for (Object object : list) {
				if (object.getClass().isArray()) {
					int arrayLength = Array.getLength(object);
					if (arrayLength == 2) {
						if (Array.get(object, 0) == null) {
							continue;
						}
						Integer id = toInteger(Array.get(object, 0));
						Integer count = toInteger(Array.get(object, 1));
						map.put(id, count);
					}
				}
			}
			log.debug("执行HQL查询用时" + (System.currentTimeMillis() - currentTime) + "毫秒:" + queryString);
			return map;
		} catch (Exception e) {
			log.error(null, e);
			return new HashMap<Integer, Integer>();
		}
	}

	public Map<Object, Integer> countByGroupProperty(Inquiry inquiry) {
		try {
			List<T> list = find(inquiry);
			Map<Object, Integer> map = new HashMap<Object, Integer>();
			for (T object : list) {
				map.put(Array.get(object, 0), ((Long) Array.get(object, 1)).intValue());
			}
			return map;
		} catch (Exception e) {
			log.error("执行HQL查询出错：" + inquiry, e);
			return null;
		}
	}

	private static Integer toInteger(Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		} else if (object instanceof Integer) {
			return (Integer) object;
		} else if (object instanceof Short) {
			return ((Short) object).intValue();
		} else if (object instanceof Long) {
			return ((Long) object).intValue();
		} else if (object instanceof Byte) {
			return ((Byte) object).intValue();
		} else {
			return null;
		}
	}

	@Override
	public int executeUpdate(Inquiry inquiry) {
		return executeUpdate(inquiry.getHql());
	}

	@Override
	public int executeUpdate(List<Inquiry> inquiries) {
		Session session = null;
		try {
			int count = 0;
			session = em.unwrap(Session.class);
			try {
				for (Inquiry inquiry : inquiries) {
					Query query = session.createQuery(inquiry.toString());
					int i = query.executeUpdate();
					count += i;
				}
				session.flush();
				return count;
			} catch (Exception e) {
				e.printStackTrace();
				for (Inquiry inquiry : inquiries) {
					int result = executeUpdate(inquiry);
					if (result > 0) {
						count++;
					}
				}
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private int executeUpdate(String queryString) {
		long currentTime = System.currentTimeMillis();
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			Query query = session.createQuery(queryString);
			int result = query.executeUpdate();
			log(System.currentTimeMillis() - currentTime, "执行HQL更新用时：" + (System.currentTimeMillis() - currentTime) + "毫秒 -->执行结果：" + result + "|" + queryString);
			return result;
		} catch (Exception e) {
			log.error("执行HQL更新出错：" + queryString, e);
			return -1;
		}
	}

	@Override
	public List find(Inquiry inquiry) {
		return find(inquiry, new Pager(1, -1));
	}

	@Override
	public List find(Inquiry inquiry, Pager pager) {
		return find(inquiry, (int)pager.getCurrentIndex(), (int)pager.getLimit());
	}

	@Override
	public List find(Inquiry inquiry, int start, int limit) {
		return find(inquiry.getHql(), start, limit);
	}


	private List find(String queryString, int start, int limit) {
		long currentTime = System.currentTimeMillis();
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			Query query = session.createQuery(queryString);
			if(start>=0){
				query.setFirstResult(start);
			}
			if(limit>0){
				query.setMaxResults(limit);
			}
			List list = query.list();
			log(System.currentTimeMillis() - currentTime, "执行HQL查询用时：" + (System.currentTimeMillis() - currentTime) + "毫秒 -->" + "起始" + start + "|结束" + (start + limit) + "|返回" + list.size() + "|" + queryString);
			return list;
		} catch (Exception e) {
			log.error("HQL执行错误：" + queryString, e);
			return null;
		}
	}

	private void log(long time, String content) {
		if (time >= 30000) {
			log.info(content);
		} else if (time > 10000 && time < 30000) {
			log.debug(content);
		} else {
			log.debug(content);
		}
	}

	private List<T> find(String queryString) {
		return find(queryString, -1, -1);
	}

	@Override
	public List<T> findByIdList(List<ID> ids) {
		return find(Inquiry.forClass(ei.getJavaType()).addIn("id",ids));
	}

	@Override
	public List<T> findByIds(ID[] ids) {
		return findByIdList(Arrays.asList(ids));
	}


	@Override
	public List<T> findByProperty(String name, Object value) {
		return find(Inquiry.forClass(ei.getJavaType()).addEq(name,value));
	}

	@Override
	public List findBySql(String sql) {
		return findBySql(sql,-1,-1);
	}

	public List findBySql(String sql,int start,int limit){
		long currentTime = System.currentTimeMillis();
		try {
			javax.persistence.Query query = em.createNativeQuery(sql);
			if(start>=0){
				query.setFirstResult(start);
			}
			if(limit>0){
				query.setMaxResults(limit);
			}
			List<?> list = query.getResultList();
			log(System.currentTimeMillis() - currentTime, "执行SQL查询用时：" + (System.currentTimeMillis() - currentTime) + "毫秒 -->" + "起始" + start + "|返回" + limit + "|" + sql);
			return list;
		} catch (Exception e) {
			log.error("SQL错误: " + "起始" + start + "|返回" + limit + "|" + sql + "|" + this.getClass().getName() + ":\t" + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public int saveAll(List<T> list) {
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			for (Object object : list) {
				session.save(object);
			}
			session.flush();
			return list.size();
		} catch (Exception e) {
			log.info("对象批量存储出错，进行单个对象存储");
			int count = 0;
			try {
				for (T object : list) {
					try {
						save(object);
						count++;
					} catch (Exception e1) {
						if (StringUtil.getStackTraceAsString(e1).indexOf("Duplicate entry") >= 0) {
							log.error(e1);
						} else {
							e1.printStackTrace();
						}
					}
				}
			} catch (Exception e2) {
				log.error(null, e2);
			}
			return count;
		}
	}

	@Override
	public int saveOrUpdateAll(List<T> list) {
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			for (Object object : list) {
				session.saveOrUpdate(object);
			}
			session.flush();
			return list.size();
		} catch (Exception e) {
			log.info("对象批量存储出错，进行单个对象存储");
			int count = 0;
			try {
				for (T object : list) {
					try {
						if(saveOrUpdate(object)){
							count++;
						}
					} catch (Exception e1) {
						if (StringUtil.getStackTraceAsString(e1).indexOf("Duplicate entry") >= 0) {
							log.error(e1);
						} else {
							e1.printStackTrace();
						}
					}
				}
			} catch (Exception e2) {
				log.error(null, e2);
			}
			return count;
		}
	}

	@Override
	public boolean saveOrUpdate(T object) {
		try {
			Session session = em.unwrap(Session.class);
			session.saveOrUpdate(object);
			session.flush();
			return true;
		} catch (Exception e) {
			log.error(object, e);
			return false;
		}
	}

	@Override
	public <T> T unique(Inquiry inquiry) {
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			Query query = session.createQuery(inquiry.getHql());
			query.setCacheable(true);
			return (T) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public <T> T uniqueResult(String property, Object value) {
		return unique(Inquiry.forClass(ei.getJavaType()).addEq(property,value));
	}

	@Override
	public <T> T uniqueResultExcludeId(ID id, String property, Object value) {
		return unique(Inquiry.forClass(ei.getJavaType()).addEq(property,value).addNe("id",id));
	}

	@Override
	public boolean update(T object) {
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			session.update(object);
			session.flush();
			return true;
		} catch (Exception e) {
			log.error(null, e);
			return false;
		}
	}

	@Override
	public int updateAll(List<T> list) {
		Session session = null;
		try {
			session = em.unwrap(Session.class);
			for (Object object : list) {
				session.saveOrUpdate(object);
			}
			session.flush();
			return list.size();
		} catch (Exception e) {
			log.info("对象批量存储出错，进行单个对象存储");
			int count = 0;
			try {
				for (T object : list) {
					try {
						boolean result = saveOrUpdate(object);
						if(result){
							count++;
						}
					} catch (Exception e1) {
						if (StringUtil.getStackTraceAsString(e1).indexOf("Duplicate entry") >= 0) {
							log.error(e1);
						} else {
							e1.printStackTrace();
						}
					}
				}
			} catch (Exception e2) {
				log.error(null, e2);
			}
			return count;
		}
	}
}
