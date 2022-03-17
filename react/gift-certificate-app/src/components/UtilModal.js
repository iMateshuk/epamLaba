import {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import {deleteCert} from './UtilCert'


export const CertViewModel = (props) => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Button variant="primary" onClick={handleShow}> View </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Certificate id={props.cert.id} </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <tr>Name: {props.cert.name}</tr>
                    <tr>Description: {props.cert.description}</tr>
                    <tr>Price: {props.cert.price}</tr>
                    <tr>Duration: {props.cert.duration}</tr>
                    <tr>Created Date: {props.cert.createdDate}</tr>
                    <tr>Modified Date: {props.cert.modifiedDate}</tr>
                    <tr>Tags: {props.cert.tags.map(tag => <div>{tag.name}</div>)}</tr>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}> Close </Button>
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

            <Modal show={show} onHide={handleClose} backdrop="static" keyboard={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete confirmation </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Do you really want delete Certificate with id = {props.cert.id}?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={props.onClick} type="submit"> Yes </Button>
                    <Button variant="secondary" onClick={handleClose}> Cancel </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}