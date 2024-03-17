// WelcomePage.js
import React from "react";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

function WelcomePage({ username, onLogout }) {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Выполняем выход пользователя и передаем управление родительскому компоненту
        onLogout();
    };

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

    return (
        <div className="container">
            <p>You are successfully logged in, {username}!</p>
            <button className="logout-btn" onClick={handleLogout}>Logout</button>
            <button className="user-page-btn" onClick={handleGoToActivitiesPage}>Go to User Page</button>
            <button className="activities-page-btn" onClick={handleGoToActivitiesPage}>Go to Activities Page</button>
        </div>
    );
}

export default WelcomePage;
