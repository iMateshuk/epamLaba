import {getUserToken} from './UtilUserData'
import {getCertSearchData} from './Pagination'

const certURL = '/gift-certificate-app/certificates';

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
}

export const addOrEditCert = async (id, certData) => {
    let localUrl = certURL;
    let method = 'POST';
    if (id) {
        localUrl = localUrl + `/${id}`;
        method = 'PATCH';
    }
    headers.Authorization = `Bearer ${getUserToken()}`;

    return fetch(localUrl, {
        method: method,
        headers: headers,
        body: JSON.stringify(certData)
    }).then(async response => {
        let data = [];
        if (!response.ok) {
            data = await response.json();
            throw new Error(data?.errorMessage ? data.errorMessage : response.status);
        }
        data.message = id;
        return data;
    }).catch(error => {
        error.errorMessage = error.toString();
        return error;
    });
}

export const deleteCert = async (id) => {
    headers.Authorization = `Bearer ${getUserToken()}`;

    return await fetch(certURL + `/${id}`, {
            method: 'DELETE',
            headers: headers
        }
    ).then(async response => {
        let data = [];
        if (!response.ok) {
            data = await response.json();
            throw new Error(data?.errorMessage ? data.errorMessage : response.status);
        }
        data.message = id;
        return data;
    }).catch(error => {
        error.errorMessage = error.toString();
        return error;
    });
}

export const getCerts = async () => {
    let dataSearch = getCertSearchData();
    return await fetch(certURL + (dataSearch ? '?' + new URLSearchParams(dataSearch) : ''),
        {
            method: 'GET',
            headers: headers
        })
        .then(async response => {
            const data = await response.json();
            if (!response.ok) {
                throw new Error(data?.errorMessage ? data.errorMessage : response.status);
            }
            return data;
        })
        .catch(error => {
            error.errorMessage = error.toString();
            return error;
        });
}

export const getCert = async (id) => {
    return await fetch(certURL + `/${id}`, {
        method: 'GET',
        headers: headers
    })
        .then(async response => response.json())
}
