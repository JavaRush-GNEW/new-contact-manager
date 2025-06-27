package ua.com.javarush.gnew.contactm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import java.time.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Getter
@Configuration
@Slf4j
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> tpl = new RedisTemplate<>();
    tpl.setConnectionFactory(connectionFactory);

    // key serializer
    tpl.setKeySerializer(new StringRedisSerializer());
    tpl.setHashKeySerializer(new StringRedisSerializer());

    // JSON value serializer with Hibernate support
    ObjectMapper objectMapper = new ObjectMapper();
    Hibernate6Module hibernate6Module = new Hibernate6Module();
    hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
    hibernate6Module.configure(
        Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    objectMapper.registerModule(hibernate6Module);

    GenericJackson2JsonRedisSerializer jsonSer =
        new GenericJackson2JsonRedisSerializer(objectMapper);
    tpl.setValueSerializer(jsonSer);
    tpl.setHashValueSerializer(jsonSer);

    tpl.afterPropertiesSet();
    log.info("Redis template initialized");
    return tpl;
  }

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory cf) {
    ObjectMapper objectMapper = new ObjectMapper();
    Hibernate6Module hibernate6Module = new Hibernate6Module();
    hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
    hibernate6Module.configure(
        Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    objectMapper.registerModule(hibernate6Module);

    RedisCacheConfiguration defaultCfg =
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer(objectMapper)));

    return RedisCacheManager.builder(cf).cacheDefaults(defaultCfg).build();
  }
}
