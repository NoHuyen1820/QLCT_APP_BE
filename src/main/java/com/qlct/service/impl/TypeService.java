package com.qlct.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.qlct.core.dto.TypeDTO;
import com.qlct.core.mapper.TypeMapper;
import com.qlct.model.Type;
import com.qlct.service.IfTypeService;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class TypeService implements IfTypeService {
    @Inject
     private TypeMapper typeMapper;
    @Override
    public String createType(TypeDTO type) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Type typeEntity = typeMapper.toEntity(type);
        typeEntity.setUpdatedAt(new Date());
        ApiFuture<DocumentReference> docRef = db.collection("type").add(typeEntity);
        return docRef.get().getId();
    }
}
