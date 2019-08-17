
import { NativeModules } from 'react-native';

export default {
    openURL: (url) => NativeModules.RNFileShareIntent.openURL(url),
    getFilePath: (url) => NativeModules.RNFileShareIntent.getFilePath(url),
    close: () => NativeModules.RNFileShareIntent.invokeToTheHostApp()

  }
