# Setting up React project with Webpack and Babel

## Pre-requisites
	- node and yarn

* Create a new folder, name it anything.
```bash
> mkdir hello-world-react
> cd hello-world-react
> yarn init
```

___
## Webpack
```bash
> yarn add webpack webpack-dev-server path
```

* We need a config file to give it instructions on what to do - `webpack.config.js`.
* Webpack creates a dependency graph of nodes(files).
* If the file extension is configured, it will run corresponding loader for it. Eg ES6 => ES5 conversion via babel
* Add configuration to webpack.config.js
	```javascript
	const path = require('path');
	module.exports = {
	  entry: './index.js',
	  output: {
	    path: path.resolve('dist'),
	    filename: 'bundle.js'
	  },
	  module: {
	    loaders: [
	      { test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
	      { test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/ }
	    ]
	  }
	}
	```

___
## Babel
```bash
> yarn add babel-loader babel-core babel-preset-es2015 babel-preset-react --dev
```

* Presets are Babel plugins that simply tell Babel what to look out for and transform into plain, vanilla Javascript.
* Configure babel with `.babelrc`.
	```javascript
		{
		    "presets":[
		        "es2015", "react"
		    ]
		}
	```
* (Note: add `"stage-1"` in **.babelrc** if you are planning to use static contextTypes and install `babel-preset-stage-1`)

## Other files 
* Create `index.html` containing the 'root' container. Add the output js file configured in `webpack.config.js` as the last line after <body. `<script src="/bundle.js"></script>`
* Create `index.js` as the starting point.
* Add a start script to `package.json`.
	```
	"scripts": {
    	"start": "webpack-dev-server"
  	},
  	```

___
## React
```bash
> yarn add react react-dom
> mkdir components 
> cd components
> touch App.jsx
```

* Create a simple component
	```javascript
		import React, { Component } from 'react';

		export default class App extends Component {
		  render() {
		    return (
		     <div style={{textAlign: 'center'}}>
		        <h1>Hello World</h1>
		      </div>);
		  }
		}
	```
* Change `index.js` to include component and provide starting point
	```javascript
		import React from 'react';
		import ReactDOM from 'react-dom';
		import App from './components/App.jsx';
		
		ReactDOM.render(<App />, document.getElementById('root'));
	```

		
#### [Reference](https://scotch.io/tutorials/setup-a-react-environment-using-webpack-and-babel)
