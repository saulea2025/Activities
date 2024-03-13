import "./App.css";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import LoginForm from "./pages/login";
import Activities from "./activities";
function App() {
    return (
        <div className="App">
            <header className="container">
                <div className="">
                    <Router>
                        <Switch>
                            <Route exact path="/" component={LoginForm} />
                            <Route path="/activities" component={Activities} />
                        </Switch>
                    </Router>
                </div>
            </header>
        </div>
    );
}

export default App;