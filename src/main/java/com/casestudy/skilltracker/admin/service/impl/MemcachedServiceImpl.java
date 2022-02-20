package com.casestudy.skilltracker.admin.service.impl;

import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.service.MemcachedService;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemcachedServiceImpl implements MemcachedService<AssociateProfile> {

    @Autowired
    MemcachedClient memcachedClient;

    @Override
    public OperationFuture<Boolean> set(String key, AssociateProfile associateProfile) {
        if (memcachedClient != null) {
            log.debug("put profile in cache for associate id:{},{}", key, associateProfile.toString());
            return memcachedClient.set(key, 3600, associateProfile);
        }
        return null;
    }

    @Override
    public AssociateProfile get(String key) {
        AssociateProfile associateProfile = null;
        if (memcachedClient != null)
            associateProfile = (AssociateProfile) memcachedClient.get(key);
        if (associateProfile != null) {
            log.info("profile found in cache for associate id:{}", key);
            return associateProfile;
        }
        return null;
    }
}
