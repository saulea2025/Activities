// LoginPage.js
import React, { useState } from "react";
import axios from "axios";

function LoginPage({ onLogin }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    constructor(props){

    }
    
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        if (name === "username") {
            setUsername(value);
        } else if (name === "password") {
            setPassword(value);
        }
    };

    const handleLogin = () => {
        // Выполняем аутентификацию и передаем данные пользователя в родительский компонент
        onLogin({ username, password });
    };

    return (
        <div className="container">
            <h2>Welcome to our website</h2>
            <p>To continue, please login:</p>
            <form>
                <div>
                    <label>Username:</label>
                    <input type="text" name="username" value={username} onChange={handleInputChange} />
                </div>
                <div>
                    <label>Password:</label>
                    <input type="password" name="password" value={password} onChange={handleInputChange} />
                </div>
                <button className="login-btn" type="button" onClick={handleLogin}>Login</button>
            </form>
        </div>
    );
}

export default LoginPage;