package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.model.User;
import com.qlct.service.IfUserService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Singleton
@Slf4j
public class UserService implements IfUserService {

    @Override
    public String saveUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApi = db.collection("user").document(user.getName()).set(user);
        return collectionApi.get().getUpdateTime().toString();
    }

    @Override
    public String deleteUser(String name) {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection("user").document(name).delete();
        return "Document ID" + name +"delete";
    }

    @Override
    public User getUser(String name) throws ExecutionException, InterruptedException {
        log.info("BEGIN: UserService.getUser");
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection("user").document(name);
        CollectionReference collectionReference = db.collection("user");
        Query chainedQuery1 = collectionReference.whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> future1 = chainedQuery1.get();

        for (DocumentSnapshot document : future1.get().getDocuments()) {
            log.info(document.getId());
            User user1 = document.toObject(User.class);
            log.info(user1.getName());
            log.info(String.valueOf(user1.getAge()));
        }

        QuerySnapshot querySnapshot = future1.get();

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user = null;
        if (document.exists()) {
            log.info("exist");
            user = document.toObject(User.class);
        }
        log.info("END: UserService.getUser");
        return user;
    }

    @Override
    public User getUsers(String name) {
        return null;
    }
}
