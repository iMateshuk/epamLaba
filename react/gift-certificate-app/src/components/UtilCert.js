import {getUserToken} from './UtilUserData'

const certURL = `/gift-certificate-app/certificates`;
const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
}

export const deleteCert = async (id) => {
    await fetch(certURL + `/${id}`, {
        method: 'DELETE',
        headers: {
            headers,
            'Authorization': `Bearer ${getUserToken()}`
        }
    });
}

export const getCerts = async () => {

    return await fetch(certURL, {
        method: 'GET',
        headers: {headers}
    })
        .then(async response => response.json())
}

export const getCert = async (id) => {

    return await fetch(certURL + `/${id}`, {
        method: 'GET',
        headers: {headers}
    })
        .then(async response => response.json())
}
