import React, {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';

export const CertViewModel = (props) => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Button variant="primary" onClick={handleShow}> View </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Certificate id={props.cert.key} </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <table>
                        <tbody>
                        <tr key={props.cert.key}>
                            <td>
                                <div key={props.cert.name}>Name: {props.cert.name}</div>
                                <div key={props.cert.description}>Description: {props.cert.description}</div>
                                <div key={props.cert.price}>Price: {props.cert.price}</div>
                                <div key={props.cert.duration}>Duration: {props.cert.duration}</div>
                                <div key={props.cert.key + 1}>Created Date: {props.cert.createdDate}</div>
                                <div key={props.cert.modifiedDate}>Modified Date: {props.cert.modifiedDate}</div>
                                <div key={props.cert.key + 2}>Tags: {props.cert.tags.map(tag => <div
                                    key={tag.name}>{tag.name}</div>)}</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}> Close </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export const CertEditModel = (props) => {

    const [show, setShow] = useState(false);
    const [inputs, setInputs] = useState({});

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;

        setInputs(values => ({...values, [name]: value}));
    }

    const fillInputs = () => {
        props?.cert?.map(field => inputs[field.name] = field.value);
        console.log(inputs);
    }

    return (
        <>
            {props?.cert?.key ? () => fillInputs : ''}
            {props?.cert?.key
                ? <Button variant="warning" onClick={handleShow}> Edit </Button>
                : <Button variant="outline-success" size="sm" onClick={handleShow}> Add new </Button>
            }

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title> {props?.cert?.key ? 'Certificate edit id=' + props.cert.key : 'Certificate add'} </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <table>
                        <tbody>
                        <tr key={props?.cert?.key}>
                            <td>
                                <div key={props?.cert?.name}>Name:
                                    <input
                                        type="text"
                                        name="name"
                                        required minLength={3} maxLength={30}
                                        value={props?.cert?.name || inputs.name}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div key={props?.cert?.description}>Description: {props?.cert?.description}</div>
                                <div key={props?.cert?.price}>Price: {props?.cert?.price}</div>
                                <div key={props?.cert?.duration}>Duration: {props?.cert?.duration}</div>
                                <div key={props?.cert?.key + 1}>Tags: {props?.cert?.tags.map(tag => <div
                                    key={tag.name}>{tag.name}</div>)}</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="success" onClick={() => {
                        props.onClick();
                        handleClose()
                    }} type="submit"> Save </Button>
                    <Button variant="secondary" onClick={handleClose}> Cancel </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export const CertDeleteModel = (props) => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Button variant="danger" onClick={handleShow}> Delete </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete confirmation</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Do you really want delete Certificate with id = {props.cert.key}?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={() => {
                        props.onClick();
                        handleClose()
                    }} type="submit"> Yes </Button>
                    <Button variant="secondary" onClick={handleClose}> Cancel </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}