package com.streaming.aggregator.timwe.subscription;

import com.streaming.aggregator.timwe.bean.*;
import com.streaming.utils.request.Request;
import com.streaming.utils.request.RequestUtils;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by HARIKRISHNA on 1/20/2021.
 */


public class SubscriptionUtils {
    public static String getAuthenticationHeader(String serviceId, String privateKey)
    {
            String presharedKey = privateKey;
            String phrasetoEncrypt = serviceId + "#" + System.currentTimeMillis(); // 1234 is the Service Id shared by TIMWE
            String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
            String encrypted = "";
            try {
                Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
                SecretKeySpec key = new SecretKeySpec(presharedKey.getBytes(), "AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                final byte[] crypted = cipher.doFinal(phrasetoEncrypt.getBytes());
                encrypted = Base64.getEncoder().encodeToString(crypted);
                System.out.println("ENCRYPTED API KEY--" + encrypted);
                System.out.println("encrypt END \n");
            } catch (Exception e) {
                System.out.println(e);
            }
            return encrypted;
    }

    public static UnSubscribeResponse unSubscribeRequest(String apiKey, String serviceId, String privateKey, UnSubscribeRequest unSubscribeRequest, String url) {
        Request request = new Request(url, "POST", new Gson().toJson(unSubscribeRequest));
        String authentication = SubscriptionUtils.getAuthenticationHeader(serviceId, privateKey);

        HashMap<String, String> headers = new HashMap<>();

        headers.put("apikey", apiKey);
        headers.put("authentication", authentication);
        headers.put("external-tx-id", "" + System.currentTimeMillis());

        String responseMessage = RequestUtils.getResponse(request, null, headers);

        System.out.println(responseMessage);

        UnSubscribeResponse response = new Gson().fromJson(responseMessage, UnSubscribeResponse.class);

        return response;
    }

    public static MtResponse sendMtMessageRequest(String apiKey, String serviceId, String privateKey,MtRequest mtMessageRequest, String url) {
        Request request = new Request(url, "POST", new Gson().toJson(mtMessageRequest));

        System.out.println("MT Request Body:" + new Gson().toJson(mtMessageRequest));

        String authentication = SubscriptionUtils.getAuthenticationHeader(serviceId, privateKey);

        HashMap<String, String> headers = new HashMap<>();

        headers.put("apikey", apiKey);

        headers.put("authentication", authentication);

        headers.put("external-tx-id", "" + System.currentTimeMillis());

        String responseMessage = RequestUtils.getResponse(request, null, headers);

        System.out.println(responseMessage);

        MtResponse response = new Gson().fromJson(responseMessage, MtResponse.class);

        return response;
    }
}
