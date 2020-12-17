import React from 'react';
import {withTranslation} from 'react-i18next';
import Button from '../../components/input/Button';
import Form from '../../components/form/Form';
import LocalStorage from "../../util/LocalStorage";
import FeedBackForm from "../../forms/feed-back/FeedBackForm";
import AccountForm from "../../forms/account/AccountForm";

class UserMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            activeTab: this.TAB.PRODUCTS,
            feedBackFormActive: false,
            accountPopupActive: false,
            accountFormActive: false
        }
    }

    TAB = {
        PRODUCTS: 'products',
        LOTS: 'lots',
        TENDERS: 'tenders',
        DEALS: 'deals',
        PRODUCTS_ADMIN: 'productsAdmin',
        COMPANY: 'company',
        TESTS: 'tests',
        LABELS: 'labels'
    };

    setActiveTab = (e, tab) => {
        e.preventDefault();
        this.setState({activeTab: tab});
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
            <li className={this.state.activeTab === this.TAB.PRODUCTS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}>
                    {t('label.navigation.products')}
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.LOTS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.LOTS)}>
                    {t('label.navigation.lots')}
                    <spna id='lot-alerts' style={{display: 'none'}} className='bid-alert'/>
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.TENDERS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.TENDERS)}>
                    {t('label.navigation.tenders')}
                    <spna id='tender-alerts' style={{display: 'none'}} className='bid-alert'/>
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.DEALS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.DEALS)}>
                    {t('label.navigation.deals')}
                    <spna id='deal-alerts' style={{display: 'none'}} className='deal-alert'/>
                    <spna id='deal-on-approve' style={{display: 'none'}} className='deal-on-approve'/>
                </a>
            </li>
        </React.Fragment>
    };

    getAdminTabs = () => {
        if (LocalStorage.hasRole("ADMIN")) {
            const {t} = this.props;
            return <React.Fragment>
                <li className={this.state.activeTab === this.TAB.PRODUCTS_ADMIN ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS_ADMIN)}>
                        {t('label.navigation.productsExt')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.TAB.COMPANY ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.COMPANY)}>
                        {t('label.navigation.company')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.TAB.TESTS ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.TESTS)}>
                        {t('label.navigation.testBG')}
                    </a>
                </li>
                <li className={this.state.activeTab === this.TAB.LABELS ? 'active' : ''}>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.LABELS)}>
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
                <AccountForm/>
            </Form>
        }
    };

    getLogo = () => {
        return <div className='logo-pages'>
            <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}>
                <img className='big-logo' src='/img/logo-pages.png' alt='YourTrader' title='YourTrader'/>
            </a>
            <div className=' small-logo'>
                <div className='burger-close'/>
                <div className='logo-mobile'>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}/>
                </div>
            </div>
        </div>
    };

    getMobileLogo = () => {
        return <div className='burger-logo'>
            <div className='burger'/>
            <div className='logo-mobile'>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}/>
            </div>
        </div>
    };

    getTabTitle = () => {
        const {t} = this.props;
        switch (this.state.activeTab) {
            case this.TAB.PRODUCTS:
                return t('label.pageNames.products');
            case this.TAB.LOTS:
                return t('label.pageNames.myLots');
            case this.TAB.TENDERS:
                return t('label.pageNames.myTenders');
            case this.TAB.DEALS:
                return t('label.pageNames.myDeals');
            case this.TAB.PRODUCTS_ADMIN:
                return t('label.pageNames.productsForAdmin');
            case this.TAB.COMPANY:
                return t('label.pageNames.companies');
            case this.TAB.TESTS:
                return t('label.pageNames.testBG');
            case this.TAB.LABELS:
                return t('label.pageNames.lables');
            default:
                return '';
        }
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
                           this.setState({accountFormActive: true});
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

    getTabContent = () => {
        return 'test';
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
        return <body className='body-pages'>
        {this.getFeedBackForm()}
        {this.getAccountForm()}
        <div className='aside'>
            {this.getLogo()}
            {this.getMenu()}
            {this.getAsideFooter()}
        </div>
        <div className='aside-modile'>
            {this.getMobileLogo()}
            {this.getAccountButton()}
        </div>
        <div className='bgdark'/>
        <div className='section'>
            <div className='table-header'>
                <h1>{this.getTabTitle()}</h1>
                <div className='table-header-button thb'>
                    {this.getAccountButton()}
                </div>
            </div>
            <div className='content'>
                {this.getTabContent()}
            </div>
        </div>

        </body>
    }

}

export default withTranslation()(UserMain);