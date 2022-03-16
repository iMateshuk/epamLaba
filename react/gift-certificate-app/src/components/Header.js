import React from "react";
import { Navbar, Button, Nav } from "react-bootstrap";
import { isRoleAdmin, getUserLogin, removeUserData } from './UtilUserData';
import { useNavigate } from "react-router-dom";

function Header() {

    let headerName = 'UI';
    let buttonName = 'Login';
    let userLogin = '';

    if (isRoleAdmin()) {
        headerName = 'Admin UI';

    }

    if (getUserLogin() !== undefined) {
        userLogin = 'name: ' + getUserLogin();
        buttonName = 'Logout';
    }

    const navigate = useNavigate();

    const chooseAction = () => {

        if (buttonName === 'Logout') {
            removeUserData();
        }
        navigate("/gift-certificate-app/login");
    }


    return (
        <>
            <Navbar fixed="top" bg="dark" variant="dark" >
                <Nav.Item className="ml-auto">
                    <Navbar.Brand >{headerName}</Navbar.Brand>
                </Nav.Item>
                <Navbar.Text> {userLogin} </Navbar.Text>
                <Button variant="outline-secondary" size="sm" onClick={chooseAction} >{buttonName}</Button>
            </Navbar>
        </>
    );
};

export default Header;