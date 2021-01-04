import React from 'react';
import i18n from 'i18next';
import {languageTranslator} from '../util/i18n';
import Landing from './public/Landing';
import LocalStorageParams from '../util/LocalStrorageParams';
import Request from '../util/Request';
import UserMain from './user/UserMain';

export default class Main extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            authenticated: localStorage.getItem(LocalStorageParams.EMAIL) !== null
        };
        window.logout = () => {
            localStorage.clear();
            this.setState({authenticated: false});
        };
        i18n.on('languageChanged', lang => {
            const supportedLanguage = languageTranslator();
            if (supportedLanguage === lang) {
                this.setState({language: lang})
            } else {
                i18n.changeLanguage(supportedLanguage);
            }
        });
    }

    onLogin = (authParams) => {
        Request.setAuthenticationParams(authParams);
        this.setState({authenticated: true});
    };

    render() {
        if (this.state.language) {
            if (this.state.authenticated) {
                return <UserMain/>
            } else {
                return <Landing onLoginSuccess={this.onLogin}/>
            }
        } else {
            return '';
        }
    }
}