// UsersPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const ActivitiesPage = () => {
    const [activities, setActivities] = useState([]);
    const [mode, setMode] = useState('');
    const [loading, setLoading] = useState(false);


    //const [id, setId] = useState('');
    const [post, setPost] = useState({
        name: '',
        priority: '',
        status: ''
    })
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
                const headers = {
                    'Authorization': `${token}` // Создание заголовка Authorization с токеном
                };
                console.log(headers);
                const response = await axios.get('http://192.168.100.21:8080/activities', {headers: headers});
                console.log('get performed')
                setActivities(response.data.activities);
                setMode(response.data.mode);
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
            'Authorization': `${token}` // Создание заголовка Authorization с токеном
        };
        axios.get('http://192.168.100.21:8080/logout', { headers: headers })
            .then(function (response) {
                console.log(response);
                console.log("Successfully Logged out ");
                //localStorage.setItem('jwtToken', '');
                localStorage.clear();
                navigate('/'); //use this  instead of history.push
            })
            .catch(err => console.log(err))

    }


    const handleSelectChange = (event) => {
        const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
        const headers = {
            'Authorization': `${token}` // Создание заголовка Authorization с токеном
        };
        setPost({...post, [event.target.name]: event.target.value})
        console.log(post)
        axios.post('http://192.168.100.21:8080/users', post, { headers: headers })
            .then(function (response) {
                console.log(response);
            })
            .catch(err => console.log(err))
    }



    const handleInput = (event) => {
        setPost({...post, [event.target.name]: event.target.value})
    };

    function handleSubmit(event) {
        event.preventDefault()
        const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
        const headers = {
            'Authorization': `${token}` // Создание заголовка Authorization с токеном
        };
        axios.post('http://192.168.100.21:8080/activities', post, {headers: headers})
            .then(function (response) {
                console.log(response);
                console.log("Successfully created ");
                console.log(response.data.id);
                localStorage.setItem('jwtToken', response.data.id);
            })
            .catch(err => console.log(err))

    }





    if(mode==='admin') {
        return (
            <div>
                <form>
                    <p>Create new activity:</p>
                    <div>
                        <label>Name:</label>
                        <input type="text" name="name" onChange={handleInput}/>
                    </div>
                    <div>
                        <label>Priority:</label>
                        <input type="text" name="priority" onChange={handleInput}/>
                    </div>
                    <div>
                        <label>Status:</label>
                        <input type="twxt" name="status" onChange={handleInput}/>
                    </div>
                    <button className="login-btn" type="button" onClick={handleSubmit}>Create</button>
                </form>
                <h1>Activities</h1>
                {loading && <p>Loading...</p>}
                <ul>
                    {activities.map(activity => (
                        <li key={activity.id}>{activity.activityName}, priority: {activity.priority},
                            status: {activity.status}, person: {activity.personName} {activity.personSurname}</li>
                    ))}
                </ul>
                <button className="logout-btn" type="button" onClick={handleLogout}>Logout</button>
            </div>
        );
    }
    if (mode === 'user') {
        return (
            <div>
                <h1>Activities</h1>
                {loading && <p>Loading...</p>}
                <ul>
                    {activities.map(activity => (
                        <li key={activity.id}>{activity.activityName}, priority: {activity.priority},
                            status: {activity.status}, person: {activity.personName} {activity.personSurname}</li>
                    ))}
                </ul>
                <button className="logout-btn" type="button" onClick={handleLogout}>Logout</button>
            </div>
        );
    }


    /*useEffect(() => {
        fetchActivities();
    }, []);

    const fetchActivities = async () => {
        try {
            setLoading(true);
            const response = await axios.get('/activities');
            setActivities(response.data);
        } catch (error) {
            console.error('Error fetching activities:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleActivitySubmit = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            await axios.post('/users', { id: activityId });
            // После успешного добавления можно обновить список активностей
            fetchActivities();
        } catch (error) {
            console.error('Error adding activity:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h1>All Activities</h1>
            {loading && <p>Loading...</p>}
            <ul>
                {activities.map(activity => (
                    <li key={activity.id}>{activity.name}</li>
                ))}
            </ul>
            <form onSubmit={handleActivitySubmit}>
                <label>
                    Activity ID:
                    <input
                        type="text"
                        value={activityId}
                        onChange={(e) => setActivityId(e.target.value)}
                    />
                </label>
                <button type="submit">Add Activity</button>
            </form>
        </div>
    );*/
};

export default ActivitiesPage;