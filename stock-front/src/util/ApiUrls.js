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

    ADDRESS_LIST: 'user/addresses',

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
    COMPANY_ADMIN_REVOKE: 'admin/company/{id}/revoke'
};

export default urls;