package com.qlct.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Singleton
@Slf4j
public class FirebaseInitialize {

    private static final String FIREBASE_ADMIN_SDK = "firebase/qlct-app-firebase-key.json";

    public void initialize() {

        log.info("Start connection firebase");
        try (InputStream serviceAccount = Thread.currentThread().getContextClassLoader().getResourceAsStream(FIREBASE_ADMIN_SDK)) {
            assert serviceAccount != null;
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://qlct-app-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            Firestore db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            log.error("Fail to connection firebase");
            e.printStackTrace();
        }
    }


}
