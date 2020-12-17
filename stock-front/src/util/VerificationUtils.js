const verificationUtils = {
    checkEmail: (email, t) => {
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (email && !re.test(email.toLowerCase())) {
            return t('enter_valid_email');
        } else {
            const domainPart = email.substr(email.indexOf("@") + 1);
            const localPart = email.substr(0, email.indexOf("@"));
            if (domainPart.length > 255 || localPart.length > 64) {
                return t('enter_valid_email');
            }
        }
    },
    checkUrl: (value, t) => {
        const re = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;
        if (value !== '' && (!re.test(value.toLowerCase()) || value.length > 2048)) {
            return t('enter_valid_url');
        }
    }
};

export default verificationUtils;