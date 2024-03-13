import React from 'react';
import Login, { Render } from 'react-login-page';
import axios from "axios";

const Demo = () => {
    const baseUrl = 'http://localhost:8080';

    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const onSubmit = () => {
        if( email != '' && password != ''){
            let loginUrl = baseUrl +'/login';
            axios.post(loginUrl, {
                "email": email,
                "password": password
            })
                .then(r => {
                    if(r.status == 200) {
                        console.log('success');
                    } else {
                        console.log('fail');
                    }
                })
        }

    }
  return (
      <Login>
        <Render>
          {({ fields, buttons, blocks, $$index }) => {
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
                    {buttons.reset}
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
export default Demo;