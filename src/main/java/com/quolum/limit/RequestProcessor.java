package com.quolum.limit;

import org.springframework.stereotype.Component;

@Component
public class RequestProcessor {
	
	@Limiting(requestLimit = 5, timeWindow = 60000)
	public void testLimit1(Request request) {
		System.out.println("Processing requestID " + request.getRequestId());
	}
	
	@Limiting(requestLimit = 10, timeWindow = 10000)
	public void testLimit2(Request request) {
		System.out.println("Processing requestID " + request.getRequestId());
	}
}
