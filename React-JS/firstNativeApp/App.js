import React, { Component } from 'react';
import { StyleSheet, Text, View, ListView } from 'react-native';

export default class App extends Component {
  constructor(props) {
    super(props);

    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {
      dataSource: ds.cloneWithRows(['Hi Meggie', 'What\'s the plan for today?']),
    };
  }

  render() { 
    return (
      <View style={styles.container}>
        <ListView style={styles.list}
          dataSource={this.state.dataSource}
          renderRow={(rowData) => <Text style={styles.text}>{rowData}</Text>} />
      </View>
      );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f55',
    alignItems: 'center',
    justifyContent: 'center'
  },
  list: {
    margin: 20
  },
  text: {
    fontSize: 30
  }
});
