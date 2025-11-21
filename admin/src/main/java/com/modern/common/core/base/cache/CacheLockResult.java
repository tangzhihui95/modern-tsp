package com.modern.common.core.base.cache;/**
 * Created by piaomiao on 2019/9/29.
 */

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * 缓存锁
 * 下一步修改为,设置锁的线程以及ip,确保是当前线程拿到锁
 * Copyright: Copyright (C) 2019 ###$$$tlcd, Inc. All rights reserved.
 * <p>
 * Company: ###$$$科技有限公司
 * <p>
 *
 * @author piaomiao
 * @since 2019/9/29 2:25 PM
 */
@Builder
public class CacheLockResult {

	@Getter
	@Setter
	private boolean lock;
	@Getter
	private final String lockKey;

	protected BaseCacheService cacheService;

	/**
	 * 释放锁
	 *
	 * @return
	 */
	public Boolean releaseLock() {
		Boolean releaseLock = cacheService.releaseLock(lockKey);
		this.delLockState();
		return releaseLock;
	}

	/**
	 * 设置锁状态
	 */
	public void setLockState() {
		cacheService.setLockState(lockKey);
	}

	/**
	 * 获取锁状态
	 *
	 * @return
	 */
	public Boolean getLockState() {
		return cacheService.getLockState(lockKey);
	}

	public Boolean delLockState() {
		return cacheService.delLockState(lockKey);
	}

	public <T> T waitAndExec(CacheLockWaitAndExec<T> exec) {
		this.waitLockState();
		return exec.run(this);
	}

	/**
	 * 等待锁状态
	 * true 锁无状态 false 没拿到状态
	 *
	 * @return
	 */
	@SneakyThrows
	public boolean waitLockState() {
		int count = 0;
		while (this.getLockState()) {
			if (20 >= count++) {
				Thread.sleep(20);
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 抢占锁
	 *
	 * @return
	 */
	@SneakyThrows
	public boolean preemptLock() {
		Integer count = 0;
		while (!this.lock) {
			if (!lock().isLock() && 20 >= count++) {
				Thread.sleep(20);
			}
		}
		return this.lock;
	}

	public interface CacheLockWaitAndExec<T> {
		T run(CacheLockResult lock);
	}

	private CacheLockResult lock() {
		return cacheService.setTimeLock(lockKey, 120L, this);
	}
}
