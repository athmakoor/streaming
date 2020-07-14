package com.streaming.utils.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public final class RequestUtils {
    private static final Integer FORBIDDEN = 403;

    private RequestUtils() {

    }

    public static String getResponse(final Request request, final String cookieString) {
        BufferedReader br = null;
        OutputStream os = null;
        String url = request.getPath();

        StringBuilder sb = new StringBuilder();

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) (new URL(url).openConnection());
            connection.setRequestMethod(request.getMethod());
            connection.setRequestProperty("Connection", "Keep-Alive");
            if (cookieString != null) {
                connection.addRequestProperty("Cookie", cookieString);
            }

            if (request.getData() != null) {
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                os = connection.getOutputStream();
                os.write(request.getData().getBytes("UTF-8"));
            } else {
                connection.connect();
            }

            if (connection.getResponseCode() == HttpStatus.OK.value()) {
                br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output;

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
            } else if (connection.getResponseCode() == FORBIDDEN) {
                br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
                String output;

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }

                throw new RequestException("403 Exception " + br.toString());
            } else if (connection.getResponseCode() == HttpStatus.BAD_REQUEST.value()) {
                br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
                String output;

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }

                throw new RequestException("400 Exception " + br.toString());
            }

        } catch (IOException e) {
            throw new RequestException("error connection to Server " + request.getPath());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

                if (connection != null) {
                    connection.disconnect();
                }

                if (os != null) {
                    os.flush();
                    os.close();
                }

            } catch (IOException e) {
                throw new RequestException("close connections error");
            }
        }

        return sb.toString();
    }
}
