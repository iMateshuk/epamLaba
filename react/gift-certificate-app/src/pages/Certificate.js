import React, {Component} from 'react';
import {message} from "antd";
import Header from '../components/Header';
import {isRoleAdmin} from '../components/UtilUserData'
import {getCerts} from '../components/UtilCert'
import {CreatePagination, getCertSearchData, removeCertSearchData, setCertSearchData} from '../components/Pagination'
import Footer from "../components/Footer";
import {CreateTable} from "../components/CertTable";

export default class Certificates extends Component {
    constructor(props) {
        super(props);
        this.state = {allCertData: {}, certificates: []};
    }

    componentDidMount() {
        window.location.search.trim() !== ''
            ? removeCertSearchData() || setCertSearchData(window.location.search)
            : setCertSearchData({
                sortDate: 'DESC',
                pageNumber: 1,
                pageSize: 20
            });
        getCerts().then(data => {
                if (data?.errorMessage) {
                    message.error({content: data.errorMessage, style: {marginTop: '30vh',}}, 3)
                } else {
                    this.setState({allCertData: data, certificates: data?.list});
                }
            }
        )
    }

    render() {
        const isAdmin = isRoleAdmin();
        return (
            <>
                <Header isAdmin={isAdmin} error={this.state.error}/>
                <CreateTable certs={this.state.certificates}/>
                <CreatePagination
                    pages={this.state.allCertData._links} pageNumber={this.state.allCertData.pageNumber}
                    lastPage={this.state.allCertData.lastPage} pageSize={this.state.allCertData.pageSize}
                    totalElements={this.state.allCertData.totalElements}
                />
                <Footer/>
            </>
        );
    }
}