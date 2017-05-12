# Adding Redux to a react project
    Redux is a document store that is used to manage the state of the application.

## Pre-requisites
> * A basic understanding of different components like actionCreators, actions, reducers, middleware etc. [Quick reference](https://github.com/raj-saxena/My-Technical-Learnings/tree/master/React-JS)
> * Have a React project setup already.
> If you don't, [here are the instructions](https://github.com/raj-saxena/My-Technical-Learnings/tree/master/React-JS/react-starter-template)

## Instructions

* Install **react** and **react-redux**.  
**react-redux** is a library to connect react components with redux store.
```
yarn add redux react-redux
```

* Create `action` and `actionCreator`
```
export const GET_PROFILE = 'get_profile';

export function getProfile() {
    return {
        type: GET_PROFILE,
        payload: {
            name: 'Raj'
        }
    }
}
```

* Create `reducer` that will handle the action and produce state:
```
import { GET_PROFILE } from '../actions/profileAction';

let defaultProfile = { name: 'world' };

export default function(state = defaultProfile, action) {
    switch(action.type) {
        case GET_PROFILE: return {
            ...state, ...action.payload
        }
    }
    return state;
}
```

* Create a `rootReducer` that will combine all reducers using `combineReducer from 'redux'`.
```
import { combineReducers } from 'redux';
import ProfileReducer from './profileReducer'

const rootReducer = combineReducers({
    profile: ProfileReducer
});

export default rootReducer;
```

* Create a `container` and `connect` the state to the component.  
Add function `mapStateToProps` to bind state to the component.  
Similarly, use `mapDispatchToProps` to link actions and component via bindActionCreators.
```
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { getProfile } from '../actions/profileAction';

class Profile extends Component {
    componentDidMount() {
        this.props.getProfile();
    }

    render() {
        return(
            <div>Hello {this.props.profile.name}!</div>
        );
    }
}

function mapStateToProps(state) {
    return {
        profile: state.profile
    }
}

function mapDispatchToProps(dispatch) {
    return bindActionCreators({ getProfile: getProfile }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile)
``` 

* Import the container in **app.js**


* In `index.js`, import `applyMiddleware` and `createStore` from **redux** and `Provider` from **react-redux**.  
`applyMiddleware` and `createStore` with `rootReducer` to form the `store`.  
Pass the above created `store` to `Provider`.

```
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';

import reducers from './src/reducers/index';

const createStoreWithMiddleware = applyMiddleware()(createStore);

ReactDOM.render(
  <Provider store={createStoreWithMiddleware(reducers)}>
    <App />
  </Provider>
  , document.querySelector('#root'));

```