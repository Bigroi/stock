import React from 'react';
import {withTranslation} from 'react-i18next';
import Button from '../../components/input/Button';
import Form from '../../components/form/Form';
import LocalStorage from "../../util/LocalStorage";
import FeedBackForm from "../../forms/feed-back/FeedBackForm";
import AccountForm from "../../forms/account/AccountForm";
import Lots from "./tab/Lots";
import Tenders from "./tab/Tenders";

class UserMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            activeTab: this.TAB.PRODUCTS,
            feedBackFormActive: false,
            accountPopupActive: false,
            accountFormActive: false,
            showEmptyForm: false,
            mobileMenuActivated: false
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
            <li className={this.state.activeTab === this.TAB.PRODUCTS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}>
                    {t('label.navigation.products')}
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.LOTS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.LOTS)}>
                    {t('label.navigation.lots')}
                    <span id='lot-alerts' style={{display: 'none'}} className='bid-alert'/>
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.TENDERS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.TENDERS)}>
                    {t('label.navigation.tenders')}
                    <span id='tender-alerts' style={{display: 'none'}} className='bid-alert'/>
                </a>
            </li>
            <li className={this.state.activeTab === this.TAB.DEALS ? 'active' : ''}>
                <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.DEALS)}>
                    {t('label.navigation.deals')}
                    <span id='deal-alerts' style={{display: 'none'}} className='deal-alert'/>
                    <span id='deal-on-approve' style={{display: 'none'}} className='deal-on-approve'/>
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
                <div className='burger-close' onClick={() => this.setState({mobileMenuActivated: false})}/>
                <div className='logo-mobile'>
                    <a href='#' onClick={(e) => this.setActiveTab(e, this.TAB.PRODUCTS)}/>
                </div>
            </div>
        </div>
    };

    getMobileLogo = () => {
        return <div className='burger-logo'>
            <div className='burger' onClick={() => this.setState({mobileMenuActivated: true})}/>
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
        switch (this.state.activeTab) {
            case this.TAB.LOTS:
                return <Lots
                    showEmptyForm={this.state.showEmptyForm}
                    onCloseForm={() => this.setState({showEmptyForm: false})}
                />;
            case this.TAB.TENDERS:
                return <Tenders
                    showEmptyForm={this.state.showEmptyForm}
                    onCloseForm={() => this.setState({showEmptyForm: false})}
                />;
            default:
                return 'test';
        }
    };

    getAddButton = () => {
        const {t} = this.props;
        switch (this.state.activeTab) {
            case this.TAB.LABELS:
            case this.TAB.PRODUCTS_ADMIN:
            case this.TAB.TENDERS:
            case this.TAB.LOTS:
                return <div className="add-button" onClick={() => this.setState({showEmptyForm: true})}>
                    <div className="plus"/>
                    <p>{t('label.button.create')}</p>
                </div>;
            default:
                return '';
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
        const addButton = this.getAddButton();
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
                    <h1>{this.getTabTitle()}</h1>
                    <div className={`table-header-button ${addButton === '' ? 'thb' : ''}`}>
                        {addButton}
                        {this.getAccountButton()}
                    </div>
                </div>
                <div className='content'>
                    {this.getTabContent()}
                </div>
            </div>

        </React.Fragment>
    }

}

export default withTranslation()(UserMain);