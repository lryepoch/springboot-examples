package com.template.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lryepoch
 * @date 2020/9/5 11:25
 * @description TODO
 */
@Configuration
@EnableCaching     //开启缓存
public class RedisConfig extends CachingConfigurerSupport {

//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
//
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//
//        //配置连接工厂
//        template.setConnectionFactory(factory);
//
//        //使用Jackson2JsonRedisSerialize替换默认序列化（默认使用JDK序列方式，不推荐）
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        template.setDefaultSerializer(serializer);
//
//        return template;
//    }


    /**
     * @description RedisTemplate相关配置
     * @author lryepoch
     * @date 2020/9/5 11:55
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //配置连接工厂
        redisTemplate.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式，不推荐）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域(范围)，field,get和set,以及修饰符范围，ANY指的是包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的。final修饰的类，比如String,Integer等会抛出异常
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //对value序列化：值采用以上配置的json序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //对key序列化：使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //对hash key和value序列化：同上
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
    * @description StringRedisTemplate相关配置，如果操作字符串的话，建议用 StringRedisTemplate。底层默认使用String序列化器
    * @author lryepoch
    * @date 2020/11/25 17:31
    *
    */
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /*-------------------------以下的bean可以直接注入到类中，然后更加简单的调用redis的api【可无】--------------------*/
   /**
     * @description 对hash类型的数据操作
     * @author lryepoch
     * @date 2020/9/5 11:46
    * @return
     */
    @Bean
    public HashOperations<Object, Object, Object> hashOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * @description 对redis字符串类型数据操作
     * @author lryepoch
     * @date 2020/9/5 13:34
     * @return
     */
    @Bean
    public ValueOperations<Object, Object> valueOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * @description 对链表类型的数据操作
     * @author lryepoch
     * @date 2020/9/5 13:36
     * @return
     */
    @Bean
    public ListOperations<Object, Object> listOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * @description 对无序集合类型的数据操作
     * @author lryepoch
     * @date 2020/9/5 13:37
     * @return
     */
    @Bean
    public SetOperations<Object, Object> setOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * @description 对有序集合类型的数据操作
     * @author lryepoch
     * @date 2020/9/5 13:38
     * @return
     */
    @Bean
    public ZSetOperations<Object, Object> zSetOperations(RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}
