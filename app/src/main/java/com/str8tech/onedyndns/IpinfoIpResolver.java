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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author Richard
 */
public class IpinfoIpResolver implements IpResolver {

  @Override
  public String resolve() throws IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create("https://ipecho.net/plain")).GET().build();
    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (InterruptedException ex) {
      throw new IOException("Login request interrupted", ex);
    }
    if (response.statusCode() != 200) {
      throw new IOException(String.format("Error checking external IP with ipecho.net, got response %d", response.statusCode()));
    }
    return response.body();
  }

}
