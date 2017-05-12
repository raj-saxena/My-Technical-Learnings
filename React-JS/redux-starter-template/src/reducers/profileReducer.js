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