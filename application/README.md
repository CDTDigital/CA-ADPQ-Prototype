**Project Setup**

**Prerequisites**:
 - Xcode
 - Android Studio
 - Apple Developer Account
 - Install Node: https://nodejs.org/en/download/

**Install Cordova:**

 - MAC machine: sudo npm install -g cordova
 - Windows machine: npm install -g cordova


*Follow these steps to setup the project for development:*

**Open command prompt**
 - Navigate to \application
 - Execute: npm install
 - Run: cordova platform add ios OR cordova platform add android
 - Run: cordova build ios OR cordova build android

**Open Xcode** for iOS:
Open \application\platforms\ios\CRNS.xcodeproj

 - Select "CRNS" on the left
 - Select the "General" tab on the right
 - Configure the "Signing" section as follows
 - Make sure you are signed into Xcode
 - Xcode > Preferences... > Accounts Click the "+" icon on
   the bottom left Click "Add Apple ID..."
 - Sign in with your Apple ID
 - Make sure "Automatically manage signing" is checked (it should be by default)
 - Select your Apple ID in the "Team" drop down list
 - Build/deploy to simulator and/or device

**Open Android Studio** for Android

 - Open Android Studio.
 - Select 'Import project (Eclipse ADT, Gradle, etc.)'
 - Choose android folder from  \application\platforms\android
 - Attach the Android device with machine
 - Tap on run icon to build the application on device.

**From terminal**, we build the Android app from below Cordova command:
 - Run: cordova run android