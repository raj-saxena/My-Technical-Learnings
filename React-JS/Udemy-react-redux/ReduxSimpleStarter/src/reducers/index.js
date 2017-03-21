import { combineReducers } from 'redux';
import BookReducer from './reducer_books'

const rootReducer = combineReducers({
  books: BookReducer
});

export default rootReducer;
