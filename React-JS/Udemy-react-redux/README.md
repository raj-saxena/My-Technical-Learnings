Learning react-redux from https://www.udemy.com/react-redux/


* `Reducer` 
	- A function that reduces a piece of the application state.
	- There could be as many reducers as the components that have state.

* `Containers`
	- __Smart containers__Have connection to data.
	- __Dumb containers__ have no connection to data and give the layout of the view.

* Connecting React and Redux.
	- `import { connect } from 'react-redux'`
	- add a function `mapStateToProps(state)`
	- connect and export `export default connect(mapStateToProps)(BookList);`