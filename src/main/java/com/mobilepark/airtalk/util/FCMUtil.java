package com.mobilepark.airtalk.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;

import org.springframework.core.io.ClassPathResource;



public class FCMUtil {
    public static String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/dfgh1400fcmtest-firebase-adminsdk-om128-1f32352e67.json";
        List<String> list = new ArrayList<>();
        list.add("https://www.googleapis.com/auth/cloud-platform");
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()).createScoped(list);

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
