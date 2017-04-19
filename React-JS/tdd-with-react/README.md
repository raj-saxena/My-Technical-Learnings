# Getting started

* Run `npm init` and add following as **test** script
```javascript
"test": "node_modules/mocha/bin/mocha --require setup.js *.spec.js"
```

* Install following

```javascript
npm install --save react react-dom react-addons-test-utils chai sinon enzyme jsdom mocha babel-core babel-preset-es2015 babel-preset-react
```

* *Babel* needs to be told to use those 2 presets. This configuration goes in a file named .babelrc. Create that file and paste this in:
```javascript
{
  "presets": ["es2015", "react"]
}
```

* We need a setup.js file to initialize our fake DOM. Create the **setup.js** file and paste this in:
```javascript
require('babel-register')();

var jsdom = require('jsdom').jsdom;

var exposedProperties = ['window', 'navigator', 'document'];

global.document = jsdom('');
global.window = document.defaultView;
Object.keys(document.defaultView).forEach((property) => {
  if (typeof global[property] === 'undefined') {
    exposedProperties.push(property);
    global[property] = document.defaultView[property];
  }
});

global.navigator = {
  userAgent: 'node.js'
};
```

* Create a sample test
```javascript
import { expect } from 'chai';

describe('the test environment', () => {
  it('works, hopefully', () => {
    expect(true).to.be.true;
  });
});
```
* Test that Mocha is working
`npm test`

___
## Setup react component
* Install pending dependencies
```javascript
npm install --save webpack webpack-dev-server path babel-loader
```

* Create **index.html** at root with following:
```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>React App Setup</title>
  </head>
  <body>
    <div id="root">

    </div>
  </body>
  <script src="/bundle.js"></script>
</html>
```

