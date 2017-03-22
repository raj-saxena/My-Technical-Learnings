Learning react-redux from https://www.udemy.com/react-redux/


* `Redux`
	- serves to provide state of the application.

* `React`
	- displays the state.

* `Reducer` 
	- A function that reduces a piece of the application state.
	- There could be as many reducers as the components that have state.
	- Reducers accept a 'state' and 'action' as argument

* `Containers`
	- __Smart containers__Have connection to data.
	- __Dumb containers__ have no connection to data and give the layout of the view.

* `Action` and `ActionCreators`
	- `Actions` describe the intent/action of the user.
	- `ActionCreators` create 'actions' that are dispatched and handled by reducers to alter state.




### Steps to create React-Redux app
	* Create component.
	* Create `reducer` for the component.
	* Combine the above reducer along with others to the global application state with some key for each. 
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