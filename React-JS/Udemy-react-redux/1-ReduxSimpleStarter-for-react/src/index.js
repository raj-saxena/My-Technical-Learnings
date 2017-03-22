import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import YTSearch from 'youtube-api-search';
import _ from 'Lodash';

import SearchBar from './components/search_bar';
import VideoList from './components/video_list'
import VideoDetail from './components/video_detail'

const API_KEY = 'AIzaSyAQPuYwlGJReMtdZ4LHrqUSzmIcyG0grLw'



class App extends Component {
	constructor(props) {
		super(props);

		this.state = { 
			videos: [],
			selectedVideo: null
		};

		this.searchVideo('Scala');
	}

	searchVideo(term) {
		YTSearch({key: API_KEY, term: term}, (videos) => {
			this.setState({ 
				videos: videos,
				selectedVideo: videos[0]
			})
		})
	}

	render() {
		const searchVideo = _.debounce(term => {this.searchVideo(term)}, 300);
		return (
			<div>
				<SearchBar onSearchTermChange={searchVideo}/>
				<VideoDetail video = {this.state.selectedVideo}/>
				<VideoList 
					onVideoSelect={selectedVideo => this.setState({selectedVideo})}
					videos={this.state.videos} />
			</div>
			);
	}
}
ReactDOM.render(<App />
	, document.querySelector('.container'));

