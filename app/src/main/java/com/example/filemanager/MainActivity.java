package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import io.reactivex.Single;

public class MainActivity extends AppCompatActivity {

    public static TextView textView;
    private Button preBtn;
    RecyclerView recyclerView;
    FilesRecyclerListAdapter mAdapter;
    static File file = new File("/storage/emulated/0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.path_text_view_id);
        preBtn = findViewById(R.id.back_btn_id);


        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!file.getPath().equals("/storage/emulated/0")){
                    file = new File(Objects.requireNonNull(file.getParent()));
                    mAdapter.setData(file.listFiles());
                    textView.setText(MainActivity.file.getPath());
                }
            }
        });

        reqPermission();
        
    }

    @Override
    protected void onResume() {
        super.onResume();


        textView.setText(file.getPath());

        recyclerView = findViewById(R.id.files_recycler_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FilesRecyclerListAdapter(file.listFiles());
        recyclerView.setAdapter(mAdapter);



//        Toast.makeText(this, Arrays.toString(file.list()), Toast.LENGTH_SHORT).show();

    }

    private void reqPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PromptForPermissionsDialog("We should have access to files!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                100);
                    }
                });
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }

//        Toast.makeText(this, Arrays.toString(file.listFiles()), Toast.LENGTH_LONG).show();
    }

    private void PromptForPermissionsDialog(String message, DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(message)
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }


}
