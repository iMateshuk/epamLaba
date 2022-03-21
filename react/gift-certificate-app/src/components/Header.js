import {Button, FormControl, Nav, Navbar} from "react-bootstrap";
import {getUserLogin, isRoleAdmin, removeUserData} from './UtilUserData';
import {useNavigate, useSearchParams} from "react-router-dom";
import React, {useState} from "react";
import {CertEditModel} from "./UtilModal";
import {Form} from "antd";
import Input from "antd/es/input/Input";

const certName = 'certName';
const tagName = 'tagName';

function Header(props) {

    let headerName = 'UI';
    let buttonLogin = 'Login';
    let buttonMain = 'Main';
    let userLogin = '';

    const [inputs, setInputs] = useState({});
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();

    let loadSearchParam = new URLSearchParams(window.location.search);
    let tagsUrl = loadSearchParam?.get(tagName) ? loadSearchParam.get(tagName).trim().split(',').map(value => '#(' + value + ')') : '';
    let searchValue = (loadSearchParam?.get(certName) ? loadSearchParam.get(certName) : '') + " " + tagsUrl;
    searchValue = searchValue.replace(',', " ");
    const [search, setSearch] = useState({searchValue});

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
        setSearch({searchValue: ''});
    }

    const handleSubmit = (event) => {
        let check = {};
        const tags = [];
        const searchData = {};
        const searchLine = search.searchValue !== '' ? search.searchValue.trim() : inputs.search;

        if (searchLine) {
            searchLine.split('#').map(element => {
                element = element.trim();
                check = element.match('^\\((\\w|\\d| )+\\)');
                check
                    ? tags.push(check[0].match('(\\w|\\d| )+')[0])
                    : (searchData.certName = element) && (searchData.certDesc = element);
            })
            searchData.tagName = tags;
            setSearchParams(new URLSearchParams(searchData));
            /*navigate(window.location.pathname + "?" + new URLSearchParams(searchData));*/
            window.location.reload();
        } else {
            setSearchParams({});
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
                    {props.isAdmin ? <a><CertEditModel/></a> : ''}
                </Navbar.Text>

                {!window.location.pathname.includes('login') ?
                    <Form layout='inline' style={{width: '40%'}} onSubmit={handleSubmit}>
                        <FormControl onChange={handleChange}
                                     size="sm" type='text' placeholder='Search'
                                     className='mr-sm-1 header-btn' style={{maxWidth: '60%'}}
                                     name="search" value={inputs.search || search.searchValue}
                        />
                        <Button onClick={handleSubmit} type="submit" size="sm" variant='outline-info'>Search</Button>
                    </Form>
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