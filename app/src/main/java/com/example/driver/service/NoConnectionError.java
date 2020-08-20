package com.example.driver.service;

import com.android.volley.NetworkError;

public class NoConnectionError extends NetworkError {
  @SuppressWarnings("serial")
  public NoConnectionError() {
    super();
  }

  public NoConnectionError(Throwable reason) {
    super(reason);
  }}