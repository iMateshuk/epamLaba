import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { setUserData, getUserLogin } from './UtilUserData'
import Header from './Header';


function Login() {

    const navigate = useNavigate();

    const [inputs, setInputs] = useState({});
    const [errors, setErrors] = useState({});


    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        errors[name] = ``;
        errors.error = ``;

        if (!value) {
            errors[name] = `${name} - field cannot be empty`;
        } else if (value.length <= 3) {
            errors[name] = `${name} - field to short`;
        }

        if (value.length >= 30) {
            errors[name] = `${name} - field max length `;
        }

        setInputs(values => ({ ...values, [name]: value }));
        setErrors(errors => ({ ...errors }));
    }

    const handleSubmit = (event) => {

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(inputs)
        };

        fetch('/gift-certificate-app/login', requestOptions)
            .then(
                async response => {
                    const isJson = response.headers.get('content-type')?.includes('application/json');
                    const data = isJson && await response.json();

                    // check for error response
                    if (!response.ok) {
                        // get error message from body or default to response status
                        const error = (data && data.errorMessage) || response.status;
                        return Promise.reject(error);
                    }

                    //add userData to xStorage
                    setUserData(data);
                    navigate("/gift-certificate-app/certificates");
                }
            )
            .catch(
                error => {
                    errors.error = error.toString();
                    setErrors(errors => ({ ...errors }));
                    console.error('There was an error!', error);
                }
            );

        event.preventDefault();
    }

    useEffect(() => {
        if (getUserLogin() !== undefined) {
            navigate("/gift-certificate-app/certificates");
        }
    })

    return (
        <form className="form-login" onSubmit={handleSubmit}>
            <Header />
            <h1 className="font-weight-normal">Login</h1>
            <input
                type="text"
                id="inputUsername"
                className="form-control"
                placeholder="Login"
                name="login"
                required minLength={3} maxLength={30}
                autoFocus
                value={inputs.login || ""}
                onChange={handleChange}
            />
            {<span className="form-error" style={{ color: "red" }}>{errors.login}</span>}

            <input
                type="password"
                id="inputPassword"
                className="form-control"
                placeholder="Password"
                name="password"
                required minLength={4} maxLength={30}
                value={inputs.password || ""}
                onChange={handleChange}
            />
            {<span className="form-error" style={{ color: "red" }}>{errors.password}</span>}
            {<span className="form-error" style={{ color: "red" }}>{errors.error}</span>}
            <input className="btn btn-lg btn-primary btn-block" type="submit" name="Login" />
        </form>
    )
}

export default Login;