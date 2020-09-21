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
package com.str8tech.onedyndns;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard
 */
public abstract class AppBase {

  private static final Logger LOG = LoggerFactory.getLogger(AppBase.class);

  private DyndnsUpdateRequest request;
  private String externalIp;

  public DyndnsUpdateRequest getRequest() {
    return request;
  }

  public String getExternalIp() throws IOException {
    if (externalIp == null) {
      String ipResolverClassName = request.getIpResolver();
      LOG.debug("Creating a new instance of the defined IpResolver: {}", ipResolverClassName);
      Class<?> ipResolverClass;
      try {
        ipResolverClass = Class.forName(ipResolverClassName);
      } catch (ClassNotFoundException ex) {
        throw new IOException(String.format("Configured IpResolver class '%s' not found", ipResolverClassName), ex);
      }
      Object ipResolverInstance;
      try {
        ipResolverInstance = ipResolverClass.getDeclaredConstructor().newInstance();
      } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
        throw new IOException(String.format("Failed to create instance of IpResolver class '%s'", ipResolverClassName), ex);
      }
      if (!(ipResolverInstance instanceof IpResolver)) {
        throw new IOException(String.format("Configured IpResolver class '%s' is not an instanceof the correct interface", ipResolverClassName));
      }
      this.externalIp = ((IpResolver) ipResolverInstance).resolve();
      LOG.info("External IP address resolved: {}", externalIp);
    }
    return externalIp;
  }

  public String processContent(String content) throws IOException {
    if (content.contains("${external-ip}")) {
      content = content.replace("${external-ip}", getExternalIp());
    }
    return content;
  }

  public void loadRequest(String fileName) throws Exception {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new IllegalArgumentException(String.format("Configuration file not found: %s", fileName));
    }
    ObjectMapper objectMapper = new ObjectMapper();
    this.request = objectMapper.readValue(file, DyndnsUpdateRequest.class);
  }

  public abstract void execute(String[] args) throws Exception;

}
