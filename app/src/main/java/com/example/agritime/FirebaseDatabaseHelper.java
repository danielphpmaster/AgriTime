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

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceEntry;
    private List<Entry> entries = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Entry> entries, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceEntry = mDatabase.getReference("entry");
    }

    public void readEntries(final DataStatus dataStatus) {
        mReferenceEntry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entries.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Entry entry = keyNode.getValue(Entry.class);
                    entries.add(entry);
                }
                dataStatus.DataIsLoaded(entries, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addEntry (Entry entry, final DataStatus dadaStatus){
        String key =mReferenceEntry.push().getKey();
        mReferenceEntry.child(key).setValue(entry)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       dadaStatus.DataIsInserted();
                   }
               });
    }
}
