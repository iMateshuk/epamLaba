import {message, Space, Table, Tag} from "antd";
import {CertDeleteModel, CertEditModel, CertViewModel} from "./UtilModal";
import React, {useState} from "react";
import {getCertSearchData} from "./Pagination";
import {useNavigate} from "react-router-dom";
import {isRoleAdmin} from "./UtilUserData";
import {deleteCert} from "./UtilCert";

const sortName = 'sortName';
const sortDate = 'sortDate';

const urlMain = "/gift-certificate-app";

export const CreateTable = (props) => {

    const [certs, setCerts] = useState({...props});
    const [table, setTable] = useState({page: 1});
    const navigate = useNavigate();
    const isAdmin = isRoleAdmin();
    let searchUrl = new URLSearchParams(window.location.search)

    React.useEffect(() => {
        setCerts(props);
    }, [props])

    const remove = (id) => {
        deleteCert(id).then(data => {
            if (data?.errorMessage) {
                message.error({content: data.errorMessage}, 3);
            } else {
                message.success({content: 'Delete OK: ' + data.message}, 3)
                let updatedCertificates = [...certs.certs].filter(i => i.id !== id);
                setCerts({certs: updatedCertificates});
            }
        });
    }

    const handleTableClick = (pagination, filters, sorter, extra) => {
        if (table.page !== pagination.current) {
            setTable({page: pagination.current});
            return;
        }
        const searchData = getCertSearchData();
        if (sorter.columnKey === "modifiedDate") {
            searchData.sortDate = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
        }
        if (sorter.columnKey === "name") {
            searchData.sortName = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
        }
        navigate(urlMain + "?" + new URLSearchParams(searchData));
        window.location.reload();
    }

    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            defaultSortOrder: searchUrl.get(sortName) === 'ASC' ? "ascend" : searchUrl.get(sortName) === 'DESC' ? "descend" : "",
            sorter: () => {
            },
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
            defaultSortOrder: searchUrl.get(sortDate) === 'ASC' ? "ascend" : searchUrl.get(sortDate) === 'DESC' ? "descend" : "",
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
                    {isAdmin ? <a><CertEditModel cert={cert}/></a> : ''}
                    {isAdmin ? <a><CertDeleteModel cert={cert} onClick={() => remove(cert.key)}/></a> : ''}
                </Space>
            ),
        },
    ];

    const dataSource = certs.certs?.map(cert => {
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
            <Table dataSource={dataSource} columns={columns} onChange={handleTableClick} scroll={{ y: 715 }}/>;
        </>
    );
}