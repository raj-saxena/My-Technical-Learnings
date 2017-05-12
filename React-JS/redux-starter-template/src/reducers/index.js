import { combineReducers } from 'redux';
import ProfileReducer from './profileReducer'

const rootReducer = combineReducers({
  profile: ProfileReducer
});

export default rootReducer;