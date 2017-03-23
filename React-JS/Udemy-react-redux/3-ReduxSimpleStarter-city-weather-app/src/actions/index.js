import axios from 'axios';

const API_KEY = '01d7c0255b77ff0623637ce74d20860a';
const ROOT_URL = `http://api.openweathermap.org/data/2.5/forecast?appid=${API_KEY}`

export const FETCH_WEATHER = 'FETCH_WEATHER';

export function fetchWeather(city) {
	const url = `${ROOT_URL}&q=${city},in`
	const request = axios.get(url)

	return {
		type: FETCH_WEATHER,
		payload:request
	};
}