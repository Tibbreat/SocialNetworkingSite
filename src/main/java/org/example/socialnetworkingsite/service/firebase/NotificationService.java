//package org.example.socialnetworkingsite.service.firebase;
//
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.messaging.*;
//import org.springframework.stereotype.Service;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//
//
//@Service
//public class NotificationService {
//
//    private final FirebaseMessaging firebaseMessaging;
//
//    public NotificationService(FirebaseApp firebaseApp) {
//        this.firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
//    }
//
//    public String sendNotification(String registrationToken, String title, String body) throws FirebaseMessagingException {
//        Message message = Message.builder()
//                .setToken(registrationToken)
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .build();
//
//        String responseMess = firebaseMessaging.send(message);
//        return responseMess;
//    }
//}