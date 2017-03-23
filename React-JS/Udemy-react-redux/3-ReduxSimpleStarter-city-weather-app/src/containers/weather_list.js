import React, { Component } from 'react';
import { connect } from 'react-redux';

import Chart from '../components/chart';

class WeatherList extends Component {
	render() {
		const cities = this.props.weather.map(w => {
			const name = w.city.name;

			const temp = w.list.map(i => {return i.main.temp})
			const humidity = w.list.map(i => {return i.main.humidity})
			const pressure = w.list.map(i => {return i.main.pressure})

			return (
				<tr key={name}>
					<td>{name}</td>
					<td><Chart data={temp} color="orange" /></td>
					<td><Chart data={pressure} color="green" /></td>
					<td><Chart data={humidity} color="blue" /></td>
				</tr>
				)
		});
		return (
		<table className="table table-hover">
			<thead>
				<tr>
					<th>City</th>
					<th>Temperature</th>
					<th>Pressure</th>
					<th>Humidity</th>
				</tr>
			</thead>
			<tbody>
				{cities}
			</tbody>
		</table>
		)
	}
}

function mapStateToProps({weather}) {
	return { weather };
}

export default connect(mapStateToProps)(WeatherList)