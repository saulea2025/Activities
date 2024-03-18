// UsersPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const UsersPage = () => {

    const [activities, setActivities] = useState([]);
    const [activityId, setActivityId] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const [selectedValue, setSelectedValue] = useState('');
    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
                const headers = {
                    'Authorization': `Bearer ${token}` // Создание заголовка Authorization с токеном
                };
                console.log(headers);
                const response = await axios.get('http://192.168.100.21:8080/users', {headers: headers});
                console.log('get performed')
                setActivities(response.data);
            } catch (error) {
                console.error('Error fetching data', error);
            }
        };

        fetchData();
    }, []);

    function handleLogout(event) {
        event.preventDefault()
        const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
        const headers = {
            'Authorization': `Bearer ${token}` // Создание заголовка Authorization с токеном
        };
        axios.get('http://192.168.100.21:8080/logout', { headers: headers })
            .then(function (response) {
                console.log(response);
                console.log("Successfully Logged out ");
                navigate('/'); //use this  instead of history.push
            })
            .catch(err => console.log(err))

    }
    /*useEffect(() => {
        fetchActivities();
    }, []);

    const fetchActivities = async () => {
        const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
        const headers = {
            'Authorization': `Bearer ${token}` // Создание заголовка Authorization с токеном
        };
        try {
            setLoading(true);
            const response = await axios.get('http://192.168.100.21/users', {headers: headers});
            setActivities(response.data);
        } catch (error) {
            console.error('Error fetching activities:', error);
        } finally {
            setLoading(false);
        }
    };*/


    const handleSelectChange = (event) => {
        setSelectedValue(event.target.value);
        console.log(selectedValue)
    }

    /*    return(
            <div>Users page</div>
        )*/
    return (
        <div>
            <h1>Your Activities</h1>
            {loading && <p>Loading...</p>}
            <form>
                <label htmlFor="activities_select">Выберите текущую активность:</label>
                <select id="activities" name="activities" onChange={handleSelectChange}>
                    {activities.map(activity => (
                        <option key={activity.id} value={activity.name}>{activity.name}</option>
                        ))}
                </select>
            </form>
            <ul>
                {activities.map(activity => (
                    <li key={activity.id}>{activity.name}, priority: {activity.priority}, status: {activity.status}</li>
                ))}
            </ul>
            <button className="logout-btn" type="button" onClick={handleLogout}>Logout</button>
{/*            <form onSubmit={handleActivitySubmit}>
            <label>
                Activity ID:
                <input
                    type="text"
                    value={activityId}
                    onChange={(e) => setActivityId(e.target.value)}
                />
            </label>
            <button type="submit">Add Activity</button>
        </form>*/}
        </div>
    );
};

export default UsersPage;