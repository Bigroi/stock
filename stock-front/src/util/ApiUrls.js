const urls = {
    //authentication
    AUTHENTICATE: 'public/authenticate',
    REGISTRATION: 'public/registration',
    REFRESH: 'public/refresh-token',
    RESET: 'public/password-reset',

    //public
    LABELS: 'public/label/{{lng}}',
    FEED_BACK: 'public/feedback',
    PRODUCT_LIST: 'public/products/categories',

    //user
    ACCOUNT_MANAGEMENT: 'user/account',

    LOT_LIST: 'user/lots',
    LOT:'user/lot/{id}',
    LOT_ACTIVATE: 'user/lot/{id}/activate',
    LOT_DEACTIVATE: 'user/lot/{id}/deactivate',

    TENDER_LIST: 'user/tenders',
    TENDER: 'user/tender/{id}',
    TENDER_ACTIVATE: 'user/tender/{id}/activate',
    TENDER_DEACTIVATE: 'user/tender/{id}/deactivate',

    ADDRESS_LIST: 'user/addresses'
};

export default urls;