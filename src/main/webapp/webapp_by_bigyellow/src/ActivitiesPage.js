// UsersPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ActivitiesPage = () => {
    const [activities, setActivities] = useState([]);
    const [activityId, setActivityId] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
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
    );
};

export default ActivitiesPage;