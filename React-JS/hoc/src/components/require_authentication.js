import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

export default function(ComposedComponent) {
	class Authentication extends Component {
 		static contextTypes = {
			router: PropTypes.object
		}

		render() {
			console.log(this.context);
			return <ComposedComponent {...this.props} />
		}
	}

	function mapStateToProps(state) {
		return {
			authenticated: state.authenticated
		}
	}

	return connect(mapStateToProps)(Authentication);
}