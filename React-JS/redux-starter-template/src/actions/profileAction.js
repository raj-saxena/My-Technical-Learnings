export const GET_PROFILE = 'get_profile';

export function getProfile() {
	return {
		type: GET_PROFILE,
		payload: {
			name: 'Raj'
		}
	}
}