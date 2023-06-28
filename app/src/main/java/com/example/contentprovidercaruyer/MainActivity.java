package com.example.contentprovidercaruyer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnTest;
    private ActivityResultLauncher<String>
            simplePermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>(){
                public void onActivityResult(Boolean res){

                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessUserDictionary();
            }
        });

    }

    private void accessUserDictionary() {
        ContentResolver resolver = getContentResolver();
        Uri uri = UserDictionary.Words.CONTENT_URI;

        String[] projection = {UserDictionary.Words.WORD};
        String sortOrder = UserDictionary.Words.WORD + " ASC";

        Cursor cursor = resolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            int wordColumnIndex = cursor.getColumnIndex(UserDictionary.Words.WORD);
            do {
                String word = cursor.getString(wordColumnIndex);
                Log.d("Dictionary", "Word: " + word);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}