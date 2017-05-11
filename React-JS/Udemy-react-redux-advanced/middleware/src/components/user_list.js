import React, { Component } from 'react';
import { connect } from 'react-redux';

import * as actions from '../actions';

class UserList extends Component {
	componentDidMount() {
		this.props.fetchUsers();
	}

	showUser(user) {
		return (
			<div key={user.name} className="card card-block">
				<h4 className="card-title">{ user.name }</h4>
				<p className="card-text">{ user.company.name}</p>
				<a className="btn btn-primary" href={ user.website }>Website</a>
			</div>
			);
	}

	render() {
		return (
			<div className="user-list">
				{this.props.users.map(this.showUser)}
			</div>
		);
	}
}

function mapStateToProps(state) {
	return {
		users: state.users
	}
}

export default connect(mapStateToProps, actions)(UserList);