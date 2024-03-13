import React from 'react';
import Login, { Render } from 'react-login-page';
import axios from "axios";
import LoginForm from "./pages/login";

const Activities = () => {
 let baseUrl = 'http://localhost:8080';
 const [activities, setActivities] = React.useState([]);
 let activitiesUrl = baseUrl +'/activities';



return (
    <div><p>Hiiii!!!</p></div>
)

};

export default Activities;
