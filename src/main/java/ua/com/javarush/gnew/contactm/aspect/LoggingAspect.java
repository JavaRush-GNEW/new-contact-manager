package ua.com.javarush.gnew.contactm.aspect;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@EnableAspectJAutoProxy
@Slf4j
public class LoggingAspect {

  private final String apiPointcut = "execution(* ua.com.javarush.gnew.contactm.controller..*(..))";

  private final String exceptionPointcut =
      "execution(* ua.com.javarush.gnew.contactm.services.*.*(..))";

  @Pointcut(apiPointcut)
  public void logController() {}

  @AfterThrowing(value = exceptionPointcut, throwing = "exception")
  public void loggingErrorAdvice(JoinPoint joinPoint, Throwable exception) {
    log.error("Failed to execute method: {}", joinPoint.getSignature().toShortString());
    log.error("Error message: {}", exception.getMessage());
  }

  @Around("logController()")
  public Object loggingEndpointAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String methodName = joinPoint.getSignature().toShortString();
    Object[] args = joinPoint.getArgs();
    HttpServletRequest request = getRequest();
    if (request != null) {
      String method = request.getMethod();
      String pattern = request.getRequestURI();
      String username =
          request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";
      String token = request.getHeader("Authorization");
      log.info("{} {}", method, pattern);
      if (token != null && token.startsWith("Bearer")) {
        log.debug("Authorization: {}", token);
        log.info("User {} authorized with token", username);
      } else {
        log.info("User: [{}]", username);
      }
    } else {
      log.warn("RequestContextHolder.getRequestAttributes() is null");
    }
    log.debug("{} with params {}", methodName, Arrays.toString(args));
    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;
    log.info("{} responded within {} ms", methodName, duration);
    return result;
  }

  private HttpServletRequest getRequest() {
    ServletRequestAttributes attrs =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return attrs != null ? attrs.getRequest() : null;
  }
}
