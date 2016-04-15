# Working with OneSignal to send multi platform notifications.

[One Signal](https://onesignal.com/) can be used to send notifications across a large no of supported platforms including mobiles, desktops, web browsers using a common API.

Here, are the steps to subscribe a Chrome browser based user and send notifications.


___


### The One Signal side of things =>

 * Register on [One Signal](https://onesignal.com/)  if you haven't already done so.
 * Select the target platform as `website push` => `chrome`.
 * Configure the settings for `Chrome` as follows: 
   * `Site Url` : `*` for anywhere or `localhost` for local testing. (I have kept it as `*` for this POC.
   * Enter the Google GCM `Server key` obtained while registering the App on GCM. For info on how to obtain key, refer => [here](https://github.com/raj-saxena/My-Technical-Learnings/blob/master/google-cloud-messaging/working-with-Google-Cloud-Messaging.md).
   * Enter the public url of any image that you may want to use as icon displayed during user consent popup.
   * Mark the checkbox if the site is HTTP or not. (I did as part of local testing).
   * Set the test domain. I had set it to `testonesignal`.
   * Enter the Google project no as obtained during the GCM registration stage.
 *  Select the target SDK as `Website Push`.
 *  Before continuing, let's setup the page that will be used to subscribe the user to OneSignal server for receiving push as below. Keep this tab open for later.


### The client side of things =>

Create the following HTML page that gets served to the user for registering to the subscribe push notifications from our OneSignal app.  
You can write something similar to the following code snippet that I used for my POC. 
```html
<head>
  <script src="https://cdn.onesignal.com/sdks/OneSignalSDK.js" async></script>
  <script>  
    var OneSignal = OneSignal || [];

    OneSignal.push(["init", {appId: "<your-unique-app-Id-obtained-from-OneSignal-App-Settings-console", subdomainName: "subdomainName-given-in-above-setup"}]);
  </script>
</head>


<body>
  <a href="#" id="registerForPushLink">Subscribe to Notifications</a>  
  <script>
  OneSignal.push(function() {
    function registerForPush(event) {
      OneSignal.push(["registerForPushNotifications"]);
      event.preventDefault();
    }
    document.getElementById("registerForPushLink").addEventListener('click', registerForPush, false);

  });
  </script>
</body>

```
* Hit the url to get the page and click on `Subscribe to Notifications`.
* On successful initialization, you should see a popup asking for your consent.
* Additionally, chrome will ask for your permission to allow the app to show notifications. Click on allow when it does.
* **Note**: Look for any errors in browser console by pressing F12 if the above fails.
___

> * Now, go back to the **`Configure Platform`** tab that we left open earlier, it should automatically detect that one user is registered and will give the userId of him.
> * Click on next to test settings by sending a test notification.
> * Send a test notification to verify that it works successfully.
