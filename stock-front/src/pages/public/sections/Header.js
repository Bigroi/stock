import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import i18n from 'i18next';
import LanguageSwitcher from '../../../components/language-switcher/LanguageSwitcher';
import Button from '../../../components/input/Button';

class Header extends React.Component {


    render() {
        const {t} = this.props;
        return <header>
            <div className='container'>
                <img className='logo' src='/img/logo.png' alt='YourTrader' title='YourTrader'/>
                <nav className='main-page-nav'>
                    <a href='#ex2'>{t('label.index.benefits')}</a>
                    <a href='#ex3'>{t('label.index.products')}</a>
                    <a href='#ex4'>{t('label.index.for_whom')}</a>
                    <a href='#ex5'>{t('label.index.try_it_now')}</a>
                </nav>
                <div className='buttons-login'>
                    <Button className='registration background-blue registration-button'
                            onClick={this.props.onRegistration}>
                        {t('label.navigation.regestration')}
                    </Button>
                    <Button className='sign-in login-button' onClick={this.props.onLogin}>
                        {t('label.navigation.login')}
                    </Button>
                </div>
                <LanguageSwitcher
                    menuClass='selectmenu-menu-landing'
                    onChange={lang => i18n.changeLanguage(lang)}
                    current={i18n.language}
                />
            </div>
        </header>;
    }
}

Header.propTypes = {
    onLogin: PropTypes.func.isRequired,
    onRegistration: PropTypes.func.isRequired
};

export default withTranslation()(Header);