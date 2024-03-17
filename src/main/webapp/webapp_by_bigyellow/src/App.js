// App.js
import React, { useState } from "react";
import { useCookies } from "react-cookie";
import LoginPage from "./LoginPage";
import WelcomePage from "./WelcomePage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserPage from "./UserPage";

function App() {
    const [cookies, setCookie, removeCookie] = useCookies(['user']);
    const [isLoggedIn, setIsLoggedIn] = useState(!!cookies.user);

    const handleLogin = (user) => {
        setCookie('user', user, { path: '/' });
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        removeCookie('user');
        setIsLoggedIn(false);
    };

    return (
        <Router>
            <Routes>
                <Route path="/" element={isLoggedIn ? <WelcomePage username={cookies.user.username} onLogout={handleLogout} /> : <LoginPage onLogin={handleLogin} />} />
                <Route path="/user" element={<UserPage />} />
            </Routes>
        </Router>
    );
}

export default App;
