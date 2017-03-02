Project Setup

Follow these steps to setup the project for development.
    Install Node
    Install Cordova

Open command prompt
    Navigate to \application
Execute: npm install

Open command prompt
    Navigate to \application
    Run a Cordova prepare operation (see section Local Cordova Module for more details)
    cordova platform add android / ios
    cordova build ios / android

Open Xcode
Open \application\platforms\ios\CRNS.xcodeproj

Select "CRNS" on the left
Select the "General" tab on the right
Configure the "Signing" section as follows
Make sure you are signed into Xcode
Xcode > Preferences... > Accounts
Click the "+" icon on the bottom left
Click "Add Apple ID..."
Sign in with your Apple ID
Make sure "Automatically manage signing" is checked (it should be by default)
Select your Apple ID in the "Team" drop down list
Build/deploy to simulator and/or device
