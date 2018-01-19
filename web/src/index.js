import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Home from './Home';
import Page2 from './Page2';
//import './index.css';
import {Router,Route,browserHistory,IndexRoute} from 'react-router';

ReactDOM.render(
  (<Router history={browserHistory}>
  	<Route path="/" component={App}>
  		<IndexRoute component={Home}/>
	  	<Route path="/page2" component={Page2}/>
	</Route>
  </Router>),
  document.getElementById('root')
);
