import {Button, FormControl, Nav, Navbar} from "react-bootstrap";
import {getUserLogin, isRoleAdmin, removeUserData} from './UtilUserData';
import {useNavigate} from "react-router-dom";

function Header(props) {

    let headerName = 'UI';
    let buttonLogin = 'Login';
    let buttonMain = 'Main';
    let userLogin = '';
    let error = false;

    if (isRoleAdmin()) {
        headerName = 'Admin UI';
    }

    if (getUserLogin() !== undefined) {
        userLogin = 'name: ' + getUserLogin();
        buttonLogin = 'Logout';
    }

    const navigate = useNavigate();

    const chooseAction = (url) => {
        if (buttonLogin === 'Logout') {
            removeUserData();
        }
        navigate(url);
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
                        <FormControl size="sm" type='text' placeholder='Search'
                                     className='mr-sm-1 header-btn'
                                     style={{maxWidth: '17%'}}/>
                        <Button className='header-btn' size="sm" variant='outline-info'>Search</Button>
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