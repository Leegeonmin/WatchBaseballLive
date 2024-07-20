package com.bongbong.watchbaseball.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

  public static String sendGetRequest(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(connection.getInputStream()))) {
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = br.readLine()) != null) {
        response.append(inputLine);
      }
      return response.toString();
    }
  }

}
