package com.qlct.core.util;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.model.Sequence;

import javax.inject.Singleton;
import java.util.concurrent.ExecutionException;

@Singleton
public class Generate {

    public static String generateCode(String prefix) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection("sequence");
        Query query = collectionReference.whereEqualTo("prefix", prefix);
        ApiFuture<QuerySnapshot> apiFuture = query.get();
        int orderNumber = 0;
        String sequenceId = "";
        for (DocumentSnapshot document : apiFuture.get().getDocuments()) {
            Sequence sequenceEntity = document.toObject(Sequence.class);
            sequenceId = document.getId();
            assert sequenceEntity != null;
            orderNumber = sequenceEntity.getOrder();
        }
        orderNumber++;
        StringBuilder orderNumberStr = new StringBuilder(String.valueOf(orderNumber));
        while (orderNumberStr.length() < 6) {
            orderNumberStr.insert(0, "0");
        }

        String code = prefix + orderNumberStr;

        //update collection vs prefix vs orderNumber moi
        DocumentReference docRef = db.collection("sequence").document(sequenceId);
        ApiFuture<WriteResult> future = docRef.update("order", orderNumber);
        future.get();
        return code;
    }

}
