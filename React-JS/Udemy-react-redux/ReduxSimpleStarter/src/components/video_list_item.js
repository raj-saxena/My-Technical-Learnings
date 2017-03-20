import React from 'react';

const VideoListItem = ({video}) => {
	const snippet = video.snippet
	return(
		<li className="list-group-item">
			<div className="video-list media">
				<div className="media-left">
					<img className="media-object" src={snippet.thumbnails.default.url} />
				</div>
				<div className="media-body">
					<div className="media-heading">{snippet.title}</div>
				</div>
			</div>
		</li>
		);
}

export default VideoListItem;