package com.kenfor.cache;

import com.kenfor.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfor.cache
 * @Description: redis实现Cache
 * @date 2018/5/19 下午2:27
 */
@Component
public class RedisCache<K, V> implements Cache<K,V> {

    private final String CACHE_PREFIX = "shiro-cache";

    @Autowired
    private JedisUtil jedisUtil;

    private byte[] getKey(K key){
        if(key instanceof String){
            return ( key + CACHE_PREFIX).getBytes();
        }
        return SerializationUtils.serialize(key);
    }

    /**
     * 可以加一个本地二级缓存，不必要每次都用redis中获取
     * @param k
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K k) throws CacheException {
        System.out.println("从缓存中获取权限信息");
        byte[] value = jedisUtil.get(getKey(k));
        if(value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key, value);
        jedisUtil.expire(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if(value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }
    //一般不重写
    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
