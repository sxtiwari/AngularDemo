package com.se.common.util;

import com.se.common.model.AsyncContext;

public interface AsyncHandler<T> {
	void onComplete(T response, AsyncContext context);
	void onError(Throwable t, AsyncContext context);
}
