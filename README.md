# Twigpage.app

A simple fragments WebView app for twigpage.com. It loads a website inside a app. File upload is enabled, as well as JavaScript access.

# JavaScript interface

On twigpage.com,call the JavaScript interface inside the app to trigger Java functions. A few examples:

```	
var android = {

	// Shows a toast inside the twigpage app
	showAndroidToast: function(toast) {
		if(window.innerWidth > 700) {
			window.alert(toast);
		} else {
			try {
			Android.showToast(toast);
			} catch(e) {}
		}
	return true;
    	},
	
	// Checking the device pixel density
	androidDensity: function() {
		try {
		if (window.devicePixelRatio >= 1.5) {
			AndroidDevice.density('high');
			} else {
			AndroidDevice.density('low');
			} 
		} catch(e) {}
	return true;
	},

	// Shows a upload progress bar when updating settings.
	progressAndroid: function(toast) {
		try {
        		AndroidProgress.uploadSettings(toast);
		} catch(e) {}
	return true;
	}
};
```

# References

If you want to learn more about Webview, Java file access and Android Camera integration and how to inplement this in Android step by step, then visit these tutorials below:

1. https://developer.android.com/develop/ui/views/layout/webapps/webview
2. https://medium.com/@stevesohcot/andriod-studio-webview-tutorial-4651701d7d1a
3. https://gist.github.com/mksantoki/097bb1fa73af1f404acc02bb594cd743 */
4. https://github.com/OpenGeeksMe/Android-File-Chooser/blob/master/app/src/main/java/it/floryn90/webapp/MainActivity.java 

Please note that the second tutorial is somewhat outdated, and might not work on the lastest Android Studio. I updated the code to make it work for Android Studio Dolphin | 2021.3.1 Patch 1. Refer to the MainActivity.java file for updated source code.
