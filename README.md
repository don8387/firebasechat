### chat powered by FCM Topics

#### Description

This is a simple chat with a few bots. Features:
* an app sends HTTP requests (send message to firebase topic)
* bots and a user use a same topic, so the user can receive messages from bots 
* bots will send a random favorites color by random time interval
* an app is subscribed to a specific topic by default 
* an app receives messages by using FCMServiceListener
* when FCMServiceListener will get a new message, it will pass it to a chat view model by using a bridge. After the chat view model will update a chat recycler view.

First Screen | Second Screen 
------------ | ------------- 
![alt text](https://raw.githubusercontent.com/don8387/firebasechat/develop/scr01.png) | ![alt text](https://raw.githubusercontent.com/don8387/firebasechat/develop/scr04.png)

