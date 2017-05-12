import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { getProfile } from '../actions/profileAction';

class Profile extends Component {
	componentDidMount() {
		this.props.getProfile();
	}

	render() {
		return(
				<div>Hello {this.props.profile.name}!</div>
			);
	}
}

function mapStateToProps(state) {
	return {
		profile: state.profile
	}
}

function mapDispatchToProps(dispatch) {
	return bindActionCreators({ getProfile: getProfile }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile)