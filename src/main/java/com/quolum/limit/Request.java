package com.quolum.limit;

public class Request implements Comparable<Request> {
	public Request(long requestId, long timeStamp) {
		super();
		this.requestId = requestId;
		this.timeStamp = timeStamp;
	}

	private long requestId;
	private long timeStamp;
	
	@Override
	public int compareTo(Request r) {
		if (this.timeStamp == r.timeStamp)
			return 0;
		else if (this.timeStamp < r.timeStamp)
			return -1;
		return 1;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
