import React, { Component } from 'react';

class SearchBar extends Component {
	constructor(props) {
		super(props);
		
		this.state = {term: ''}
	}

	render() {
		return (
			<div className="search-bar">
				<input
					value={this.state.term} 
					onChange = {event => this.onEventChange(event.target.value)} />
			</div>
			);
	}

	onEventChange(term) {
		this.setState({ term});
		this.props.onSearchTermChange(term);
	}
}

export default SearchBar;