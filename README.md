# simple firebasechat powered by FCM Topics

This is a simple chat with many bots. When a user clicks on a button *send*, an app sends an HTTP request (send message to firebase topic). Bots use the same topic with the user, so the user can receive messages from bots (they will send a random favorites color by random time interval). Also, the app is subscribed to a specific topic. The app receives messages by using FCMServiceListener. When FCMServiceListener will get a new message, it will pass it to a chat view model by using a bridge.  After the chat view model will update a chat recycler view.

First Screen | Second Screen | Third Screen
------------ | ------------- | -------------
![alt text](https://raw.githubusercontent.com/don8387/firebasechat/develop/photo_1.jpg) | ![alt text](https://raw.githubusercontent.com/don8387/firebasechat/develop/photo_2.jpg) | ![alt text](https://raw.githubusercontent.com/don8387/firebasechat/develop/photo_3.jpg)
