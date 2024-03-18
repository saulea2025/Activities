// UsersPage.js
import React, {useState, useEffect, useCallback} from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const UsersPage = () => {

    const [activities, setActivities] = useState([]);
    const [activityId, setActivityId] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    //const [id, setId] = useState('');
    const [post, setPost] = useState({
        id: '1',
    })
    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem('jwtToken'); // Получение токена из localStorage
                const headers = {
                    'Authorization': `${token}` // Создание заголовка Authorization с токеном
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

    /*    return(
            <div>Users page</div>
        )*/
    return (
        <div>
            <h1>Your Activities</h1>
            {loading && <p>Loading...</p>}
            <form>
                <label htmlFor="activities_select">Выберите текущую активность:</label>
                <select id="id" name="id" onChange={handleSelectChange}>
                    {activities.map(activity => (
                        <option key={activity.id} value={activity.id}>{activity.name}</option>
                        ))}
                </select>
            </form>
            <ul>
                {activities.map(activity => (
                    <li key={activity.id}>{activity.name}, priority: {activity.priority}, status: {activity.status}</li>
                ))}
            </ul>
            <button className="logout-btn" type="button" onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default UsersPage;