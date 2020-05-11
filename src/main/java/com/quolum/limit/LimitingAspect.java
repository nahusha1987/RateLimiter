package com.quolum.limit;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// https://stackoverflow.com/questions/12130653/custom-annotation-as-interceptor-for-a-method-logging
@Component
@Aspect
public class LimitingAspect {

	private Map<Integer, RateLimiter> rateLimiterMap = new HashMap<>();

	@Pointcut(value = "execution(* *.*(..))")
	public void allMethods() {
		
	}
	
	@Around("allMethods() && @annotation(limiting)")
	public void limitRate(ProceedingJoinPoint pjp, Limiting limiting) throws Throwable {
		int requestLimit = limiting.requestLimit();
		long timeWindow = limiting.timeWindow();
		int id = getUniqueId(requestLimit, (int)timeWindow);
		RateLimiter rl = null;
		if (rateLimiterMap.containsKey(id)) {
			rl = rateLimiterMap.get(id);
		} else {
			rl = new RateLimiter(requestLimit, timeWindow);
			rateLimiterMap.put(getUniqueId(requestLimit, (int)timeWindow), rl);
		}
		Object[] signatures = pjp.getArgs();
		Request request = (Request)signatures[0];
		boolean shouldExecute = rl.limitRate(request);
		if (shouldExecute)
			pjp.proceed();
	}
	
	// Szudzik's function
	// https://stackoverflow.com/questions/53802655/how-to-write-szudziks-function-in-java
	private int getUniqueId(int a, int b) {
		return a >= b ? a * a + a + b : a + b * b; 
	}
}
