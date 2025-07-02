package ua.com.javarush.gnew.contactm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  /**
   * Shared ObjectMapper with Hibernate support.
   */
  @Bean
  public ObjectMapper redisObjectMapper() {
    Hibernate6Module hibernateModule = new Hibernate6Module();
    hibernateModule.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
    hibernateModule.configure(
            Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);

    return new ObjectMapper().registerModule(hibernateModule);
  }

  /**
   * Generic JSON serializer using the shared ObjectMapper.
   */
  @Bean
  public RedisSerializer<Object> genericJsonSerializer(ObjectMapper redisObjectMapper) {
    return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
  }

  /**
   * A RedisTemplate that uses String keys and JSON‐serialized values.
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
          RedisConnectionFactory connectionFactory,
          RedisSerializer<Object> genericJsonSerializer
  ) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    // key serializers
    StringRedisSerializer stringSerializer = new StringRedisSerializer();
    template.setKeySerializer(stringSerializer);
    template.setHashKeySerializer(stringSerializer);

    // value serializers
    template.setValueSerializer(genericJsonSerializer);
    template.setHashValueSerializer(genericJsonSerializer);

    template.afterPropertiesSet();
    log.info("RedisTemplate<String,Object> initialized");
    return template;
  }

  /**
   * RedisCacheManager that applies a 60‐minute TTL and JSON serialization.
   */
  @Bean
  public RedisCacheManager cacheManager(
          RedisConnectionFactory connectionFactory,
          RedisSerializer<Object> genericJsonSerializer
  ) {
    RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(genericJsonSerializer)
            );

    return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(cacheConfig)
            .build();
  }
}
