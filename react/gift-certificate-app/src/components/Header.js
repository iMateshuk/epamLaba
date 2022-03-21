import {Button, FormControl, Nav, Navbar} from "react-bootstrap";
import {getUserLogin, isRoleAdmin, removeUserData, setUserData} from './UtilUserData';
import {useNavigate} from "react-router-dom";
import {useState} from "react";

function Header(props) {

    let headerName = 'UI';
    let buttonLogin = 'Login';
    let buttonMain = 'Main';
    let userLogin = '';

    const [inputs, setInputs] = useState({});
    const navigate = useNavigate();

    if (isRoleAdmin()) {
        headerName = 'Admin UI';
    }

    if (getUserLogin() !== undefined) {
        userLogin = 'name: ' + getUserLogin();
        buttonLogin = 'Logout';
    }

    const chooseAction = (url) => {
        if (buttonLogin === 'Logout') {
            removeUserData();
        }
        navigate(url);
    }

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}));
    }

    const handleSubmit = (event) => {
        let check = {};
        const tags = [];
        const searchData = {};

        if (inputs.search) {
            inputs.search.split('#').map(element => {
                element = element.trim();
                check = element.match('^\\((\\w|\\d| )+\\)');
                check
                    ? tags.push(check[0].match('(\\w|\\d| )+')[0])
                    : (searchData.certName = element) && (searchData.certDesc = element);
            })
            searchData.tagName = tags;
            navigate(window.location.pathname + "?" + new URLSearchParams(searchData));
            window.location.reload();
        }
    }

    return (
        <>
            <Navbar className="top-sticky" bg="dark" variant="dark">
                <Nav.Item className="ml-auto header-txt">
                    <Navbar.Brand>{headerName}</Navbar.Brand>
                </Nav.Item>
                <Navbar.Text>
                    {props.isAdmin ?
                        <Button className="header-txt" variant="outline-success" size="sm">Add
                            Certificate</Button> : ''}
                </Navbar.Text>

                {!window.location.pathname.includes('login') ?
                    <>
                        <FormControl onChange={handleChange} name="search" size="sm" type='text' placeholder='Search'
                                     className='mr-sm-1 header-btn'
                                     style={{maxWidth: '17%'}}/>
                        <Button onClick={handleSubmit} className='header-btn' size="sm"
                                variant='outline-info'>Search</Button>
                    </>
                    : ''
                }
                <div className='header-btn'
                     style={{width: '30%', color: 'grey', textAlign: 'right'}}>{userLogin}</div>
                {!window.location.pathname.includes('login') ?
                    <Button className='header-btn' variant="outline-secondary" size="sm"
                            onClick={() => chooseAction("/gift-certificate-app/login")}>{buttonLogin}</Button>
                    : <Button className='header-btn' variant="outline-secondary" size="sm"
                              onClick={() => chooseAction("/gift-certificate-app/certificates")}>{buttonMain}</Button>}
            </Navbar>
        </>
    );
}

export default Header;