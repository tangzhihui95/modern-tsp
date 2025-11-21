package com.modern.common.core.redis;
/**
 * Created by piaomiao on 2019/9/24.
 */

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 * Copyright: Copyright (C) 2019 ###$$$tlcd, Inc. All rights reserved.
 * <p>
 * Company: ###$$$科技有限公司
 * <p>
 *
 * @author piaomiao
 * @since 2019/9/24 3:55 PM
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheTemplate<T> {

	private final RedisTemplate redisTemplate;

	private final RedissonClient redissonClient;

//	private final RedisConfig redissonConfig;

	/**
	 * 获取操作key/value对象
	 * key/value
	 *
	 * @return
	 */
	private ValueOperations<String, T> getKeyValueOperations() {
//		RBucket<Object> bucket = redissonClient.getBucket();
		return redisTemplate.opsForValue();
	}

	/**
	 * 获取操作有序集合的对象
	 * zset
	 *
	 * @return
	 */
	private ZSetOperations<String, T> getZsetOperations() {
		return redisTemplate.opsForZSet();
	}

	/**
	 * 获取操作Map/hash集合的对象
	 * hmap
	 *
	 * @return
	 */
	private HashOperations<String, String, T> getHsetOperations() {
		return redisTemplate.opsForHash();
	}

	/**
	 * 获取操作栈/队列的对象
	 * list 有序集合
	 *
	 * @return
	 */
	private ListOperations<String, T> getListOperations() {
		return redisTemplate.opsForList();
	}

	/**
	 * 获取操作set集合的对象
	 * set 无序集合
	 *
	 * @return
	 */
	private SetOperations<String, T> getSetOperations() {
		return redisTemplate.opsForSet();
	}

	/**
	 * 设置有效时间 单位秒
	 *
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Boolean setExpire(String key, Long seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 获取有效期 单位秒
	 * 命令 ttl
	 *
	 * @param key
	 * @return
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断是否有值
	 *
	 * @param key 键
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * kv存储
	 */

	/**
	 * 读数据
	 * get
	 *
	 * @param key 键
	 * @return
	 */
	public T get(String key) {
		return getKeyValueOperations().get(key);
	}

	/**
	 * 删除数据
	 * del
	 *
	 * @param key 键
	 * @return
	 */
	public Boolean del(String key) {
		return redisTemplate.delete(key);
	}


	/**
	 * 写数据(key - value)
	 * set
	 *
	 * @param key   键
	 * @param value 值
	 */
	public void set(String key, T value) {
		getKeyValueOperations().set(key, value);
	}

	/**
	 * 写数据(key - value),设置过期时间
	 * setex
	 *
	 * @param key     键
	 * @param value   值
	 * @param seconds 过期时间(秒)
	 */
	public void setex(String key, T value, Long seconds) {
		getKeyValueOperations().set(key, value, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 设置缓存锁
	 * setnx
	 *
	 * @param key   键
	 * @param value 值
	 */
	public Boolean setnx(String key, T value) {
		return getKeyValueOperations().setIfAbsent(key, value);
	}

	/**
	 * 设置缓存锁 带有效期
	 * setnx
	 *
	 * @param key   键
	 * @param value 值
	 */
	public Boolean setnx(String key, T value, Long seconds) {
		return getKeyValueOperations().setIfAbsent(key, value, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 将 key 中储存的数字值增一。
	 * incr
	 *
	 * @param key 键
	 * @return
	 */
	public Long incr(String key) {
		return getKeyValueOperations().increment(key);
	}

	/**
	 * 增加数值
	 * incr
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public Long incr(String key, Long count) {
		return getKeyValueOperations().increment(key, count);
	}

	/**
	 * 减少数值
	 * decr
	 *
	 * @param key
	 * @return
	 */
	public Long decr(String key) {
		return getKeyValueOperations().decrement(key);
	}

	/**
	 * 减少数值
	 * decr
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	public Long decr(String key, Long count) {
		return getKeyValueOperations().decrement(key, count);
	}

	/**
	 * hash Map类
	 */

	/**
	 * mapput写数据
	 * hset
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hset(String key, String hashKey, T value) {
		getHsetOperations().put(key, hashKey, value);
	}

	/**
	 * map加锁
	 * hsetnx
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public boolean hsetnx(String key, String hashKey, T value) {
		return getHsetOperations().putIfAbsent(key, hashKey, value);
	}

	/**
	 * map增加数值
	 * hincr
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	public Long hincr(String key, String hashKey, Long value) {
		return getHsetOperations().increment(key, hashKey, value);
	}

	/**
	 * mapputall
	 * hset
	 *
	 * @param key
	 * @param m
	 */
	public void hset(String key, Map<String, T> m) {
		getHsetOperations().putAll(key, m);
	}

	/**
	 * map长度
	 * hlen
	 *
	 * @param key
	 * @return
	 */
	public Long hlen(String key) {
		return getHsetOperations().size(key);
	}

	/**
	 * map删除
	 * hdel
	 *
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public Long hdel(String key, String mapKey) {
		return getHsetOperations().delete(key, mapKey);
	}

	/**
	 * map批量删除
	 * hdel
	 *
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public Long hdel(String key, Set<String> mapKey) {
		return getHsetOperations().delete(key, mapKey);
	}

	/**
	 * map判断是否存在
	 * hexists
	 *
	 * @param key
	 * @param hasKey
	 * @return
	 */
	public boolean hexists(String key, String hasKey) {
		return getHsetOperations().hasKey(key, hasKey);
	}

	/**
	 * map获取所有
	 * hgetAll
	 *
	 * @param key
	 * @return
	 */
	public Map<String, T> hgetAll(String key) {
		return getHsetOperations().entries(key);
	}

	/**
	 * map获取所有key
	 * hkeys
	 *
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key) {
		return getHsetOperations().keys(key);
	}

	/**
	 * map获取mkey值
	 * hget
	 *
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public T hget(String key, String mapKey) {
		return getHsetOperations().get(key, mapKey);
	}

	/**
	 * map获取所有mkey值
	 *
	 * @param key
	 * @return
	 */
	public List<T> hvals(String key) {
		return getHsetOperations().values(key);
	}

	/**
	 * zset 有序集合
	 */

	/**
	 * @param key
	 * @param member
	 * @param score
	 * @return
	 */
	public Boolean zadd(String key, T member, double score) {
		return getZsetOperations().add(key, member, score);
	}

	public Long zrank(String key, String member) {
		return getZsetOperations().rank(key, member);
	}

	public Long zrevrank(String key, String member) {
		return getZsetOperations().reverseRank(key, member);
	}

	public Long zscore(String key, String member) {
		Double score = getZsetOperations().score(key, member);
		return null == score ? 0L : score.longValue();
	}

	public Double zscoreDecimal(String key, String member) {
		return getZsetOperations().score(key, member);
	}

	public Long zsize(String key) {
		return getZsetOperations().size(key);
	}

	public Long zlexcount(String key, Long min, Long max) {
		return getZsetOperations().count(key, min, max);
	}

	public Long zrem(String key, String member) {
		return getZsetOperations().remove(key, member);
	}

	/**
	 * 有序集合 删除 根据分数删除
	 * zremRangeByScore
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zremRangeByScore(String key, double min, double max) {
		return getZsetOperations().removeRangeByScore(key, min, max);
	}

	/**
	 * 有序集合 删除 根据索引/角标删除
	 * zremRangeByRank
	 *
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Long zremRangeByRank(String key, Long start, Long stop) {
		return getZsetOperations().removeRange(key, start, stop);
	}

	/**
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<T>> zrangeByScoreWithScores(String key, double min, double max) {
		return getZsetOperations().rangeByScoreWithScores(key, min, max);
	}

	public Set<ZSetOperations.TypedTuple<T>> zrangeByScoreWithScores(String key, double min, double max, Long offset, Long count) {
		return getZsetOperations().rangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<ZSetOperations.TypedTuple<T>> zrevrangeByScoreWithScores(String key, double min, double max, Long offset, Long count) {
		return getZsetOperations().reverseRangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<ZSetOperations.TypedTuple<T>> zrevrangeByScoreWithScores(String key, double min, double max) {
		return getZsetOperations().reverseRangeByScoreWithScores(key, min, max);
	}

	public Set<ZSetOperations.TypedTuple<T>> zrevrangeWithScorese(String key, Long start, Long end) {
		return getZsetOperations().reverseRangeWithScores(key, start, end);
	}

	/*
	 * 队列
	 */
	public Long lpush(String key, T value) {
		return getListOperations().leftPush(key, value);
	}

	public T rpop(String key) {
		return getListOperations().rightPop(key);
	}

	public T lpop(String key) {
		return getListOperations().leftPop(key);
	}

	public Long rpush(String key, T value) {
		return getListOperations().rightPush(key, value);
	}

	public Long llen(String key) {
		return getListOperations().size(key);
	}


	public Cursor<T> scan(String key, String cursor, Long count) {
		SetOperations<String, T> set = getSetOperations();
		ScanOptions.ScanOptionsBuilder scanOptionsBuilder = ScanOptions.scanOptions();
		if (0 == count) {
			count = 100L;
		}
		scanOptionsBuilder.count(count);
		scanOptionsBuilder.match(cursor);
		ScanOptions build = scanOptionsBuilder.build();
		Cursor<T> scan = set.scan(key, build);
		return scan;
	}

	/**
	 * 无序集合
	 */

	/**
	 * 添加成员
	 *
	 * @param key
	 * @param member
	 */
	public void sadd(String key, T member) {
		getSetOperations().add(key, member);
	}

	public Boolean sismember(String key, T member) {
		return getSetOperations().isMember(key, member);
	}

	public Long srem(String key, T value) {
		return getSetOperations().remove(key, value);
	}

	public Set<T> members(String key) {
		return getSetOperations().members(key);
	}

	/**
	 * scan 实现
	 *
	 * @param pattern  表达式
	 * @param consumer 对迭代到的key进行操作
	 */
	public void scan(String pattern, Consumer<byte[]> consumer) {
		redisTemplate.execute((RedisConnection connection) -> {
			try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
				cursor.forEachRemaining(consumer);
				return null;
			}
		});
	}

	/**
	 * 获取符合条件的key
	 *
	 * @param pattern 表达式
	 * @return
	 */
	public List<String> keys(String pattern) {
		List<String> keys = new ArrayList<>();
		this.scan(pattern, item -> {
			//符合条件的key
			String key = new String(item, StandardCharsets.UTF_8);
			keys.add(key);
		});
		return keys;
	}

	public void zincrby(String key, T value, double score) {
		getZsetOperations().incrementScore(key, value, score);
	}

}
