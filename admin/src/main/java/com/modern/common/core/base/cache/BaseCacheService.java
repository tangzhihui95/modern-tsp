package com.modern.common.core.base.cache;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.modern.common.core.redis.CacheTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by piaomiao on 2019/10/9.
 */
@Component
public abstract class BaseCacheService {

	@Autowired
	protected CacheTemplate cache;

	protected static Long CACHE_EXPIRED_DAY = Long.valueOf(24 * 60 * 60);

	protected static Long CACHE_EXPIRED = Long.valueOf(7 * 24 * 60 * 60);

	public Boolean releaseLock(String lockKey) {
		return cache.del(lockKey);
	}

	public CacheLockResult setLock(String lockKey) {
		return setLock(lockKey, null);
	}

	public CacheLockResult setLock(String lockKey, CacheLockResult lockResult) {
		return setLock(lockKey, "1", null, lockResult);
	}

	public CacheLockResult setTimeLock(String lockKey, Long duration) {
		return setTimeLock(lockKey, duration, null);
	}

	public CacheLockResult setTimeLock(String lockKey, Long duration, CacheLockResult lockResult) {
		return setLock(lockKey, "1", duration, lockResult);
	}

	public CacheLockResult setLock(String lockKey, String lockValue, Long duration, CacheLockResult lockResult) {
		boolean lock = null == duration ? cache.setnx(lockKey, lockValue) : cache.setnx(lockKey, lockValue, duration);
		if (null == lockResult) {
			lockResult = CacheLockResult.builder().lock(lock).lockKey(lockKey).cacheService(this).build();
		} else {
			lockResult.setLock(lock);
		}
		return lockResult;
	}

	public CacheLockResult getLock(String lockKey) {
		boolean lock = null != cache.get(lockKey);
		return CacheLockResult.builder().lock(lock).lockKey(lockKey).cacheService(this).build();
	}

	/**
	 * 删除
	 *
	 * @param key
	 */
	protected void del(String key) {
		cache.del(key);
	}

	/**
	 * 获取值
	 *
	 * @param key
	 * @return
	 */
	protected String get(String key) {
		return (String) cache.get(key);
	}

	/**
	 * 获取值并转换对象
	 *
	 * @param key
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	protected <T> T get(String key, Class<T> clazz) {
		return JSON.parseObject(get(key), clazz);
	}

	/**
	 * 保存值
	 *
	 * @param key
	 * @param o
	 */
	protected void set(String key, Object o) {
		cache.set(key, JSON.toJSONString(o));
	}

	/**
	 * MAP集合 - 获取
	 *
	 * @param key
	 * @return
	 */
	protected Map<String, String> hgetAll(String key) {
		return cache.hgetAll(key);
	}

	/**
	 * MAP集合 - 获取并返回转换对象
	 *
	 * @param key
	 * @param tClass
	 * @param <T>
	 * @return
	 */
	@SneakyThrows
	protected <T> T hgetAll(String key, Class<T> tClass) {
		Map<String, String> stringStringMap = hgetAll(key);
		if (null == stringStringMap || stringStringMap.isEmpty()) {
			return null;
		}
		return JSON.parseObject(JSON.toJSONString(stringStringMap), tClass);
	}

	/**
	 * MAP集合 - 值自增长
	 *
	 * @param key
	 * @param hkey
	 */
	protected void hmIncr(String key, String hkey) {
		cache.hincr(key, hkey, 1L);
	}

	/**
	 * MAP集合 - 值负增长
	 *
	 * @param key
	 * @param hkey
	 */
	protected void hmDecr(String key, String hkey) {
		cache.hincr(key, hkey, -1L);
	}


	/**
	 * 设置值
	 *
	 * @param key
	 * @param o
	 */
	protected void setex(String key, Object o) {
		cache.set(key, JSON.toJSONString(o));
	}

	public Set<ZSetOperations.TypedTuple<String>> getZsetPageListOrderDesc(String key, Integer pageNumber, Integer pageSize, Long nextSeq) {

		Long start = (pageNumber.longValue() - 1) * pageSize.longValue();
		Long end = pageSize.longValue();

		if (0 == nextSeq) {
			nextSeq = this.listMaxScore(key);
		}

		Set<ZSetOperations.TypedTuple<String>> typedTuples = cache.zrevrangeByScoreWithScores(key, 0, nextSeq, start, end);
		if (null == typedTuples) {
			typedTuples = new TreeSet();
		}
		return typedTuples;
	}

	public Set<ZSetOperations.TypedTuple<String>> getZsetPageListOrderAsc(String key, Integer pageNumber, Integer pageSize, Long nextSeq) {

		Long start = (pageNumber.longValue() - 1) * pageSize.longValue();
		Long end = pageSize.longValue();

		if (0 == nextSeq) {
			nextSeq = this.listMaxScore(key);
		}

		Set<ZSetOperations.TypedTuple<String>> typedTuples = cache.zrangeByScoreWithScores(key, 0, nextSeq, start, end);
		if (null == typedTuples) {
			typedTuples = new TreeSet();
		}
		return typedTuples;
	}

	public Long listMaxScore(String key) {
		Set<ZSetOperations.TypedTuple<String>> typedTuples = cache.zrevrangeWithScorese(key, 0L, 0L);
		if (typedTuples.isEmpty()) {
			return 0L;
		}
		long l = typedTuples.iterator().next().getScore().longValue();
		return 0 == l ? 1 : l;
	}


	public void listMemberAdd(String key, String value, Long seq) {
		cache.zadd(key, value, seq.doubleValue());
	}

	public void listIncrScore(String key, String value, Long seq) {
		cache.zincrby(key, value, seq);
	}

	public Long listMemberRemove(String key, String value) {
		return cache.zrem(key, value);
	}

	public void listClear(String key) {
		cache.del(key);
	}

	public Long listSize(String key) {
		return cache.zsize(key);
	}

	public Set<ZSetOperations.TypedTuple<String>> listPage(String key, Integer pageNumber, Integer pageSize, Long nextSeq) {
		return this.getZsetPageListOrderDesc(key, pageNumber, pageSize, nextSeq);
	}

	public Set<ZSetOperations.TypedTuple<String>> listAll(String key) {
		Long nextSeq = this.listMaxScore(key);
		Set<ZSetOperations.TypedTuple<String>> typedTuples = cache.zrevrangeByScoreWithScores(key, 0, nextSeq, 0L, nextSeq);
		if (null == typedTuples) {
			typedTuples = new TreeSet();
		}
		return typedTuples;
	}

	public Boolean listMemberIsExist(String key, String member) {
		Long zrank = cache.zrank(key, member);
		return null != zrank;
	}

	public Long listMemberScore(String key, String member) {
		return cache.zscore(key, member);
	}

	/**
	 * 获取所状态
	 *
	 * @param lockKey
	 * @return
	 */
	public Boolean getLockState(String lockKey) {
		String value = (String) cache.get("LOCK_STATE::" + lockKey);
		return StringUtil.isNotEmpty(value);
	}

	/**
	 * 设置锁状态
	 *
	 * @param lockKey
	 */
	public void setLockState(String lockKey) {
		setLockState(lockKey, 60L);
	}

	public void setLockState(String lockKey, Long expired) {
		if (null == expired || 0 == expired) {
			cache.set("LOCK_STATE::" + lockKey, "1");

		} else {
			cache.setex("LOCK_STATE::" + lockKey, "1", expired);

		}
	}

	/**
	 * 删除锁状态
	 *
	 * @param lockKey
	 */
	public Boolean delLockState(String lockKey) {
		return cache.del("LOCK_STATE::" + lockKey);
	}

}
