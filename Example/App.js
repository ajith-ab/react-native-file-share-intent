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
        <Text style={{fontSize:30,color:'black'}}>Shared Url: {uri}</Text>
      </View>
    );
  }
}
 