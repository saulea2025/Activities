// WelcomePage.js
import React from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useCookies } from "react-cookie";

function WelcomePage({ username, onLogout }) {
    const navigate = useNavigate();

    function handleLogout(event) {
        event.preventDefault()
        axios.get('http://192.168.100.21:8080/logout')
            .then(function (response) {
                console.log(response);
                console.log("Successfully Logged out ");
                navigate('/'); //use this  instead of history.push
            })
            .catch(err => console.log(err))
    }

    const handleGoToUserPage = () => {
        navigate("/user"); // Перенаправляем на страницу пользователя
    };
    const handleGoToActivitiesPage = () => {
        navigate("/activities"); // Перенаправляем на страницу пользователя
    };

    return (
        <div className="container">
            <p>You are successfully logged in, {username}!</p>
            <button className="logout-btn" onClick={handleLogout}>Logout</button>
            <button className="user-page-btn" onClick={handleGoToUserPage}>Go to User Page</button>
            <button className="activities-page-btn" onClick={handleGoToActivitiesPage}>Go to Activities Page</button>
        </div>
    );

}

export default WelcomePage;
