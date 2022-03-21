import {message, Space, Table, Tag} from "antd";
import {CertDeleteModel, CertViewModel} from "./UtilModal";
import React, {useState} from "react";
import {getCertSearchData, setCertSearchData} from "./Pagination";
import {useNavigate, useSearchParams} from "react-router-dom";
import {isRoleAdmin} from "./UtilUserData";
import {deleteCert} from "./UtilCert";

export const CreateTable = (props) => {

    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    const isAdmin = isRoleAdmin();

    const remove = (id) => {
        deleteCert(id).then(data => {
            if (data?.errorMessage) {
                message.error({content: data.errorMessage}, 3);
            } else {
                message.success({content: 'Delete OK: ' + data.message}, 3)
                props.certs= [...props.certs].filter(i => i.id !== id);
            }

        });
    }

    const handleTableClick = (pagination, filters, sorter, extra) => {

        const searchData = getCertSearchData();
        if (sorter.columnKey === "modifiedDate") {
            searchData.sortDate = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
        }
        if (sorter.columnKey === "name") {
            searchData.sortName = sorter.order === "ascend" ? 'ASC' : sorter.order === "descend" ? 'DESC' : '';
        }
        setCertSearchData(searchData);
        setSearchParams(searchData);
        /*navigate(window.location.pathname + "?" + new URLSearchParams(searchData));*/
        /*window.location.reload();*/
        /*console.log('params', pagination, filters, sorter, extra);*/
    }

    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
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
            defaultSortOrder: 'descend',
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
                    {isAdmin ? <a><CertDeleteModel cert={cert} onClick={() => remove(cert.key)}/></a> : ''}
                </Space>
            ),
        },
    ];

    const dataSource = props?.certs?.map(cert => {
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
            <Table dataSource={dataSource} columns={columns} onChange={handleTableClick}/>;
        </>
    );
}