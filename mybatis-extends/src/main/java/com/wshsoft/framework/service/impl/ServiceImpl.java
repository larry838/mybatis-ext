package com.wshsoft.framework.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.wshsoft.framework.service.IService;
import com.wshsoft.mybatis.annotations.IdType;
import com.wshsoft.mybatis.exceptions.MybatisExtendsException;
import com.wshsoft.mybatis.mapper.BaseMapper;
import com.wshsoft.mybatis.mapper.EntityWrapper;
import com.wshsoft.mybatis.plugins.pagination.Page;
import com.wshsoft.mybatis.toolkit.CollectionUtil;
import com.wshsoft.mybatis.toolkit.ReflectionKit;
import com.wshsoft.mybatis.toolkit.TableInfo;
import com.wshsoft.mybatis.toolkit.TableInfoHelper;

/**
 * <p>
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * </p>
 *
 * @author carry xie
 * @Date 2016-04-20
 */
public class ServiceImpl<M extends BaseMapper<T, PK>, T, PK extends Serializable> implements IService<T, PK> {

	protected static final Logger logger = Logger.getLogger("ServiceImpl");

	@Autowired
	protected M baseMapper;

	/**
	 * 判断数据库操作是否成功
	 *
	 * @param result
	 *            数据库操作返回影响条数
	 * @return boolean
	 */
	protected boolean retBool(int result) {
		return result >= 1;
	}

	/**
	 * <p>
	 * TableId 注解存在更新记录，否插入一条记录
	 * </p>
	 *
	 * @param entity
	 *            实体对象
	 * @param isSelective
	 *            true 选择字段 false 不选择字段
	 * @return boolean
	 */
	public boolean insertOrUpdate(T entity, boolean isSelective) {
		if (null != entity) {
			Class<?> cls = entity.getClass();
			TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
			if (null != tableInfo) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
				if (null == idVal || "".equals(idVal)) {
					return isSelective ? insertSelective(entity) : insert(entity);
				} else {
					/* 特殊处理 INPUT 主键策略逻辑 */
					if (IdType.INPUT == tableInfo.getIdType()) {
						T entityValue = selectById((Serializable) idVal);
						if (null != entityValue) {
							return isSelective ? updateSelectiveById(entity) : updateById(entity);
						} else {
							return isSelective ? insertSelective(entity) : insert(entity);
						}
					}
					return isSelective ? updateSelectiveById(entity) : updateById(entity);
				}
			} else {
				throw new MybatisExtendsException("Error:  Cannot execute. Could not find @TableId.");
			}
		}
		return false;
	}

	public boolean insertOrUpdate(T entity) {
		return insertOrUpdate(entity, false);
	}

	public boolean insertOrUpdateSelective(T entity) {
		return insertOrUpdate(entity, true);
	}

	public boolean insert(T entity) {
		return retBool(baseMapper.insert(entity));
	}

	public boolean insertSelective(T entity) {
		return retBool(baseMapper.insertSelective(entity));
	}

	public boolean insertBatch(List<T> entityList) {
		if (null == entityList) {
			throw new IllegalArgumentException("entityList must not be empty");
		}
		return retBool(baseMapper.insertBatch(entityList));
	}

	public boolean insertBatchSelective(List<T> entityList, int batchSize) {
		return insertBatch(entityList, batchSize, true);
	}

	public boolean insertBatch(List<T> entityList, int batchSize) {
		return insertBatch(entityList, batchSize, false);
	}

	/**
	 * 批量插入
	 *
	 * @param entityList
	 * @param batchSize
	 * @param isSelective
	 * @return
	 */
	protected boolean insertBatch(List<T> entityList, int batchSize, boolean isSelective) {
		if (null == entityList) {
			throw new IllegalArgumentException("entityList must not be empty");
		}
		TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModleClass());
		if (null == tableInfo) {
			throw new MybatisExtendsException("Error: insertBatch Fail, ClassGenricType not found .");
		}
		SqlSession batchSqlSession = tableInfo.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			int size = entityList.size();
			for (int i = 0; i < size; i++) {
				if (isSelective) {
					baseMapper.insertSelective(entityList.get(0));
				} else {
					baseMapper.insert(entityList.get(0));
				}
				if (i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
			}
			batchSqlSession.flushStatements();
		} catch (Exception e) {
			logger.warning("Warn: Method insertBatch Fail. Cause:" + e);
			return false;
		}
		return true;

	}

	@SuppressWarnings("unchecked")
	protected Class<T> currentModleClass() {
		return ReflectionKit.getSuperClassGenricType(getClass(), 1);
	}

	public boolean insertBatchSelective(List<T> entityList) {
		if (null == entityList) {
			throw new IllegalArgumentException("entityList must not be empty");
		}
		int result = 0;
		for (T t : entityList) {
			result = baseMapper.insertSelective(t);
			if (result <= 0) {
				break;
			}
		}
		return retBool(result);
	}

	public boolean deleteById(Serializable id) {
		return retBool(baseMapper.deleteById(id));
	}

	public boolean deleteByMap(Map<String, Object> columnMap) {
		return retBool(baseMapper.deleteByMap(columnMap));
	}

	public boolean deleteSelective(T entity) {
		return retBool(baseMapper.deleteSelective(entity));
	}

	public boolean deleteBatchIds(List<? extends Serializable> idList) {
		return retBool(baseMapper.deleteBatchIds(idList));
	}

	public boolean updateById(T entity) {
		return retBool(baseMapper.updateById(entity));
	}

	public boolean updateSelectiveById(T entity) {
		return retBool(baseMapper.updateSelectiveById(entity));
	}

	public boolean update(T entity, T whereEntity) {
		return retBool(baseMapper.update(entity, whereEntity));
	}

	public boolean updateSelective(T entity, T whereEntity) {
		return retBool(baseMapper.updateSelective(entity, whereEntity));
	}

	public boolean updateBatchById(List<T> entityList) {
		return retBool(baseMapper.updateBatchById(entityList));
	}

	public T selectById(Serializable id) {
		return baseMapper.selectById(id);
	}

	public List<T> selectBatchIds(List<? extends Serializable> idList) {
		return baseMapper.selectBatchIds(idList);
	}

	public List<T> selectByMap(Map<String, Object> columnMap) {
		return baseMapper.selectByMap(columnMap);
	}  

	public T selectOne(T entity) {
		return baseMapper.selectOne(entity);
	}

	public T selectOne(EntityWrapper<T> entityWrapper) {
		List<T> list = baseMapper.selectList(entityWrapper);
		if (CollectionUtil.isNotEmpty(list)) {
			int size = list.size();
			if (size > 1) {
				logger.warning("Warn: selectOne Method There are " + size + " results.");
			}
			return list.get(0);
		}
		return null;
	}

	public int selectCount(T entity) {
		return baseMapper.selectCount(entity);
	}

	public int selectCount(EntityWrapper<T> entityWrapper) {
		return baseMapper.selectCountByEw(entityWrapper);
	}

	public List<T> selectList(EntityWrapper<T> entityWrapper) {
		return baseMapper.selectList(entityWrapper);
	}

	public Page<T> selectPage(Page<T> page, EntityWrapper<T> entityWrapper) {
		if (null != entityWrapper) {
			entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
		}
		page.setRecords(baseMapper.selectPage(page, entityWrapper));
		return page;
	}

}