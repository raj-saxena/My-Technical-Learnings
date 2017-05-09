import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

export default function(ComposedComponent) {
	class Authentication extends Component {
 		static contextTypes = {
			router: PropTypes.object
		}

		componentWillMount() {
			if(!this.props.authenticated) {
				this.context.router.push('/');
			}
		}

		componentWillUpdate(nextProps, nextState) {
			if(!nextProps.authenticated) {
				this.context.router.push('/');

			}
		}
		
		render() {
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