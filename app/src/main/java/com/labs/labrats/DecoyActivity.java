package com.labs.labrats;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class DecoyActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnCheckUpdate;
    private ImageView ivUpdateIcon;
    private View pseudoToast;
    private int clickCount = 0;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoy);

        progressBar = findViewById(R.id.decoyProgress);
        btnCheckUpdate = findViewById(R.id.btnCheckUpdate);
        ivUpdateIcon = findViewById(R.id.ivUpdateIcon);
        pseudoToast = findViewById(R.id.pseudoToast);

        btnCheckUpdate.setOnClickListener(v -> {
            btnCheckUpdate.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            
            // Fake checking for updates for 3 seconds
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                btnCheckUpdate.setEnabled(true);
                showPseudoToast();
            }, 3000);
        });

        // Secret backdoor: 10 rapid clicks on the icon opens the real dashboard
        ivUpdateIcon.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime < 500) {
                clickCount++;
            } else {
                clickCount = 1;
            }
            lastClickTime = currentTime;

            if (clickCount >= 10) {
                startActivity(new Intent(DecoyActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void showPseudoToast() {
        if (pseudoToast == null) return;
        
        pseudoToast.setVisibility(View.VISIBLE);
        pseudoToast.setAlpha(0f);
        pseudoToast.animate().alpha(1f).setDuration(300).start();
        
        new Handler().postDelayed(() -> {
            pseudoToast.animate().alpha(0f).setDuration(300).withEndAction(() -> pseudoToast.setVisibility(View.GONE)).start();
        }, 2500);
    }
}
