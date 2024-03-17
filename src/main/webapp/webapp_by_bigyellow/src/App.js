// App.js
import React, { useState } from "react";
import { useCookies } from "react-cookie";
import LoginPage from "./LoginPage";
import WelcomePage from "./WelcomePage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserPage from "./UserPage";
import ActivitiesPage from "./ActivitiesPage";
import axios from "axios";

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
                <Route path="/" element={<LoginPage />} />
                <Route path="/menu" element={<WelcomePage />} />
                <Route path="/user" element={<UserPage />} />
                <Route path="/activities" element={<ActivitiesPage />} />
            </Routes>
        </Router>
    );
}

export default App;
