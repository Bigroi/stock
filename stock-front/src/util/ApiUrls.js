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
    PRODUCTS_STATISTICS: 'public/statistics/products',
    PRODUCTS_STATISTICS_DETAILS: 'public/statistics/product/{id}/details',

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

    ADDRESS_LIST: 'user/addresses',

    DEAL_LIST: 'user/deals',
    DEAL: 'user/deal/{id}',
    DEAL_CHOICE: 'user/deal/{id}/choice/{choice}',
    DEAL_FEED_BACK: 'user/deal/{id}/comment',

    //admin
    PRODUCTS_ADMIN: 'admin/products',
    PRODUCT_ADMIN: 'admin/product',
    PRODUCT_ADMIN_ACTIVATE: 'admin/product/{id}/activate',
    PRODUCT_ADMIN_DEACTIVATE: 'admin/product/{id}/deactivate',

    PRODUCT_CATEGORIES_ADMIN: 'admin/product/{productId}/categories',
    PRODUCT_CATEGORY_ADMIN: 'admin/product/{productId}/category',
    PRODUCT_CATEGORY_ADMIN_ACTIVATE: 'admin/product/{productId}/category/{id}/activate',
    PRODUCT_CATEGORY_ADMIN_DEACTIVATE: 'admin/product/{productId}/category/{id}/deactivate',

    COMPANIES_ADMIN: 'admin/companies',
    COMPANY_ADMIN_ACTIVATE: 'admin/company/{id}/activate',
    COMPANY_ADMIN_REVOKE: 'admin/company/{id}/revoke',

    LABELS_ADMIN: 'admin/labels',
    LABEL_ADMIN: 'admin/label/{name}',

    TRADE_ADMIN_EXPIRATION_CHECK: 'admin/trade/expiration-check',
    TRADE_ADMIN_CLEAR_PREDEAL: 'admin/trade/clean-predeal',
    TRADE_ADMIN_RUN: 'admin/trade/run'
};

export default urls;