package com.labs.labrats;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DecoyActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnCheckUpdate;
    private ImageView ivUpdateIcon;
    private int clickCount = 0;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Use a generic light theme for the decoy
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_decoy);

        progressBar = findViewById(R.id.decoyProgress);
        btnCheckUpdate = findViewById(R.id.btnCheckUpdate);
        ivUpdateIcon = findViewById(R.id.ivUpdateIcon);

        btnCheckUpdate.setOnClickListener(v -> {
            btnCheckUpdate.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            
            // Fake checking for updates for 3 seconds
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                btnCheckUpdate.setEnabled(true);
                Toast.makeText(DecoyActivity.this, getString(R.string.decoy_title), Toast.LENGTH_SHORT).show();
            }, 3000);
        });

        // Secret backdoor: 5 rapid clicks on the icon opens the real dashboard
        ivUpdateIcon.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime < 500) {
                clickCount++;
            } else {
                clickCount = 1;
            }
            lastClickTime = currentTime;

            if (clickCount >= 5) {
                startActivity(new Intent(DecoyActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
