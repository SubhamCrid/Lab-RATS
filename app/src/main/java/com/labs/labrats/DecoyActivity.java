package com.labs.labrats;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

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
        
        String componentName = getIntent().getComponent().getClassName();
        Log.d("DecoyActivity", "Launched via: " + componentName);

        if (componentName.contains("CalculatorAlias")) {
            setContentView(R.layout.activity_decoy_calculator);
            setupCalculator();
        } else if (componentName.contains("WeatherAlias")) {
            setContentView(R.layout.activity_decoy_weather);
            setupWeather();
        } else if (componentName.contains("SettingsAlias")) {
            setContentView(R.layout.activity_decoy_settings);
        } else {
            setContentView(R.layout.activity_decoy);
            setupUpdateDecoy();
        }
    }

    private void setupUpdateDecoy() {
        progressBar = findViewById(R.id.decoyProgress);
        btnCheckUpdate = findViewById(R.id.btnCheckUpdate);
        ivUpdateIcon = findViewById(R.id.ivUpdateIcon);
        pseudoToast = findViewById(R.id.pseudoToast);

        btnCheckUpdate.setOnClickListener(v -> {
            btnCheckUpdate.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            
            new Handler(getMainLooper()).postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                btnCheckUpdate.setEnabled(true);
                showPseudoToast();
            }, 3000);
        });

        ivUpdateIcon.setOnClickListener(v -> handleBackdoorClick());
    }

    private void setupCalculator() {
        TextView display = findViewById(R.id.calcDisplay);
        if (display == null) return;

        display.setOnClickListener(v -> handleBackdoorClick());

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String text = b.getText().toString();
            String current = display.getText().toString();

            if (text.equals("C") || text.equals("AC")) {
                display.setText("0");
            } else if (text.equals("=")) {
                try {
                    if (current.contains("+")) {
                        String[] parts = current.split("\\+");
                        double res = Double.parseDouble(parts[0]) + Double.parseDouble(parts[parts.length-1]);
                        display.setText(formatResult(res));
                    } else if (current.contains("-")) {
                        String[] parts = current.split("-");
                        double res = Double.parseDouble(parts[0]) - Double.parseDouble(parts[parts.length-1]);
                        display.setText(formatResult(res));
                    } else if (current.contains("x")) {
                        String[] parts = current.split("x");
                        double res = Double.parseDouble(parts[0]) * Double.parseDouble(parts[parts.length-1]);
                        display.setText(formatResult(res));
                    } else if (current.contains("/")) {
                        String[] parts = current.split("/");
                        double res = Double.parseDouble(parts[0]) / Double.parseDouble(parts[parts.length-1]);
                        display.setText(formatResult(res));
                    }
                } catch (Exception e) {
                    display.setText("0");
                }
            } else {
                if (current.equals("0") && !text.equals(".")) display.setText(text);
                else display.setText(current + text);
            }
        };

        android.view.ViewGroup root = (android.view.ViewGroup) display.getParent();
        findAndAttachButtons(root, listener);
    }

    private void findAndAttachButtons(android.view.ViewGroup parent, View.OnClickListener listener) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            if (v instanceof Button) {
                v.setOnClickListener(listener);
            } else if (v instanceof android.view.ViewGroup) {
                findAndAttachButtons((android.view.ViewGroup) v, listener);
            }
        }
    }

    private String formatResult(double d) {
        if (d == (long) d) return String.format(Locale.US, "%d", (long) d);
        else return String.format(Locale.US, "%.2f", d);
    }

    private void setupWeather() {
        TextView cityTv = findViewById(R.id.weatherCity);
        if (cityTv != null) {
            String city = getSharedPreferences("LabRATSSettings", MODE_PRIVATE).getString("last_city", "New York");
            cityTv.setText(city);
        }

        View root = findViewById(android.R.id.content);
        if (root != null) {
            root.setOnClickListener(v -> handleBackdoorClick());
        }
    }

    private void handleBackdoorClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 500) {
            clickCount++;
        } else {
            clickCount = 1;
        }
        lastClickTime = currentTime;

        if (clickCount >= 10) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void showPseudoToast() {
        if (pseudoToast == null) return;
        pseudoToast.setVisibility(View.VISIBLE);
        pseudoToast.setAlpha(0f);
        pseudoToast.animate().alpha(1f).setDuration(300).start();
        new Handler(getMainLooper()).postDelayed(() -> {
            pseudoToast.animate().alpha(0f).setDuration(300).withEndAction(() -> pseudoToast.setVisibility(View.GONE)).start();
        }, 2500);
    }
}
