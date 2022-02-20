package com.casestudy.skilltracker.admin.service;

import net.spy.memcached.internal.OperationFuture;

public interface MemcachedService<T> {
     OperationFuture<Boolean> set(String key, T o) ;
     T get(String key);
}
