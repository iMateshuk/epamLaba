import React, {useState} from "react";

function Login() {
    const [inputs, setInputs] = useState({});
    const [errors, setErrors] = useState({});

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        errors[name] = ``;

        if (!value) {
            errors[name] = `${name} - field cannot be empty`;
        } else if (value.length <= 3) {
            errors[name] = `${name} - field to short`;
        }

        if (value.length >= 30) {
            errors[name] = `${name} - field max length `;
        }

        setInputs(values => ({...values, [name]: value}));
        setErrors(errors => ({...errors}));
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log(inputs);
    }

    return (
        <form className="form-login" onSubmit={handleSubmit}>
            <h1 className="font-weight-normal">Login</h1>
            {<span style={{color: "red"}}>{errors.login}</span>}
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

            {<span style={{color: "red"}}>{errors.password}</span>}
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

            <button className="btn btn-lg btn-primary btn-block" type="submit">
                Login
            </button>
        </form>
    )
}

export default Login;
/*const Login = () => (
    <form className="form-login" >
        <h1 className="font-weight-normal">Login</h1>
        <input
            type="text"
            id="inputUsername"
            className="form-control"
            placeholder="Username"
            required=""
            autoFocus=""
            size="30"
        />
        <input
            type="password"
            id="inputPassword"
            className="form-control"
            placeholder="Password"
            required=""
            size="30"
        />
        <button className="btn btn-lg btn-primary btn-block" type="submit">
            Login
        </button>
    </form>
);

export default Login;*/
