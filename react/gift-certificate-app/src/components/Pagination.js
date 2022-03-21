import {message, Pagination} from "antd";
import React from "react";
import {useSearchParams} from 'react-router-dom';

const pageData = {};
const sortDate = 'sortDate';
const sortName = 'sortName';
const pageNumber = 'pageNumber';
const pageSize = 'pageSize';

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
        setCertSearchData(searchData);
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