/*
 * Copyright (C) 2020 Straight Technologies
 * All rights reserved.
 * http://www.str8tech.com
 * 
 * This is a legal notice from Straight Technologies,
 * registered in Sweden.
 * 
 * This document is proprietary, confidential and is legally privileged.
 * Any un-authorised access to it is prohibited. Its disclosure, copying,
 * distribution or any action taken or omitted to be taken in reliance on
 * it is prohibited and may be unlawful.
 */
package com.str8tech.onedyndns.client;

/**
 *
 * @author Richard
 */
public class RecordAlreadyExistsException extends Exception {

  public RecordAlreadyExistsException() {
  }

  public RecordAlreadyExistsException(String message) {
    super(message);
  }

  public RecordAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecordAlreadyExistsException(Throwable cause) {
    super(cause);
  }

  public RecordAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
