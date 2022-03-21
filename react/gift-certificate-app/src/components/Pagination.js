import {message, Pagination} from "antd";
import React from "react";
import {useSearchParams} from 'react-router-dom';

const pageData = {};
const sortDate = 'sortDate';
const sortName = 'sortName';
const pageNumber = 'pageNumber';
const pageSize = 'pageSize';
const certName = 'certName';
const certDesc = 'certDesc';
const tagName = 'tagName';

export const setCertSearchData = (certPageSearchData) => {
    let searchData = new URLSearchParams(certPageSearchData);

    let check = searchData.get(sortDate);
    if (check || check === '') {
        pageData.sortDate = searchData.get(sortDate);
    }
    check = searchData.get(sortName);
    if (check || check === '') {
        pageData.sortName = searchData.get(sortName);
    }
    check = searchData.get(pageNumber);
    if (check) {
        pageData.pageNumber = searchData.get(pageNumber);
    }
    check = searchData.get(pageSize);
    if (check) {
        pageData.pageSize = searchData.get(pageSize);
    }
    check = searchData.get(certName);
    if (check) {
        pageData.certName = searchData.get(certName);
    }
    check = searchData.get(certDesc);
    if (check) {
        pageData.certDesc = searchData.get(certDesc);
    }
    check = searchData.get(tagName);
    if (check) {
        pageData.tagName = searchData.get(tagName);
    }
    sessionStorage.setItem('certPageData', JSON.stringify(pageData));
}

export const removeCertSearchData = () => {
    sessionStorage.removeItem('certPageData');
}

export const getCertSearchData = () => {
    const certPageDataString = sessionStorage.getItem('certPageData');
    return JSON.parse(certPageDataString);
}

export const CreatePagination = (props) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const pageSize = props.pageSize;

    let searchData = getCertSearchData();
    searchData = searchData ? searchData : {};

    const handlePageClick = (page, pageSize) => {
        searchData.pageNumber = page;
        searchData.pageSize = pageSize;
        setSearchParams(searchData);
        window.location.reload();
    }

    return (
        <div className='pagination'>
            <Pagination className='pag'
                        onChange={handlePageClick}
                        current={props.pageNumber}
                        total={props.totalElements}
                        defaultPageSize={props.pageSize}
                        pageSize={pageSize < 20 ? 10 : (props.pageSize > 20 ? 50 : 20)}
                        pageSizeOptions={[10, 20, 50]}
            />
        </div>
    )
}