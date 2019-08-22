# react-native-file-share-intent

Adds the application to the share intent of the device, so it can be launched from other apps and receive data from them 


## Installation

* Install the module

```bash
npm i --save react-native-file-share-intent
```

## Android Installation

* In `android/settings.gradle`

```gradle
...
include ':react-native-file-share-intent', ':app'
project(':react-native-file-share-intent').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-file-share-intent/android')
```

* In `android/app/build.gradle`

```gradle
...
dependencies {
    ...
    compile project(':react-native-file-share-intent')
}
```

* In `android/app/src/main/AndroidManifest.xml` in the `<activity>` tag: 
Add What ever Want to share MIME Type so only include you want to share your App


```xml
<activity
  ...
 android:documentLaunchMode="never">
  ...
  <intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="text/plain" />
    <data android:mimeType="image/*" /> 
    <data android:mimeType="application/*" />
  </intent-filter>
</activity>
```

* Register module (in MainApplication.java)

```java
import com.ajithab.RNFileShareIntentPackage;  // <--- import

public class MainApplication extends Application implements ReactApplication {
  ......
  @Override
  protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
      new MainReactPackage(),
      new RNFileShareIntentPackage()  // <------ add here
    );
  }
  ......

}
```

### Donate

<p><a href="https://www.paypal.me/ajithab" rel="nofollow"><img height="75" src="https://raw.githubusercontent.com/stefan-niedermann/paypal-donate-button/master/paypal-donate-button.png" style="max-width:100%;"></a></p>



## Example


App.js

```javascript
import React, { Component } from 'react';
import  {Text,View} from 'react-native';

import RNFileShareIntent from 'react-native-file-share-intent';
 
export default class App extends Component {
  constructor(props) {
    super(props); 
    this.state = {
      fileUrl: null,
    };
  }
 
  componentDidMount() {
   if(RNFileShareIntent){
     RNFileShareIntent.getFilepath((url) => {
       this.setState({ fileUrl: url }); 
       })  
   }
  
  }
  render() {
    var uri = this.state.fileUrl;
    return (
      <View style={{flex:1,justifyContent:'center'}}>
        <Text>Shared Url: {uri}</Text>
      </View>
    );
  }
}
 
```




## Credits

Sponsored and developed by Ajith A B
