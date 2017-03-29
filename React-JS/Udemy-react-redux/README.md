Learning react-redux from https://www.udemy.com/react-redux/

> React (user action) =>  ActionCreator => Action => Middleware => Reducers => State => (back to) React

* `Redux`
	- serves to provide state of the application.

* `React`
	- displays the state.

* `Reducer` 
	- A function that reduces a piece of the application state.
	- There could be as many reducers as the components that have state.
	- Reducers accept a 'state' and 'action' as argument.
	- Reducers are combined together

* `Containers`
	- __Smart containers__ have connection to data.
	- __Dumb containers__ have no connection to data and give the layout of the view.

* `Action` and `ActionCreators`
	- `Actions` describe the intent/action of the user.
	- `ActionCreators` create 'actions' that are dispatched and handled by reducers to alter state.

* `Middleware`
	- Take 'actions' as input and can optionally manipulate, log, stop it.
	- `redux-promise` is a npm package for Ajax middleware.
	- `Axios` returns promise that is intercepted by 'redux-promise' and forwarded on completion. This helps to keep the code clean.

* `React-Router`
	- Updates the react components depending on the routes.


___
### Steps to create React-Redux app
* Create component.
* Create `reducer` for the component. Reducer takes 'state' and 'action' as arguments.
* Combine the above reducer along with others to the global application state with some key for each. Usually done in `reducers/index.js`
	Eg: if 'books' is key, data can be accessed as `state.books`.
* Connecting React and Redux. Inside the component
	- `import { connect } from 'react-redux'`
	- add a function `mapStateToProps(state)`
	- connect and export `export default connect(mapStateToProps)(BookList);`
* Creating `actions` and `actionCreators`
	- `ActionCreators` create `Actions` that contain a 'type' and 'payload'.
	- `import { bindActionCreators } from 'redux'`
	- add a function 
	```
	mapDispatchToProps(dispatch) {
		return bindActionCreators({ actionCreatorKey:<actionCreator> }, dispatch)
	}
	```
	- connect and export `export default connect(mapStateToProps)(BookList);`
* Making Ajax request with `redux-promise` as middleware and `Axios` library.
	- create store by applying the middleware.

___

## Rally coding
* Redux-Thunk
	- for async action creators.
	- enables returning function from action creator
	```
	return (dispatch) => { 
		request.then({data}) => {
			dispatch({type: 'ACTION_DO_STUFF', payload: data})
		}
	}
	```
* Reselect
	- Use it when calculating a state in a reducer is a product of 2 or more states.
	- Create reselect `selector`.
* ReactCSSTransitionGroup - for animation magic.
	- `npm install --save react-addons-ccs-transition-group`
	- <transition-name>-enter  for initial state.
	- <transition-name>-enter-active for actual animation.
* While using browserHistory router, for all requests, send 'index.html'
* While working with 3rd party libs 
	- create wrapper components
	- use ref system to get div.
	- return false from `shouldComponentUpdate()` lifecycle method.
	- Initialize component once by overriding `componentDidMount()` and capture the reference in `this`
	- To update values, 
		- change props for the component.
		- override `componentWillReceiveProps(nextProps)` and implement the change using reference created with `this` in 
		`componentDidMount()`	

___
# Deployment
#### Development Pipeline  
> Code => Webpack => Webpack dev Server => localhost:8080

#### Production Pipeline  
> Code => Push to Heroku => Install NPM modules => Run 'postinstall' => Run 'start' => xyz.herokuapp.com

* Heroku automatically runs 'postinstall' and 'start' scripts. These are defined in package.json
* `postinstall` runs once. In package.json =>
	```
	"scripts": {
		"postinstall": "webpack -p",
		"start": "node server.js"
	}
	```












