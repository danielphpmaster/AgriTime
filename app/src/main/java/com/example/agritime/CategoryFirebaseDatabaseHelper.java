package com.example.agritime;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCategory;
    private List<Category> categories = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Category> categories, List<String> keys);

        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public CategoryFirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceCategory = mDatabase.getReference("category");
    }

    public void readCategories(final DataStatus dataStatus) {
        mReferenceCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Category category = keyNode.getValue(Category.class);
                    categories.add(category);
                }
                dataStatus.DataIsLoaded(categories, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addCategory(Category category, final DataStatus dataStatus) {
        String key = mReferenceCategory.push().getKey();
        mReferenceCategory.child(key).setValue(category)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }
}
