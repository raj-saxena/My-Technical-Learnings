# Understanding Webpack and Babel

## Pre-requisites
	- node and yarn

* Create a new folder, name it anything.
```bash
> mkdir hello-world-react
> cd hello-world-react
> yarn init
```

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
	

#### Reference(https://scotch.io/tutorials/setup-a-react-environment-using-webpack-and-babel)
