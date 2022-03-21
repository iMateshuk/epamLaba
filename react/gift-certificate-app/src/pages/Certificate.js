import React, {Component} from 'react';
import {message, Space, Tag, Table as AntTable} from "antd";
import Header from '../components/Header';
import {isRoleAdmin} from '../components/UtilUserData'
import {deleteCert, getCerts} from '../components/UtilCert'
import {CreatePagination, getCertSearchData, removeCertSearchData, setCertSearchData} from '../components/Pagination'
import Footer from "../components/Footer";
import {CreateTable} from "../components/CertTable";
import {useNavigate, useSearchParams} from "react-router-dom";
import {CertDeleteModel, CertViewModel} from "../components/UtilModal";

export default function Certificate(props) {
    let navigate = useNavigate();
    return <Certificates {...props} navigate={navigate}/>
}

class Certificates extends Component {
    constructor(props) {
        super(props);
        this.state = {allCertData: {}, certificates: [], tablePage: 1};
        this.remove = this.remove.bind(this);
        this.handleTableClick = this.handleTableClick.bind(this);
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

    remove(id) {
        deleteCert(id).then(data => {
            if (data?.errorMessage) {
                message.error({content: data.errorMessage}, 3);
            } else {
                message.success({content: 'Delete OK: ' + data.message}, 3)
                let updatedCertificates = [...this.state.certificates].filter(i => i.id !== id);
                this.setState({certificates: updatedCertificates});
            }
        });
    }

    handleTableClick(pagination, filters, sorter, extra) {
        if (this.state.tablePage !== pagination.current) {
            this.setState({tablePage: pagination.current})
        } else {
            const searchData = getCertSearchData();
            if (sorter.columnKey === "modifiedDate") {
                searchData.sortDate = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
            }
            if (sorter.columnKey === "name") {
                searchData.sortName = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
            }
            this.props.navigate(window.location.pathname + "?" + new URLSearchParams(searchData));
            window.location.reload();
        }
    }

    render() {
        const certificates = this.state.certificates;

        const isAdmin = isRoleAdmin();
        let searchData = getCertSearchData();

        const columns = [
            {
                title: 'Name',
                dataIndex: 'name',
                key: 'name',
                defaultSortOrder: searchData?.sortName === 'ASC' ? "ascend" : searchData?.sortName === 'DESC' ? "descend" : '',
                sorter: () => {
                }
                /*sorter: (a, b) => {
                    let A = a.name.toUpperCase();
                    let B = b.name.toUpperCase();
                    return (A < B) ? -1 : (A > B) ? 1 : 0;
                }*/,
            },
            {
                title: 'Description',
                dataIndex: 'description',
                key: 'description',
            },
            {
                title: 'Price',
                dataIndex: 'price',
                key: 'price',
            },
            {
                title: 'Duration',
                dataIndex: 'duration',
                key: 'duration',
            },
            {
                title: 'Created Date',
                dataIndex: 'createdDate',
                key: 'createdDate',
            },
            {
                title: 'Modified Date',
                dataIndex: 'modifiedDate',
                key: 'modifiedDate',
                defaultSortOrder: searchData?.sortDate === 'ASC' ? "ascend" : searchData?.sortDate === 'DESC' ? "descend" : '',
                sorter: () => {
                }
            },
            {
                title: 'Tags',
                key: 'tags',
                dataIndex: 'tags',
                render: tags => (
                    <>
                        {tags.map(tag => {
                            return (
                                <Tag color={'geekblue'} key={tag}>
                                    {tag.name}
                                </Tag>
                            );
                        })}
                    </>
                ),
            },
            {
                title: 'Action',
                key: 'action',
                render: (text, cert) => (
                    <Space size="middle">
                        <a><CertViewModel cert={cert}/></a>
                        {isAdmin ? <a>Edit</a> : ''}
                        {isAdmin ? <a><CertDeleteModel cert={cert} onClick={() => this.remove(cert.key)}/></a> : ''}
                    </Space>
                ),
            },
        ];

        const dataSource = certificates?.map(cert => {
            return {
                key: cert.id,
                name: cert.name,
                description: cert.description,
                price: cert.price,
                duration: cert.duration,
                createdDate: cert.createdDate,
                modifiedDate: cert.modifiedDate,
                tags: cert.tags,
                action: cert
            }
        });

        return (
            <>
                <Header isAdmin={isAdmin} error={this.state.error}/>
                <AntTable dataSource={dataSource} columns={columns} onChange={this.handleTableClick}/>;
                {/*<CreateTable certs={this.state.certificates}/>*/}
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