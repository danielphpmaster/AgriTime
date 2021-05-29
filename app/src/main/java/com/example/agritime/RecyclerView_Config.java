package com.example.agritime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private EntryAdapter mEntryAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Entry> entries, List<String> keys) {
        mContext = context;
        mEntryAdapter = new EntryAdapter(entries, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mEntryAdapter);
    }

    class EntryItemView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mCategory;
        private TextView mTime;

        private String key;

        public EntryItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.entry_list_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.tvName);
            mCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            mTime = (TextView) itemView.findViewById(R.id.tvTime);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(mContext, EntryDetailsActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("name",mName.getText().toString());
                    intent.putExtra("category",mCategory.getText().toString());
                    intent.putExtra("time",mTime.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Entry entry, String key) {
            mName.setText(entry.getName());
            mCategory.setText((entry.getCategory()));
            mTime.setText(entry.getTime());
            this.key = key;
        }
    }

    class EntryAdapter extends RecyclerView.Adapter<EntryItemView>{
        private List<Entry> mEntryList;
        private List<String> mKeys;

        public EntryAdapter(List<Entry> mEntryList, List<String> mKeys) {
            this.mEntryList = mEntryList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public EntryItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EntryItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryItemView holder, int position) {
            holder.bind(mEntryList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mEntryList.size();
        }
    }
}
