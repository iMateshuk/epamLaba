import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import { isRoleAdmin, getUserToken } from '../components/UtilUserData'

export default class Certificate extends Component {

    constructor(props) {
        super(props);
        this.state = { allCertData: {}, certificates: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch("/gift-certificate-app/certificates")
            .then(async response => response.json())
            .then(data => this.setState({ allCertData: data, certificates: data.list }));
    }

    async remove(id) {
        await fetch(`/gift-certificate-app/certificates/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getUserToken()}`
            }
        }).then(() => {
            let updatedCertificates = [...this.state.certificates].filter(i => i.id !== id);
            this.setState({ certificates: updatedCertificates });
        });
    }

    render() {
        const certificates = this.state.certificates;

        let isAdmin = false;
        if (isRoleAdmin()) {
            isAdmin = true;
        }

        const certificatesList = certificates.map(cert => {
            return <tr key={cert.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{cert.name}</td>
                <td>{cert.description}</td>
                <td>{cert.price}</td>
                <td>{cert.duration}</td>
                <td>{cert.createdDate}</td>
                <td>{cert.modifiedDate}</td>
                <td>{cert.tags.map(tag => <div>{tag.name}</div>)} </td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" variant="primary" tag={Link} to={"/gift-certificate-app/certificates/" + cert.id}>View</Button>
                        {isAdmin ?
                            <Button size="sm" variant="warning" href={"/gift-certificate-app/certificates/" + cert.id} >Edit</Button>
                            : ''
                        }
                        {isAdmin ?
                            <Button size="sm" variant="danger" onClick={() => this.remove(cert.id)}>Delete</Button>
                            : ''
                        }
                    </ButtonGroup>
                </td>
            </tr>
        });


        return (
            <div className='default'>
                <Header />
                <Container fluid>
                    {isAdmin ?
                        <div className="float-right">
                            <Button variant="primary" tag={Link} to="/gift-certificate-app/certificates/new">Add new</Button>
                        </div>
                        : ''
                    }

                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="12%">Name</th>
                                <th width="20%">Description</th>
                                <th width="7%">Price</th>
                                <th width="7%">Duration</th>
                                <th width="15%">Created Date</th>
                                <th width="15%">Modified Date</th>
                                <th width="10%">Tags</th>
                                <th width="15%">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {certificatesList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}