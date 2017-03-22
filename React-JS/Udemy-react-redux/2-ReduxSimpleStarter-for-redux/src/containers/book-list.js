import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { selectBook } from '../actions/index'

class BookList extends Component {
	listBooks() {
		return this.props.books.map(book => {
			return (
				<li 
					key={book.title} 
					onClick={() => this.props.selectBook(book)}
					className="list-group-item">
					{book.title}
				</li>
				);
		})
	}
	render() {
		return (
			<ul className="list-group col-md-4">
			{this.listBooks()}
			</ul>
		);
	}
}

function mapStateToProps(state) {
	return {
		books: state.books
	}
}

function mapDispatchToProps(dispatch) {
	return bindActionCreators({ selectBook:selectBook }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(BookList);