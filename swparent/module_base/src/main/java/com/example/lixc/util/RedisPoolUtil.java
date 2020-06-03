package com.example.lixc.util;


import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/8/9  ljs.
 */
@Component
public class RedisPoolUtil {

	private static RedisTemplate<String, Object> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	/**指定缓存失效时间
	 * @param key
	 * @param time
	 * @return
	 */
	public static boolean expire(String key,long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**根据key获取过期时间
	 * @param key
	 * @return
	 */
	public static long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**判断key是否存在
	 * @param key
	 * @return
	 */
	public static boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			return false;
		}
	}

	/**删除缓存
	 * @param key 可以传一个值或多个
	 */
	public static void del(String ... key) {
		if(key!=null&&key.length>0) {
			if(key.length==1) {
				redisTemplate.delete(key[0]);
			}else {
				redisTemplate.delete(Arrays.asList(key));
			}
		}
	}

//  ==========================String============================
	/**普通缓存获取
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		try {
			String string = redisTemplate.opsForValue().get(key).toString();
			return string;
		} catch (Exception e) {
			return null;
		}
	}

	/**普通缓存放入
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key,Object value) {
		try {
			redisTemplate.opsForValue().set(key,value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**缓存JSON格式放入
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setjson(String key,Object value) {
		try {
			redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
    public static boolean setLock(String lockKey, int expireSeconds) {
        RedisCallback<Boolean> callback = (connection) -> {
            final Boolean success = connection.set(lockKey.getBytes(), new byte[0], Expiration.from(expireSeconds, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
            return success;
        };
        return redisTemplate.execute(callback);
    }

	/**普通缓存放入并设置时间
	 * @param key
	 * @param value
	 * @param time time如果小于等于0，将设置无限期
	 * @return
	 */
	public static boolean set(String key,Object value,long time) {
		try {
			if(time>0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else {
				set(key,value);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**递增或递减
	 * @param key
	 * @param delta
	 * @return
	 */
	public static long incre(String key,long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	//==============================Map==================================
	/**HashMap或HashSet缓存获取具体的value
	 * @param key
	 * @param item
	 * @return
	 */
	public static Object hget(String key,String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**获取所有键值对
	 * @param key
	 * @return
	 */
	public static Map<Object,Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**hashmap放入缓存
	 * @param key
	 * @param map
	 * @return
	 */
	public  static boolean hmset(String key,Map<String,Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**hashmap放入缓存并设置时间
	 * @param key
	 * @param map
	 * @param time
	 * @return
	 */
	public static boolean hmset(String key,Map<Object,Object> map,long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**向一张hash表中放入数据，如果不存在则创建
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	public static boolean hset(String key,String item,Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**向一张hash表中放入数据并设置时间，如果不存在则创建
	 * @param key
	 * @param item
	 * @param value
	 * @param time
	 * @return
	 */
	public static boolean hset(String key,String item,Object value,long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**删除hash表中的值
	 * @param key
	 * @param item
	 */
	public static void hdel(String key,Object...item ) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**判断hash表中是否有该项的值
	 * @param key
	 * @param item
	 * @return
	 */
	public static  boolean hHasKey(String key,String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

//==========================List=================================
	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public static List<Object> lGet(String key,long start, long end){
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public static long lGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public static Object lGetIndex(String key,long index){
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public static boolean lUpdateIndex(String key, long index,Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public static long lRemove(String key,long count,Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			return 0;
		}
	}

	public static long increment(String czNumberCounter) {
		try {
			Long remove = redisTemplate.opsForValue().increment(czNumberCounter,6);
			return remove;
		} catch (Exception e) {
			return 0;
		}
	}

}
