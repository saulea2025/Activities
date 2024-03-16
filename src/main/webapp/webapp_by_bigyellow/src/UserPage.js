import React, { useState, useEffect } from "react";

function UserPage() {
    const [userData, setUserData] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('http://localhost:8080/users')
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Failed to fetch user data');
                }
            })
            .then(data => {
                setUserData(data);
                setLoading(false);
            })
            .catch(error => console.error('Error fetching user data:', error));
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>User Information</h2>
            <p><strong>Name:</strong> {userData.name}</p>
            <p><strong>Email:</strong> {userData.email}</p>
            {/* Дополнительная информация о пользователе, если необходимо */}
        </div>
    );
}

export default UserPage;
