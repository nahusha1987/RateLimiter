package com.quolum.limit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RequestProcessorDriver {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AopConfig.class);
		ctx.register(AopConfig.class);
		//ctx.refresh();
		RequestProcessor rp = ctx.getBean(RequestProcessor.class);

		List<Request> requestList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Request req = new Request(i, System.currentTimeMillis());
			requestList.add(req);
		}
		requestList.forEach(req -> rp.testLimit1(req));
		ctx.close();
	}
}