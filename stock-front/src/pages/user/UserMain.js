import React from 'react';
import {withTranslation} from 'react-i18next';
import Button from '../../components/input/Button';
import Form from '../../components/form/Form';
import LocalStorage from '../../util/LocalStorage';
import FeedBackForm from '../../forms/feed-back/FeedBackForm';
import AccountForm from '../../forms/account/AccountForm';
import Lots from './tab/Lots';
import Tenders from './tab/Tenders';
import Products from './tab/Products';
import Companies from './tab/Companies';
import Labels from './tab/Labels';
import Trade from './tab/Trade';
import ProductsStatistics from './tab/ProductsStatistics';
import Categories from './tab/Categories';
import ProductStatisticsDetails from './tab/ProductStatisticsDetails';
import Deals from './tab/Deals';
import DealDetails from './tab/DealDetails';
import Addresses from './tab/Addresses';
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';

class UserMain extends React.Component {

    constructor(props) {
        super(props);
        this.tabMap = this.getTabMap();
        this.state = {
            activeTab: this.tabMap.PRODUCTS,
            feedBackFormActive: false,
            accountPopupActive: false,
            accountFormActive: false,
            showEmptyForm: false,
            mobileMenuActivated: false,
            alerts: {
                lastUpdate: null,
                lotAlerts: 0,
                tenderAlerts: 0,
                dealOnApprove: 0,
                canceledDeal: 0
            }
        };
    }

    componentDidMount() {
        window.onmousemove = () => {
            if (this.state.alerts.lastUpdate < new Date(new Date().getTime() - 5 * 60000)) {
                Request.doGet(ApiUrls.ALERTS)
                    .then(async response => {
                        if (response.ok) {
                            this.setState({
                                alerts: {
                                    ...JSON.parse(await response.text()),
                                    lastUpdate: new Date()
                                }
                            });
                        }
                    })
            }
        };
    }

    componentWillUnmount() {
        window.onmousemove = null;
    }

    getAddButton = (visible) => {
        const {t} = this.props;
        if (visible) {
            return <div className='add-button' onClick={() => this.setState({showEmptyForm: true})}>
                <div className='plus'/>
                <p>{t('label.button.create')}</p>
            </div>;
        } else {
            return '';
        }
    };

    getTabMap = () => {
        const {t} = this.props;
        return {
            ADDRESSES: {
                id: 'addresses',
                getTitle: () => t('label.pageNames.myAddresses'),
                addButton: true,
                getContent: () =>
                    <Addresses
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                    />
            },
            PRODUCTS: {
                id: 'products',
                getTitle: () => t('label.pageNames.products'),
                addButton: false,
                getContent: () =>
                    <ProductsStatistics
                        onDetails={(pId =>
                                this.setState({activeTab: this.tabMap.PRODUCT_DETAILS, productId: pId})
                        )}
                    />
            },
            PRODUCT_DETAILS: {
                id: 'productDetails',
                getTitle: () => t('label.pageNames.tradeOffers'),
                addButton: false,
                getContent: () =>
                    <ProductStatisticsDetails
                        productId={this.state.productId}
                        onBack={() => this.setState({activeTab: this.tabMap.PRODUCTS})}
                    />
            },
            LOTS: {
                id: 'lots',
                getTitle: () => t('label.pageNames.myLots'),
                addButton: true,
                getContent: () =>
                    <Lots
                        resetAlerts={() => this.setState({alerts: {...this.state.alerts, lotAlerts: 0}})}
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                    />
            },
            TENDERS: {
                id: 'tenders',
                getTitle: () => t('label.pageNames.myTenders'),
                addButton: true,
                getContent: () =>
                    <Tenders
                        resetAlerts={() => this.setState({alerts: {...this.state.alerts, tenderAlerts: 0}})}
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                    />
            },
            DEALS: {
                id: 'deals',
                getTitle: () => t('label.pageNames.myDeals'),
                addButton: false,
                getContent: () =>
                    <Deals
                        resetAlerts={() => this.setState({alerts: {...this.state.alerts, canceledDeal: 0}})}
                        onDetails={(dealId => this.setState({activeTab: this.tabMap.DEAL_DETAILS, dealId: dealId}))}
                    />
            },
            DEAL_DETAILS: {
                id: 'dealDetails',
                getTitle: () => t('label.pageNames.deal'),
                addButton: false,
                getContent: () =>
                    <DealDetails
                        resetDealAlert={() => this.setState({
                            alerts:
                                {...this.state.alerts, dealOnApprove: this.state.alerts.dealOnApprove - 1}
                        })}
                        dealId={this.state.dealId}
                        onBack={() => this.setState({activeTab: this.tabMap.DEALS})}
                    />
            },
            PRODUCTS_ADMIN: {
                id: 'productsAdmin',
                getTitle: () => t('label.pageNames.productsForAdmin'),
                addButton: true,
                getContent: () =>
                    <Products
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                        onToDetails={(pId) =>
                            this.setState({activeTab: this.tabMap.PRODUCT_CATEGORIES_ADMIN, productId: pId})
                        }
                    />
            },
            PRODUCT_CATEGORIES_ADMIN: {
                id: 'productCategoriesAdmin',
                getTitle: () => t('label.pageNames.productsForAdmin'),
                addButton: true,
                getContent: () =>
                    <Categories
                        productId={this.state.productId}
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                    />
            },
            COMPANY: {
                id: 'company',
                getTitle: () => t('label.pageNames.companies'),
                addButton: false,
                getContent: () => <Companies/>
            },
            TESTS: {
                id: 'tests',
                getTitle: () => t('label.navigation.testBG'),
                addButton: false,
                getContent: () => <Trade/>
            },
            LABELS: {
                id: 'labels',
                getTitle: () => t('label.pageNames.lables'),
                addButton: true,
                getContent: () =>
                    <Labels
                        showEmptyForm={this.state.showEmptyForm}
                        onCloseForm={() => this.setState({showEmptyForm: false})}
                    />
            }
        }
    };

    setActiveTab = (e, tab) => {
        e.preventDefault();
        this.setState({
            activeTab: tab,
            mobileMenuActivated: false
        });
    };

    getMenu = () => {
        return <nav className='main-menu'>
            <ul>
                {this.getUserTabs()}
                {this.getAdminTabs()}
            </ul>
        </nav>
    };

    getUserTabs = () => {
        const {t} = this.props;
        return <React.Fragment>
            <li className={this.state.activeTab === this.tabMap.PRODUCTS
            || this.state.activeTab === this.tabMap.PRODUCT_DETAILS
                ? 'active'
                : ''
            }>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.PRODUCTS)}>
                    {t('label.navigation.products')}
                </a>
            </li>
            <li className={this.state.activeTab === this.tabMap.LOTS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.LOTS)}>
                    {t('label.navigation.lots')}
                    {this.state.alerts.lotAlerts
                        ? <span id='lot-alerts' className='bid-alert'>{this.state.alerts.lotAlerts}</span>
                        : ''
                    }
                </a>
            </li>
            <li className={this.state.activeTab === this.tabMap.TENDERS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.TENDERS)}>
                    {t('label.navigation.tenders')}
                    {this.state.alerts.tenderAlerts
                        ? <span id='tender-alerts' className='bid-alert'>{this.state.alerts.tenderAlerts}</span>
                        : ''
                    }
                </a>
            </li>
            <li className={this.state.activeTab === this.tabMap.DEALS
            || this.state.activeTab === this.tabMap.DEAL_DETAILS
                ? 'active'
                : ''
            }>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.DEALS)}>
                    {t('label.navigation.deals')}
                    {this.state.alerts.canceledDeal
                        ? <span id='deal-alerts' className='deal-alert'>{this.state.alerts.canceledDeal}</span>
                        : ''
                    }
                    {this.state.alerts.dealOnApprove
                        ? <span id='deal-on-approve' className='deal-on-approve'>
                            {this.state.alerts.dealOnApprove}</span>
                        : ''
                    }
                </a>
            </li>
        </React.Fragment>
    };

    getAdminTabs = () => {
        if (LocalStorage.hasRole('ADMIN')) {
            const {t} = this.props;
            return <React.Fragment>
                <li className={
                    this.state.activeTab === this.tabMap.PRODUCTS_ADMIN
                    || this.state.activeTab === this.tabMap.PRODUCT_CATEGORIES_ADMIN
                        ? 'active'
                        : ''
                }>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.PRODUCTS_ADMIN)}>
                        {t('label.navigation.productsExt')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.tabMap.COMPANY ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.COMPANY)}>
                        {t('label.navigation.company')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.tabMap.TESTS ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.TESTS)}>
                        {t('label.navigation.testBG')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.tabMap.LABELS ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.LABELS)}>
                        {t('label.navigation.labels')}
                    </a>
                </li>
            </React.Fragment>
        } else {
            return '';
        }
    };

    getFeedBackForm = () => {
        if (this.state.feedBackFormActive) {
            return <Form
                className='contact-us-dialogbox'
                onClose={() => this.setState({feedBackFormActive: false})}
            >
                <FeedBackForm/>
            </Form>
        }
    };

    getAccountForm = () => {
        if (this.state.accountFormActive) {
            return <Form
                map={true}
                className='account-dialogbox'
                onClose={() => this.setState({accountFormActive: false})}
            >
                <AccountForm
                    onAddress={() => this.setState({activeTab: this.tabMap.ADDRESSES, accountFormActive: false})}
                />
            </Form>
        }
    };

    getLogo = () => {
        return <div className='logo-pages'>
            <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.PRODUCTS)}>
                <img className='big-logo' src='/img/logo-pages.png' alt='YourTrader' title='YourTrader'/>
            </a>
            <div className=' small-logo'>
                <div className='burger-close' onClick={() => this.setState({mobileMenuActivated: false})}/>
                <div className='logo-mobile'>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.PRODUCTS)}/>
                </div>
            </div>
        </div>
    };

    getMobileLogo = () => {
        return <div className='burger-logo'>
            <div className='burger' onClick={() => this.setState({mobileMenuActivated: true})}/>
            <div className='logo-mobile'>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.tabMap.PRODUCTS)}/>
            </div>
        </div>
    };


    getAccountButton = () => {
        return <div className={`login-box ${this.state.accountPopupActive ? '' : 'login-box-shadow'}`}>
            <div
                className='login-button-page'
                onClick={() => this.setState({accountPopupActive: !this.state.accountPopupActive})}
            >
                <div>
                    <div>{LocalStorage.getCompanyName()}</div>
                    <div className='hor_bg'/>
                </div>
            </div>
            {this.getAccountPopup()}
        </div>
    };

    getAccountPopup = () => {
        const {t} = this.props;
        if (this.state.accountPopupActive) {
            return <div className='login-list'>
                <div>
                    <a href='#'
                       className='edit-account'
                       onClick={(e) => {
                           e.preventDefault();
                           this.setState({accountFormActive: true, accountPopupActive: false});
                       }}
                    >
                        {t('label.navigation.account')}
                    </a>
                    <a href='#'
                       onClick={(e) => {
                           e.preventDefault();
                           window.logout();
                       }}
                       id='session-start'>
                        {t('label.navigation.logout')}
                    </a>
                </div>
            </div>
        }
    };

    getAsideFooter = () => {
        const {t} = this.props;
        return <div className='aside-footer'>
            <p className='footer-phone'>
                <a href={`tel:${t('label.index.phone_prefix')}${t('label.index.phone')}`}>
                    {t('label.index.phone_prefix')}<span>{t('label.index.phone')}</span>
                </a>
            </p>
            <p className='footer-email'>
                <a href={`mailto:${t('label.index.email')}`}>{t('label.index.email')}</a>
            </p>
            <Button
                className='gray-button send-message'
                onClick={() => this.setState({feedBackFormActive: true})}
            >
                {t('label.account.contact_us')}
            </Button>
            <br/><br/>
        </div>
    };

    render() {
        const addButton = this.getAddButton(this.state.activeTab.addButton);
        return <React.Fragment>
            {this.getFeedBackForm()}
            {this.getAccountForm()}
            <div className={`aside ${this.state.mobileMenuActivated ? 'aside-adaptive' : ''}`}>
                {this.getLogo()}
                {this.getMenu()}
                {this.getAsideFooter()}
            </div>
            <div className='aside-modile'>
                {this.getMobileLogo()}
                {this.getAccountButton()}
            </div>
            {this.state.mobileMenuActivated ? <div className='bgdark'/> : ''}
            <div className='section'>
                <div className='table-header'>
                    <h1>{this.state.activeTab.getTitle()}</h1>
                    <div className={`table-header-button ${addButton === '' ? 'thb' : ''}`}>
                        {addButton}
                        {this.getAccountButton()}
                    </div>
                </div>
                <div className='content'>
                    {this.state.activeTab.getContent()}
                </div>
            </div>

        </React.Fragment>
    }

}

export default withTranslation()(UserMain);