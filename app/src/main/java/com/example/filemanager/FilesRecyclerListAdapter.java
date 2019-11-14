package com.example.filemanager;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FilesRecyclerListAdapter extends RecyclerView.Adapter<FilesRecyclerListAdapter.FileViewHolder> {

    private File[] dataset;

    public FilesRecyclerListAdapter(File[] dataset) {
        this.dataset = dataset;
    }

    public void setData(File[] newData) {
        this.dataset = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilesRecyclerListAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list_item,
                parent,
                false);

        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, final int position) {
        holder.itemNameTextView.setText(dataset[position].getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dataset[position].isFile()){
                    MainActivity.file = new File(dataset[position].getPath());
                    setData(MainActivity.file.listFiles());
                    MainActivity.textView.setText(MainActivity.file.getPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name_list_id);
        }
    }
}
