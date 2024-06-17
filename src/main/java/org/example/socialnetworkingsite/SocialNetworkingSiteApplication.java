package org.example.socialnetworkingsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class SocialNetworkingSiteApplication {

    public static void main(String[] args) throws IOException {
//        FileInputStream serviceAccount =
//                new FileInputStream("src/main/resources/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);


        SpringApplication.run(SocialNetworkingSiteApplication.class, args);
    }

}
