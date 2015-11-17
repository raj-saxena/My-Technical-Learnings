* Go to the [google console](https://console.developers.google.com/project) and create a project for your app to send GCM messages from your server.
* I created a project named `testGCM`
* Once created, note down the Project number. For me, it was => 663694774030
* Now, click on the link to `Enable and manage APIs`.
* Under mobile APIs, search for `Cloud Messaging for Android` and enable it.
* Go back to the `API Manager` page.
* On the left, look for `Credentials` link. Create a new **server key** and note down the value for the key. We will call it the **API_KEY**. For me, this was 'AIzaSyB8Bp5GpdLiC1GjkQMXYnZyiiAw_Rh-Ei0'

* Transfer the attached GCM_Test.apk file and install it on your mobile. The app will install as **GCMTestApp**.
* Open the app and enter the project number in the `*Enter GCM Key*` field and press submit.
* The app will register the device with GCM server and display the registration Id.
* Copy the **registrationId** and save it. (I use pushbullet app to universally copy-paste between computer and mobile).

* Next, you are all set to send the msg to your mob now. 
* Open some rest client like **Postman** or **Advanced rest client** chrome extensions.
* In the URL field enter `https://android.googleapis.com/gcm/send`
* Set the headers as
```
    Content-Type = application/json
    Authorization = key=AIzaSyB8Bp5GpdLiC1GjkQMXYnZyiiAw_Rh-Ei0  //Use your *API_KEY* here
```
* Add the body as below
```
  {
    "registration_ids": ["APA91bE-NEN00KNTWxBl8xJ5cb9FbjAUILQ2qLf8yrNZF-mJo-wQhCcJcG24mHWxSgpy5TebKCaXeW5VbYvEM_BN-2nM8iGp0gTSDQAfoqMCW9Ydap2nP5p0NedMXmhA0zgg5j2zqhwv"],
    "data": {
      "value": "Why So serious?"
    }
  }
```
* Use the registrationId obtained in the above step while registering the device on GCM server.
* Change the value of the 'value' key and see the msgs coming instantaneously as toasts.

> **_Note_**
> * It is a simple android app, so you need to have the app open in foreground.
> * The demo values used here won't work as I have deleted my testGCM app.
> * Remember to use the correct project number as the **SenderID**