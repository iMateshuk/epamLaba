export const setUserData = (userData) => {
    sessionStorage.setItem('userData', JSON.stringify(userData));
}

export const removeUserData = () => {
    sessionStorage.removeItem('userData');
}

export const getUserLogin = () => {
    return getUserData()?.login
}

export const getUserToken = () => {
    return getUserData()?.token
}

export const getUserRoles = () => {
    return getUserData()?.roles
}

export const checkUserRole = (role) => {
    return getUserRoles()?.indexOf(role) > -1;
}

export const isRoleAdmin = () => {
    return checkUserRole("ROLE_ADMIN");
}

const getUserData = () => {
    const userDataString = sessionStorage.getItem('userData');
    return JSON.parse(userDataString);
}
