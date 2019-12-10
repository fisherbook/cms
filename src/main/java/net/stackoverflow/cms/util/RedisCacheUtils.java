package net.stackoverflow.cms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 *
 * @author 凉衫薄
 */
@Slf4j
public class RedisCacheUtils {

    private static RedisTemplate redisTemplate;

    public static RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(RedisTemplate template) {
        redisTemplate = template;
    }

    private static void checkRedisTemplate() {
        Assert.notNull(redisTemplate, "redisTemplate为空");
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public static boolean expire(String key, long time) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || time < 0) {
            return false;
        }

        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    public static long getExpire(String key) {

        checkRedisTemplate();

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 是否包含key
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static void del(String... key) {

        checkRedisTemplate();

        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        } else {
            return redisTemplate.opsForValue().get(key);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, Object value) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置缓存以及过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static boolean set(String key, Object value, long time) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || time < 0) {
            return false;
        }

        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取hash表项
     *
     * @param key
     * @param item
     * @return
     */
    public static Object hGet(String key, String item) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        } else {
            return redisTemplate.opsForHash().get(key, item);
        }
    }

    /**
     * 获取hash表
     *
     * @param key
     * @return
     */
    public static Map<Object, Object> hmGet(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        } else {
            return redisTemplate.opsForHash().entries(key);
        }
    }

    /**
     * 设置hash表
     *
     * @param key
     * @param map
     * @return
     */
    public static boolean hmSet(String key, Map<String, Object> map) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置hash表及过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public static boolean hmSet(String key, Map<String, Object> map, long time) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || time < 0) {
            return false;
        }

        try {
            redisTemplate.opsForHash().putAll(key, map);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置hash表项
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public static boolean hSet(String key, String item, Object value) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || item == null || "".equals(item)) {
            return false;
        }

        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置hash表项及过期时间
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public static boolean hSet(String key, String item, Object value, long time) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || item == null || "".equals(item) || time < 0) {
            return false;
        }

        try {
            redisTemplate.opsForHash().put(key, item, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表项
     *
     * @param key
     * @param items
     */
    public static void hDel(String key, Object... items) {

        checkRedisTemplate();

        if (key != null && !"".equals(key)) {
            redisTemplate.opsForHash().delete(key, items);
        }
    }

    /**
     * 判断hash表是否存在某项
     *
     * @param key
     * @param item
     * @return
     */
    public static boolean hHasKey(String key, String item) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取set中的所有值
     *
     * @param key
     * @return
     */
    public static Set<Object> sGet(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        }

        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 判断set是否存在某值
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean sHasValue(String key, Object value) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || value == null) {
            return false;
        }

        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置set缓存
     *
     * @param key
     * @param values
     * @return
     */
    public static long sSet(String key, Object... values) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return 0;
        }

        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 设置set缓存及过期时间
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    public static long sSet(String key, long time, Object... values) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || time < 0) {
            return 0;
        }

        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            expire(key, time);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set大小
     *
     * @param key
     * @return
     */
    public static long sGetSize(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return 0;
        }

        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 删除set里面的值
     *
     * @param key
     * @param values
     * @return
     */
    public static long sDel(String key, Object... values) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return 0;
        }

        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 获取list缓存
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        }

        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取list长度
     *
     * @param key
     * @return
     */
    public static long lGetSize(String key) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return 0;
        }

        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 获取list下标处的值
     *
     * @param key
     * @param index
     * @return
     */
    public static Object lGetIndex(String key, long index) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return null;
        }

        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 设置list缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean lSet(String key, Object value) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置list缓存以及过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {

        checkRedisTemplate();

        if (key == null || "".equals(key) || time < 0) {
            return false;
        }

        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置list缓存下标处的值
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public static boolean lsetIndex(String key, long index, Object value) {

        checkRedisTemplate();

        if (key == null || "".equals(key)) {
            return false;
        }

        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
