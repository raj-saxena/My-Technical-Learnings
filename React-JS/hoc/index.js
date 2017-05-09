import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import { Router, Route, browserHistory } from 'react-router';

import App from './src/components/app';
import Resources from './src/components/resources'

import reducers from './src/reducers/index';

const createStoreWithMiddleware = applyMiddleware()(createStore);

ReactDOM.render(
	<Provider store={ createStoreWithMiddleware(reducers) } >
		<Router history={browserHistory}>
			<Route path="/" component={App} >
				<Route path="resources" component={Resources} />
			</Route>
		</Router>
	</Provider>
	, document.getElementById('root'));