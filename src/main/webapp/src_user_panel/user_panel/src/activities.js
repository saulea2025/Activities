import React, {useEffect} from 'react';
import Login, { Render } from 'react-login-page';
import axios from "axios";
import LoginForm from "./pages/login";

const Activities = () => {
 const baseUrl = 'http://localhost:8080';
 const [activities, setActivities] = React.useState([]);


 useEffect(() => {
  getActivities().then(console.log('activities fetched'));
 }, []);

 const getActivities = async () => {
     let activitiesUrl = baseUrl +'/activities';
  await axios
        .get(activitiesUrl)
        .then((res) => {
       console.log(res.data);
       setActivities(res.data);
      })
      .catch((err) => {
       console.log(err);
      });
 }

return (
    <div><p>Hiiii!!!</p></div>
)

};

export default Activities;
