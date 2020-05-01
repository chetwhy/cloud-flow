package cn.yccoding.mail.service.impl;

import cn.yccoding.mail.model.RedisInfo;
import cn.yccoding.mail.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisConnection execute() {
        return redisTemplate.execute((RedisCallback<RedisConnection>)connection -> connection);
    }

    @Override
    public List<RedisInfo> getRedisInfo() {
        try {
            List<RedisInfo> redisInfoList = new ArrayList<>();
            Properties info = execute().info();
            for (String name : info.stringPropertyNames()) {
                RedisInfo RedisInfo = new RedisInfo();
                RedisInfo.setKey(name);
                RedisInfo.setValue(info.getProperty(name));
                redisInfoList.add(RedisInfo);
            }
            return redisInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Map<String, Object> getData(String name, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", LocalDateTime.now());
        map.put(name, data);
        return map;
    }

    @Override
    public Map<String, Object> getRedisMemory() {
        return getData("memory", execute().info("memory").get("used_memory"));
    }

    @Override
    public Map<String, Object> getRedisDbSize() {
        return getData("dbsize", execute().dbSize());
    }

    @Override
    public Set<String> getKeys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @Override
    public String get(String key) {
        byte[] bytes = execute().get(key.getBytes());
        if (bytes != null) {
            return new String(bytes, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    @Override
    public Boolean set(String key, String value) {
        return execute().set(key.getBytes(), value.getBytes());
    }

    @Override
    public Boolean set(String key, String value, Long timeout) {
        return execute().set(key.getBytes(), value.getBytes(), Expiration.milliseconds(timeout),
            RedisStringCommands.SetOption.UPSERT);
    }

    @Override
    public Long del(String... keys) {
        long result = 0;
        for (String key : keys) {
            result += execute().del(key.getBytes());
        }
        return result;
    }

    @Override
    public Long exists(String... keys) {
        long result = 0;
        for (String key : keys) {
            if (execute().exists(key.getBytes())) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Long pttl(String key) {
        return execute().pTtl(key.getBytes());
    }

    @Override
    public Long pexpire(String key, Long time) {
        if (execute().pExpire(key.getBytes(), time)) {
            return 1L;
        }
        return 0L;
    }
}
