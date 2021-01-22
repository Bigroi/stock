import i18n from 'i18next';
import Config from './Config';
import ApiUrls from './ApiUrls'
import LocalStorageParams from './LocalStrorageParams';

const __querystring = require('querystring');

const __authHeader = () => {
    const token = localStorage.getItem(LocalStorageParams.ACCESS_TOKEN);

    if (token) {
        return {'Authorization': `Bearer ${token}`};
    } else {
        return {};
    }
};

const __requestOptions = (method, body) => {
    return {
        method: method,
        headers: {...__authHeader(), 'Content-Type': 'application/json'},
        body: typeof body === 'string' ? body : (body ? JSON.stringify(body) : null)
    };
};

const __queryUrl = (url, params) => {
    return `${Config.getConfig().apiUrl}/${url}?${__querystring.encode(params)}`;
};

const __authorisationRedirect = async (response, retryFunction) => {
    if (response.status === 401) {
        let refreshResponse = await fetch(
            __queryUrl(ApiUrls.REFRESH, null),
            __requestOptions('POST',
                {token: localStorage.getItem(LocalStorageParams.REFRESH_TOKEN)}
            ));
        if (refreshResponse.ok && retryFunction) {
            const text = await refreshResponse.text();
            __setAuthenticationParams(JSON.parse(text));
            return retryFunction();
        } else {
            window.logout();
            // eslint-disable-next-line no-throw-literal
            throw 'logout';
        }
    } else {
        return response;
    }
};

const __setAuthenticationParams = (json) => {
    console.log(json);
    localStorage.setItem(LocalStorageParams.REFRESH_TOKEN, json.refreshToken);
    localStorage.setItem(LocalStorageParams.ACCESS_TOKEN, json.accessToken);
    localStorage.setItem(LocalStorageParams.ROLES, JSON.stringify(json.roles));
    localStorage.setItem(LocalStorageParams.EMAIL, json.email);
    localStorage.setItem(LocalStorageParams.COMPANY_NAME, json.companyName);
    i18n.changeLanguage(json.language);
};

const Request = {

    setAuthenticationParams(json) {
        __setAuthenticationParams(json);
    },

    doPost: (url, params, data) => {
        const callFunction = (retryFunction) => {
            return fetch(
                __queryUrl(url, params),
                __requestOptions('POST', data)
            ).then(response => __authorisationRedirect(response, retryFunction));
        };
        return callFunction(callFunction);
    },

    download: (url, params, fileName) => {
        const callFunction = (retryFunction) => {
            return fetch(__queryUrl(url, params), __requestOptions('GET'))
                .then(response => __authorisationRedirect(response, retryFunction))
                .then(response => {
                    if (response.ok) {
                        response.blob().then(blob => {
                            const a = document.createElement('a');
                            a.href = window.URL.createObjectURL(blob);
                            a.download = fileName;
                            a.click();
                        });
                    }
                    return response;
                });
        };
        return callFunction(callFunction);
    },

    doPut: (url, params, data) => {
        const callFunction = (retryFunction) => {
            return fetch(
                __queryUrl(url, params),
                __requestOptions('PUT', data)
            ).then(response => __authorisationRedirect(response, retryFunction));
        };
        return callFunction(callFunction);
    },

    doDelete: (url, params) => {
        const callFunction = (retryFunction) => {
            return fetch(
                __queryUrl(url, params),
                __requestOptions('DELETE')
            ).then(response => __authorisationRedirect(response, retryFunction));
        };
        return callFunction(callFunction);
    },

    doGet: (url, params) => {
        const callFunction = (retryFunction) => {
            return fetch(
                __queryUrl(url, params),
                __requestOptions('GET')
            ).then(response => __authorisationRedirect(response, retryFunction));
        };
        return callFunction(callFunction);
    },
};


export default Request;