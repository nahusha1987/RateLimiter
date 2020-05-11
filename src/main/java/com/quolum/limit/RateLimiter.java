package com.quolum.limit;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class RateLimiter {
	
	private PriorityQueue<Request> pq;
	private long timeWindow;
	private int requestLimit;
	
	public RateLimiter(int n, long t) {
		requestLimit = n;
		pq = new PriorityQueue<>();
		timeWindow = t;
	}
	
	public Boolean limitRate(Request request) {
		Boolean shouldProcess = false;
		if (pq.size() < requestLimit) {
			pq.offer(request);
			System.out.println("Request " + request.getRequestId() + " processed");
			shouldProcess = true;
		} else {
			Request req = pq.peek();
			long currentTime = System.currentTimeMillis();
			if (currentTime - req.getTimeStamp() > timeWindow) {
				Request r = pq.poll();
				System.out.println("Request " + r.getRequestId() + " has been removed");
				pq.offer(request);
				System.out.println("Request " + request.getRequestId() + " has been processed");
				shouldProcess = true;
			} else {
				System.out.println("Request " + request.getRequestId() + " has been dropped");
				shouldProcess = false;
			}
		}
		return shouldProcess;
	}
	
	public static void main(String[] args) throws InterruptedException {
		RateLimiter limiter = new RateLimiter(10, 5000);
		List<Request> requestList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Request req = new Request(i, System.currentTimeMillis());
			requestList.add(req);
		}
		requestList.forEach(req -> limiter.limitRate(req));
		Thread.sleep(5000);
		limiter.limitRate(new Request(1, System.currentTimeMillis()));
	}
}
