# appium



## Requirements

1. Node version (hopefully >= 20.9.0) or Bun.

## Installation
To install dependencies:

```bash
bun install
```

or

```bash
bun install -g appium
```

or

```bash
npm install -g appium
```

Then install `appium-doctor`.

## Steps to verify if your installation is corret:

1. Run `appium -v`.
  - It should show something like `2.5.4`
  - If it isn't try installing it again (following the instructions above or visiting the appium [quickstart page](https://appium.io/docs/en/latest/quickstart/install/))
2. Verify your `ANDROID_HOME` environment variable is set for the verison you want to test. You can do so with `appium-doctor`.
  - Windows: `powershell gci env:ANDROID_HOME`
  - Linux based: `echo $ANDROID_HOME`
3. Verify your `JAVA_HOME` environment variable is set to android_home
3. Prepare the device you want to test. It can be an emulator or a real device. Follow the instructions found in [this page](https://appium.io/docs/en/latest/quickstart/uiauto2-driver/#prepare-the-device). You should be able to see your device by running `adb devices` on your terminal.
4. Install the uiautomator2 driver. `appium driver install uiautomator2` && `appium driver install espresso`
  - If it fails uninstall it `appium driver uninstall uiautomator2` or espresso and reinstall it.
5. Run the appium server with the following command in your shell: `appium`
5. Install the appium inspector tool to write and monitor your tests. You can find the repo [here](https://github.com/appium/appium-inspector).
6. Configure the appium inspector tool:
  - You'll have to add a similar json found below to the section *JSON Representation* part of the panel **Capacity Builder**.
  - For more details regarding 
  ```json
  {
    "platformName": "Android",
    "appium:deviceName": "emulator-5554",
    "appium:appPackage": "com.example.viniloscompose",
    "appium:appWaitActivity": "com.example.viniloscompose.MainActivity",
    "appium:automationName": "Espresso",
    "appium:driver": "Compose"
  }
  ```
  - Put the port and ip address of your appium server, it should be the default values at this point, but if it isn't please introduce them correctly.
7. Start appium inspector session!
