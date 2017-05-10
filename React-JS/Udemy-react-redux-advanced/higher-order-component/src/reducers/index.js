import { combineReducers } from 'redux';
import authenticationReducer from './authentication';

const rootReducer = combineReducers({
	authenticated: authenticationReducer
});

export default rootReducer;