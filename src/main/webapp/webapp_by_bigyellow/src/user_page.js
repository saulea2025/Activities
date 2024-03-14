import React, { Component } from "react";

class UserPage extends Component {
    handleLogout = () => {
    }

    render() {
        return (
            <div>
                <h2>Welcome to your User Page</h2>
                <p>This is your personalized page!</p>
                <button onClick={this.handleLogout}>Logout</button>
            </div>
        );
    }
}

export default UserPage;
