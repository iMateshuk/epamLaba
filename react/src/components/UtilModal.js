import React, {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import {Form, message} from "antd";
import Input from "antd/es/input/Input";
import {WithContext as ReactTags} from 'react-tag-input';
import {addOrEditCert} from "./UtilCert";
import {useSearchParams} from "react-router-dom";
import Text from "antd/es/typography/Text";

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

const nameMinLen = 3;
const nameMaxLen = 30;
const descMinLen = 12;
const descMaxLen = 1000;
const tagMinLen = 3;
const tagMaxLen = 15;

const KeyCodes = {
    comma: 188,
    enter: 13
};
const delimiters = [KeyCodes.comma, KeyCodes.enter];

export const CertEditModel = (props) => {

    const errorTagLengthMin = 'length must be between min: ';
    const errorLengthMin = 'must be fill, and length between min: ';
    const errorLengthMax = ', max: ';
    const errorZero = 'must be great then 0';

    const [show, setShow] = useState(false);
    const [inputs, setInputs] = useState({});
    const [tags, setTags] = useState([]);
    const [searchParams, setSearchParams] = useSearchParams();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}));
    }

    const onFinish = () => {
        console.log(isNaN(inputs.price))
        const errors = {};
        if (inputs.name === '' || inputs.name.length < nameMinLen || inputs.name.length > nameMaxLen) {
            errors.name = errorLengthMin + nameMinLen + errorLengthMax + nameMaxLen;
        }
        if (inputs.description === '' || inputs.description.length < descMinLen || inputs.description.length > descMaxLen) {
            errors.description = errorLengthMin + descMinLen + errorLengthMax + descMaxLen;
        }
        if (inputs.price === '' || isNaN(inputs.price) || parseFloat(inputs.price.toString().replace(",", ".")) <= 0) {
            errors.price = errorZero;
        }
        if (inputs.duration === '' || isNaN(inputs.duration) || parseInt(inputs.duration, 10) < 0) {
            errors.duration = errorZero;
        }
        if (tags) {
            inputs.tags = tags.map(tag => {
                if (tag.name.length < tagMinLen || tag.name.length > tagMaxLen) {
                    errors.tag = errorTagLengthMin + nameMinLen + errorLengthMax + nameMaxLen;
                }
                return {name: tag.name}
            });
        }

        if (Object.keys(errors).length > 0) {
            Object.keys(errors).map((key) => message.error(key + ": " + errors[key], 3));
            return;
        }

        inputs.action = '';
        addOrEditCert(inputs.id > 0 ? inputs.id : undefined, inputs).then(data => {
            if (data?.errorMessage) {
                message.error({content: data.errorMessage}, 3);
                return;
            }
            setSearchParams(new URLSearchParams({sortDate: 'DESC'}))
            window.location.reload();
            message.success({content: 'OK: ' + data.message}, 3);
        });
    }
    // Tags
    let suggestions = [];
    const handleDelete = i => {
        setTags(tags.filter((tag, index) => index !== i));
    };
    const handleAddition = tag => {
        setTags([...tags, tag]);
    };
    const handleDrag = (tag, currPos, newPos) => {
        const newTags = tags.slice();
        newTags.splice(currPos, 1);
        newTags.splice(newPos, 0, tag);
        // re-render
        setTags(newTags);
    };
    const handleTagClick = index => {
        console.log('The tag at index ' + index + ' was clicked');
    };
    // EndTags
    const formItemLayout = {
        labelCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 5,
            },
        },
        wrapperCol: {
            xs: {
                span: 24,
            },
            sm: {
                span: 20,
            },
        },
    };

    React.useEffect(() => {
        if (props?.cert) {
            setInputs(props.cert);
            const id = props.cert.key ? props.cert.key : 0;
            setInputs(values => ({...values, ['id']: id}))
            suggestions = props.cert.tags.map(tag => {
                return {
                    id: tag.id.toString(),
                    name: tag.name
                };
            });
            setTags(suggestions);
        }
    }, [props])

    return (
        <>
            {props?.cert?.key
                ? <Button variant="warning" onClick={handleShow}> Edit </Button>
                : <Button variant="outline-success" size="sm" onClick={handleShow}> Add new </Button>
            }

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title> {props?.cert?.key ? 'Certificate edit id=' + props?.cert?.key : 'Certificate add'} </Modal.Title>
                </Modal.Header>
                <Modal.Body>

                    <Form fname="control-hooks" {...formItemLayout} onFinish={onFinish}>
                        <Form.Item name='Name' label='Name' initialValue={props?.cert?.name || inputs.name}
                                   rules={[{required: true, min: nameMinLen, max: nameMaxLen}]}>
                            <Input name="name" placeholder="Name" onChange={handleChange}/>
                        </Form.Item>
                        <Form.Item name='Description' label='Description'
                                   initialValue={props?.cert?.description || inputs.description}
                                   rules={[{required: true, min: descMinLen, max: descMaxLen}]}>
                            <Input.TextArea showCount maxLength={descMaxLen} name="description"
                                            placeholder="Description"
                                            onChange={handleChange}/>
                        </Form.Item>
                        <Form.Item name='Price' label='Price' initialValue={props?.cert?.price || inputs.price}
                                   rules={[{required: true}]}>
                            <Input name="price" placeholder="Price" onChange={handleChange}/>
                        </Form.Item>
                        <Form.Item name='Duration' label='Duration'
                                   initialValue={props?.cert?.duration || inputs.duration}
                                   rules={[{required: true}]}>
                            <Input name="duration" placeholder="Duration" onChange={handleChange}/>
                        </Form.Item>
                        <Form.Item name='Tags' label='Tags' rules={[{min: tagMinLen, max: tagMaxLen}]}>
                            <ReactTags
                                name="Tags"
                                maxLength={tagMaxLen}
                                minQueryLength={tagMinLen}
                                tags={tags}
                                labelField='name'
                                placeholder='Tag'
                                suggestions={suggestions}
                                delimiters={delimiters}
                                handleDelete={handleDelete}
                                handleAddition={handleAddition}
                                handleDrag={handleDrag}
                                handleTagClick={handleTagClick}
                                inputFieldPosition="top"
                                allowDeleteFromEmptyInput={false}
                                autocomplete
                            />
                        </Form.Item>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="success" onClick={() => {
                        handleClose();
                        onFinish()
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