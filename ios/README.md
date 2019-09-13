
# react-native-file-share-intent

Adds the application to the share intent of the device, so it can be launched from other apps and receive data from them 


## Installation

* Install the module

```bash
npm i --save react-native-file-share-intent
```

# IOS Installation


- Click on your project's name
- Click on `+` sign

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_01.png" />
</p>

- Select `Share Extension` under `iOS > Application Extension`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_02.png" />
</p>

- Select a name for your new share extension, in my case I chose `Share Intent`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_03.png" />
</p>

- Select both files  `ShareViewController.h` and `ShareViewController.m`. 

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_04.png" />
</p>

- Copy the below code and paste into the `ShareViewController.h`

```objective-c
#import <UIKit/UIKit.h>
#import <Social/Social.h>

@interface ShareViewController :  UIViewController

@end
```

- Copy the below code and paste into the `ShareViewController.m`
```objective-c
#import "ShareViewController.h"
#import "RCTRootView.h"
#import "RCTBundleURLProvider.h"
#import "RNFileShareIntent.h"

@interface ShareViewController ()

@end

@implementation ShareViewController

- (void) loadView
{
NSURL *jsCodeLocation;

NSExtensionItem *item = self.extensionContext.inputItems.firstObject;
NSItemProvider *itemProvider = item.attachments.firstObject;
[RNFileShareIntent setShareFileIntentModule_itemProvider:itemProvider];
[RNFileShareIntent setContext: self.extensionContext];


#if DEBUG
jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"Share" fallbackResource:nil];
#else
jsCodeLocation = [NSURL URLWithString:@"http://localhost:8081/share.bundle?platform=ios&dev=true"]; 
// Change localhost to your network Ip for your Device Debugging
#endif


RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
moduleName:@"Share"
initialProperties:nil
launchOptions:nil];
self.view = rootView;
}

// animate (IN)
- (void)viewWillAppear:(BOOL)animated
{
[super viewWillAppear:animated];

self.view.transform = CGAffineTransformMakeTranslation(0, self.view.frame.size.height);
[UIView animateWithDuration:0.25 animations:^
{
self.view.transform = CGAffineTransformIdentity;
}];
}


@end

```


- Now it's time to add our library. Right click on the `Libraries` group and select `Add Files to "Sample1"...`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_09.png" />
</p>

- select `node_modules` > `react-native-file-share-intent` > `ios` > `RNFileShareIntent.xcodeproj`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_05.png" />
</p>

- Now we need to tell the share extension that we want to read new header files. Click on project name (in my case `Example`), then click on your extension name (in my case `Share Intent`). After that click on Build Settings and search for `Header Search Paths`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_06.png" />
</p>

- Add the new path `$(SRCROOT)/../node_modules/react-native-file-share-intent/ios` with `recursive` selected

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_06.png" />
</p>

- We need to add some linker flags as well, so search for `Other Linker Flags` and add `-ObjC` and `-lc++`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_07.png" />
</p>

- We also need to add all the static libraries such as `React` and `React Native File Share Intent`. Select the `General` tab and under `Linked frameworks and Libraries` click on `+` and add all of the selected static binaries there

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_08.png" />
</p>

- select from top menu `Products` > `Schemes` > `Edit Schemes` > `Build` click on `+` and add `React`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_10.png" />
</p>

- Select the `Build Phases` tab and under `Target Dependencies` click on `+` and add React

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_step_11.png" />
</p>

- Select Share Extension folder in side Menu `info.plist` and add below code

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/ios_setup_12.png" />
</p>

```
<key>NSAppTransportSecurity</key>
  <dict>
    <key>NSExceptionDomains</key>
    <dict>
      <key>localhost</key>
      <dict>
        <key>NSTemporaryExceptionAllowsInsecureHTTPLoads</key>
        <true/>
      </dict>
    </dict>
  </dict>
```

### For IOS Share Extension View

Share.js in the Root Folder

```javascript
import React, { Component } from 'react';
import { View, Text, AppRegistry, StyleSheet, Button } from 'react-native';
import RNFileShareIntent from 'react-native-file-share-intent';
export default class Share extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sharedText: null
        };
    }
    componentDidMount() {
        var that = this;
        RNFileShareIntent.getFilePath((text) => {
            that.setState({ sharedText: text });

        })
    }

    render() {
        var url = this.state.sharedText;
        return (
            <View style={{ flex: 1, justifyContent: 'center' }}>
                <Text style={styles.text}>Shared file Path: {url}</Text>
                <Button
                    title="open With Other App"
                    color="#841584"
                    onPress={() => RNFileShareIntent.openURL(url)}
                />
                <Button
                    title="Close Extension"
                    color="#841584"
                    onPress={() => RNFileShareIntent.close()}
                />
            </View>
        )
    }
}

const styles = StyleSheet.create({
    text: {
        color: 'black',
        backgroundColor: 'white',
        fontSize: 30,
        margin: 80
    }
});

AppRegistry.registerComponent('Share', () => Share);

```



Or check the "example" directory for an example application.

### For Ios Release

- run command in the root folder

```bash
react-native bundle --entry-file share.js --platform ios --dev false --bundle-output ios/share.jsbundle --assets-dest ios
```

then open Xcode and  from side menu select the folder of  `share extension` and right-click the folder you will get a menu to `add files` then you will select the  `share.jsbundle`  from your ios folder 


### Donate

<p><a href="https://www.paypal.me/ajithab" rel="nofollow"><img height="75" src="https://raw.githubusercontent.com/stefan-niedermann/paypal-donate-button/master/paypal-donate-button.png" style="max-width:100%;"></a></p>


## Communication Between Main App and Share Extension 

- create a Same App group For `Main App` and `Share Extension` through Select the Main App or Share Extension and Select `Capablities tab`  then select App group `create group with same name`

<p align="center">
<img src ="https://github.com/ajith-ab/react-native-file-share-intent/blob/master/assets/appgroup.png" />
</p>

 ###  Message Communication 
   
  ```bash
  npm install --save react-native-swiss-knife
  ```
  
 - To save a value on your main app:
 
 ```Javascript
import { RNSKBucket } from 'react-native-swiss-knife'

const myGroup = 'group.groupName'
RNSKBucket.set('test', 'myValue', myGroup)
 ```
 
 - and then, to read the saved data on your shared extension:
 
 ```Javascript
import { RNSKBucket } from 'react-native-swiss-knife'

const myGroup = 'group.groupName'
RNSKBucket.get('test', myGroup).then( (value) => console.log(value) ) // myValue
```

### Manage Files

- Use  <a href="https://www.npmjs.com/package/react-native-fs">  react-native-fs</a>

- In Share Extension

```Javascript

import * as RNFS from 'react-native-fs';

var path = RNFS.pathForGroup('group.groupName');
path = path+'filename.txt'; // according to files use .jpeg, .pdf etc ...
RNFS.copyFile(url, path) // url-> path getting From Share Extension, path -> copying Location
    .then((success) => {
      
    })
    .catch((err) => {
      console.log("Error: " + err.message);
    });
```
- In Main App

```Javascript

import * as RNFS from 'react-native-fs';

var path = RNFS.pathForGroup('group.groupName');
RNFS.readDir(path) 
  .then((result) => {
    console.log('GOT RESULT', result);
 
    // stat the first file
    return Promise.all([RNFS.stat(result[0].path), result[0].path]);
  })
  .then((statResult) => {
    if (statResult[0].isFile()) {
      // if we have a file, read it
      return RNFS.readFile(statResult[1], 'utf8');
    }
 
    return 'no file';
  })
  .then((contents) => {
    // log the file contents
    console.log(contents);
  })
  .catch((err) => {
    console.log(err.message, err.code);
  });
```
### Opening Share Extension in Main App

- In Share Extension

```javascript
import RNFileShareIntent from 'react-native-file-share-intent';

RNFileShareIntent.openURL(url)

```
- In Main App 

 Refer <a href="https://medium.com/react-native-training/deep-linking-your-react-native-app-d87c39a1ad5e"> Linking</a>

```javascript
import { Linking } from 'react-native';

 Linking.getInitialURL().then((url) => {
    if (url) {
      console.log('Initial url is: ' + url);
    }
  }).catch(err => console.error('An error occurred', err));
```

## Credits

Sponsored and developed by Ajith A B
