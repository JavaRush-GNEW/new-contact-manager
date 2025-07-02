package ua.com.javarush.gnew.contactm.component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisHealthCheck {

  private final RedisTemplate<String, Object> redisTemplate;

  @PostConstruct
  public void checkRedisConnection() {
    try {
      String pong = redisTemplate.getConnectionFactory().getConnection().ping();
      log.info("Redis connection successful. Response: {}", pong);
    } catch (Exception e) {
      log.warn("Redis connection failed: {}", e.getMessage());
      log.warn("Application will continue without Redis caching");
    }
  }
}
