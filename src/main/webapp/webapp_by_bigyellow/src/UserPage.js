// UsersPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UsersPage = () => {
    /*const [activities, setActivities] = useState([]);
    const [activityId, setActivityId] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetchActivities();
    }, []);

    const fetchActivities = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://192.168.100.21/users');
            setActivities(response.data);
        } catch (error) {
            console.error('Error fetching activities:', error);
        } finally {
            setLoading(false);
        }
    };*/


    return(
        <div>Users page</div>
    )
    /*return (
        <div>
            <h1>Users Activities</h1>
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

export default UsersPage;