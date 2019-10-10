package com.streaming.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class SubscriptionUtil {
    private static String SUBSCRIPTION_URL = "https://ma‐ooma.timwe.com/neo‐billing‐plugins‐ooma‐" +
            "facade/docharge?PartnerRoleId=@PartnerRoleId@&Password=@Password@&ProductId=@ProductId@" +
            "&PricePointId=@PricePointId@&Destination=@Msisdn@&OpId=253&ExtTxId=@ExtId@&VariableChargingAmount=@Price@&Content_Data_Json=@Data@";
    public static String subscribe(String msisdn) throws IOException {
        String url = SUBSCRIPTION_URL;
        url = url.replace("@Msisdn", msisdn);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }

        return null;
    }
}
