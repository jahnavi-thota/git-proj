
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'

import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponents';
import UserList from './components/UserList';
import AddUser from './components/AddUser';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {UserList}></Route>
                          <Route path = "/add-user/:id" component = {AddUser}></Route>
                           
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
