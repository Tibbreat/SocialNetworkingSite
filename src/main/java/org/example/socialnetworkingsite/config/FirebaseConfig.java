//package org.example.socialnetworkingsite.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//    @Value("${firebase.credential-path}")
//    private String firebaseCredentialPath;
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        FileInputStream serviceAccount =
//                new FileInputStream(firebaseCredentialPath);
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//        return FirebaseApp.getInstance();
//    }
//}
