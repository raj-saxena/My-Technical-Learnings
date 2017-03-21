import React from 'react';

const VideoListItem = ({video, onVideoSelect}) => {
	const snippet = video.snippet
	return(
		<li onClick={() => onVideoSelect(video)} className="list-group-item">
			<div className="video-item media">
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