package com.se.common.model;

import javax.ws.rs.container.AsyncResponse;

public class AsyncContext {

  private AsyncResponse asyncResponse;

  public AsyncContext(AsyncResponse asyncResponse) {
    this.asyncResponse = asyncResponse;
  }

  public AsyncResponse getAsyncResponse() {
    return this.asyncResponse;
  }
}
