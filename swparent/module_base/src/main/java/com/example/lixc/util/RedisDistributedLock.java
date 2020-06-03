package com.example.lixc.util;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/***
 * redis分布式锁
 * 
 * @author ssr
 */
@Component
public class RedisDistributedLock {


    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /***
     * 锁定key, expire秒后自动解锁
     * @param
     * @param
     * @return 操作成功返回ture,失败返回false;
     */
    public boolean setLock(String lockKey, long expireSeconds) {
        RedisCallback<Boolean> callback = (connection) -> {
            final Boolean success = connection.set(lockKey.getBytes(), new byte[0], Expiration.from(expireSeconds, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
            return success;
        };
        return RedisPoolUtil.getRedisTemplate().execute(callback);
    }

    /***
     * 释放锁
     * @param lockKey
     */
    public void releaseLock(String lockKey) {
        RedisPoolUtil.getRedisTemplate().delete(lockKey);
    }

}
