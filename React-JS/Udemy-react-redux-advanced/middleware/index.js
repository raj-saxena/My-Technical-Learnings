import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';

import App from './src/components/app';
import reducers from './src/reducers/index';
import Async from './src/middlewares/async';

const createStoreWithMiddleware = applyMiddleware(Async)(createStore);

ReactDOM.render(
	<Provider store= { createStoreWithMiddleware(reducers) }>
		<App />
	</Provider>
	, document.getElementById('root'));