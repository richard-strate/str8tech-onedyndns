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
package com.str8tech.onedyndns.client.jdk;

import com.str8tech.onedyndns.client.InvalidCredentialsException;
import com.str8tech.onedyndns.client.OneClientSession;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard
 */
public class OneClientSessionImpl implements OneClientSession {

  private static final Logger LOG = LoggerFactory.getLogger(OneClientSessionImpl.class);

  class Session {

    public Session(HttpClient client, String userName, OneClientImpl oneClient) {
      this.client = client;
      this.userName = userName;
      this.oneClient = oneClient;
    }

    final HttpClient client;
    final String userName;
    final OneClientImpl oneClient;
  }
  private Session session;

  protected HttpClient getHttpClient() {
    if (session == null) {
      throw new IllegalStateException("No valid HTTP session exists for client, logged out?");
    }
    return session.client;
  }

  public OneClientImpl login(String userName, String password) throws IOException, InvalidCredentialsException {
    LOG.trace("Attempt to log in as '{}' {}", userName, password != null ? "using a password" : "without password");
    if (session != null) {
      LOG.warn(String.format("Ignored login attempt on already valid session for user '%s', create a new session", session.userName));
      return session.oneClient;
    }
    CookieManager cm = new CookieManager();
    cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    HttpClient client = HttpClient
            .newBuilder()
            .cookieHandler(cm)
            .followRedirects(Redirect.ALWAYS)
            .build();
    String form = Map.of(
            "loginDomain", "true",
            "username", userName,
            "displayUsername", userName,
            "targetDomain", "",
            "loginTarget", "",
            "password1", password
    )
            .entrySet()
            .stream()
            .map(entry -> Stream.of(
            URLEncoder.encode(entry.getKey(), UTF_8),
            URLEncoder.encode(entry.getValue(), UTF_8))
            .collect(Collectors.joining("="))
            ).collect(Collectors.joining("&"));
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://www.one.com/admin/login.do"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(form))
                    .build();
    HttpResponse<String> response;
    try {
      response = client.send(request, BodyHandlers.ofString());
    } catch (InterruptedException ex) {
      throw new IOException("Login request interrupted", ex);
    }
    if (response.statusCode() != 200) {
      throw new IOException(String.format("Non-success response to login of user '%s': %d", userName, response.statusCode()));
    }
    if (!response.body().contains("<meta name=\"one.com:active-user\" content=\"dnsmanager@strate.se\" />")) {
      throw new InvalidCredentialsException(String.format("Invalid credentials for user '%s'", userName));
    }
    this.session = new Session(client, userName, new OneClientImpl(this));
    LOG.trace("Session established for '{}'", userName);
    return session.oneClient;
  }

  void logout() throws IOException {
    if (session == null) {
      LOG.trace("Client not logged in, close() ignored");
      return;
    }
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://www.one.com/logout.do"))
                    .GET()
                    .build();
    HttpResponse<String> response;
    try {
      response = session.client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new IOException(String.format("Non-success response to logout of user '%s': %d", session.userName, response.statusCode()));
      }
    } catch (InterruptedException ex) {
      throw new IOException("Login request interrupted", ex);
    } finally {
      session = null;
    }
    LOG.trace("Session terminated");
  }

}
