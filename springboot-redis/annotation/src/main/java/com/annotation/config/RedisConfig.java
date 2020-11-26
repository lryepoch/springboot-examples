package com.annotation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
* @description TODO 注解方式使用Redis缓存，无需 RedisConfig配置类，添加 @EnableCaching 可。
 *                  但是也可以通过 RedisConfig配置类 设置缓存过期时间，缓存管理器xxxCacheManager
 *
 * 缓存管理器与@CacheConfig、@Cachable等注解结合使用。当有两个缓存管理器，但是redis必须要有一个默认的，所以就给其中任意一个设置为默认，
 * 加上@Primary注解即可。
 *
 *
* @author lryepoch
* @date 2020/11/25 16:58
*
*/
/**
 * 自定义缓存配置类
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * 自定义cacheManager缓存管理器
     *
     * RedisCacheManager类型的 Bean，它间接实现了 Spring Cache的接口，有了它我们就可以直接使用 Spring中的缓存注解和接口了，
     * 而缓存的数据则会被自动存储到 Redis 中。
     *
     * 注意：在单机的 Redis中，系统会自动提供这个 Bean，但如果是 Redis集群，则需要我们自己来提供。
     *
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        /*-------------------------------仅使用全局默认过期时间---------------------------------*/
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(cacheConfiguration())
                .build();


        /*--------------------------------设置多个缓存失效时间-------------------------------*/
        //有个问题，序列化产生乱码？？
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        //自定义设置缓存时间
        redisCacheConfigurationMap.put("oneDay", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(86400L))
                .disableCachingNullValues());
        redisCacheConfigurationMap.put("oneMin", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60L))
                .disableCachingNullValues());

        /* 配置oneDay、oneMin的超时时间为120s、60s*/
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        RedisCacheManager cacheManager1 = RedisCacheManager.builder(redisCacheWriter)
                .cacheDefaults(cacheConfiguration())      //加载默认配置
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .transactionAware()
                .build();

        return cacheManager;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(){
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式，不推荐）
        /*Object可以设置为特定实体类，不同实体可以重新设置不同的cacheManager*/
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        //解决查询缓存转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        //配置序列化（解决乱码问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))     //key采用string类型的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))        //value采用json类型的序列化
                .entryTtl(Duration.ofSeconds(30L))    //设置全局默认过期时间为60秒
                .disableCachingNullValues();           //不缓存空值

        return config;
    }
}
