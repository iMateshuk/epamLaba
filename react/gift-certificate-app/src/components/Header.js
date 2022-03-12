import React from "react";
import {Navbar} from "react-bootstrap";

const Header = () => (
    <>
        <Navbar fixed="top" bg="dark" variant="dark">
            <h4 className="text-secondary" align="center">Admin UI</h4>
        </Navbar>
    </>
);

export default Header;

/*
export default class Header extends Component {
    render() {
        return (
            <>
                <Navbar fixed="top" bg="dark" variant="dark">
                    <h4 class="text-secondary">Admin UI</h4>
                </Navbar>
            </>
        )
    }
}*/
