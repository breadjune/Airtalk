package com.mobilepark.airtalk.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mobilepark.airtalk.data.FcmMessage;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@RequiredArgsConstructor
public class FCMService {
    private final ObjectMapper objectMapper;
    private final String FCM_SERVER_KEY = "AAAAw39ksXI:APA91bEu0ZodPZ6pMU5nwy9AGu44nKR9m7fDEDufmMeZWDNOi-FZAJ1zT849QL0wWOmNl7LtajvgnWTVesNqx10qxV5k8TS8BETzG1LwKQXeClcinx-qlSx2YCvxR4nK_o1pmGouEkXl";
    private final String API_URL = "https://fcm.googleapis.com/fcm/send";

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                            .url(API_URL)
                            .post(requestBody)
                            .addHeader(HttpHeaders.AUTHORIZATION, "key="+FCM_SERVER_KEY)
                            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                            .build();

        Response response = client.newCall(request).execute();
        System.out.println("response : " + response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
        .message(FcmMessage.Message.builder()
            .token(targetToken)
            .notification(FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build()
            )
            .build()
        )
        .validate_only(false)
        .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

}
