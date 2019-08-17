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
