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
public interface OneClientFactory {

  /**
   * Crate a OneClient instance, ready to used with the supplied session.
   * Depending on the implementation this session could be anything from an
   * HTTP/REST based auth token to "nothing".
   *
   * @param session
   * @return
   */
  OneClient create(OneClientSession session);

}
