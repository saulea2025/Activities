import React from 'react';
import Login, { Render } from 'react-login-page';
import axios from "axios";
import { useHistory } from 'react-router-dom';

const LoginForm = () => {
    let baseUrl = 'http://localhost:8080';
    const history = useHistory();
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const loginUrl = baseUrl +'/login';

    const onSubmit = async () => {
        if( email != '' && password != ''){
            await axios.post(loginUrl, {
                "email": email,
                "password": password
            })
                .then(r => {
                    if(r.status == 200) {
                        console.log('success');
                        history.push('/activities');
                        history.go('/activities');
                    } else {
                        console.log('fail');

                    }
                });
        }

    }
    return (
        <Login>
            <Render>
                {({ fields, buttons}) => {
                    return (
                        <div>
                            <div>
                                <label>{fields.username}</label>
                            </div>
                            <div>
                                <label>{fields.password}</label>
                            </div>
                            <div>
                                {buttons.submit}
                            </div>
                        </div>
                    );
                }}
            </Render>
            <Login.Block keyname="title" tagName="span">
                Login
            </Login.Block>
            <Login.Input keyname="username" placeholder="Username" onChange={(ev) => setEmail(ev.target.value)}/>
            <Login.Input keyname="password" placeholder="Password" onChange={(ev) => setPassword(ev.target.value)}/>
            <Login.Button keyname="submit" type="submit" onClick = {onSubmit}>
                Submit
            </Login.Button>
        </Login>
    );
};
export default LoginForm;