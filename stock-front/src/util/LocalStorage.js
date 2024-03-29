import LocalStorageParams from './LocalStrorageParams';

const hasRole = (role) => {
    const roles = JSON.parse(localStorage.getItem(LocalStorageParams.ROLES));
    return roles.find(r => r === role);
};

const getEmail = () => {
    return localStorage.getItem(LocalStorageParams.EMAIL);
};

const getCompanyName = () => {
    return localStorage.getItem(LocalStorageParams.COMPANY_NAME);
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
    hasRole: hasRole,
    getEmail: getEmail,
    getCompanyName: getCompanyName
}