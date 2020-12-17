import React from 'react';
import {withTranslation} from 'react-i18next';
import Button from '../../components/input/Button';
import LocalStorage from '../../util/LocalStorage';
import Input from '../../components/input/Input';
import TextArea from '../../components/input/TextArea';
import VerificationUtils from '../../util/VerificationUtils';
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';
import Message, {TYPES} from '../../components/message/Message';
import LanguageSwitcher from "../../components/language-switcher/LanguageSwitcher";
import i18next from "i18next";

class AccountForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            account: {},
            error: {},
            message: null
        }
    }

    validateAccount = () => {
        const {t} = this.props;
        const error = {};
        let message = null;
        let errorsFound = false;
        if (this.state.account.username === '' || VerificationUtils.checkEmail(this.state.account.username, t)) {
            error.username = true;
            errorsFound = true;
        }
        if (this.state.account.phone === '') {
            error.phone = true;
            errorsFound = true;
        }
        if (this.state.account.password !== this.state.account.repeatPassword) {
            message = {
                type: TYPES.ERROR,
                value: t('label.account.error_password')
            };
            errorsFound = true;
        }
        if (errorsFound) {
            this.setState({error: error, message: message});
        }
        return !errorsFound
    };

    componentDidMount() {
        Request.doGet(ApiUrls.ACCOUNT_MANAGEMENT)
            .then(async response => {
                if (response.ok) {
                    const json = JSON.parse(await response.text());
                    this.setState({account: {...json, repeatPassword: ''}});
                    this.props.onAddressChanged({
                        latitude: json.latitude,
                        longitude: json.longitude
                    }, true)
                }
            })
    }

    submitAccount = () => {
        const {t} = this.props;
        if (this.validateAccount()) {
            const data = {
                username: this.state.account.username,
                phone: this.state.account.phone,
                language: this.state.account.language,
                password: this.state.account.password
            };
            Request.doPut(ApiUrls.ACCOUNT_MANAGEMENT, null, data)
                .then(async response => {
                    const text = await response.text();
                    if (response.ok) {
                        this.setState({
                            message: {
                                type: TYPES.SUCCESS,
                                value: t('label.account.edit_success')
                            }
                        });
                        Request.setAuthenticationParams(JSON.parse(text));
                    } else {
                        this.setState({
                            message: {
                                type: TYPES.ERROR,
                                value: t(text)
                            }
                        })
                    }
                })
        }
    };

    changeValue = (propertyName, value) => {
        this.setState({
            account: {
                ...this.state.account,
                [propertyName]: value
            },
            error: {
                ...this.state.error,
                [propertyName]: false
            }
        })
    };


    render() {
        const {t} = this.props;
        return <form className="form" name="form" id="account-form">
            <h3>{t('label.account.edit')}</h3>
            <div>
                <Message message={this.state.message}/>
            </div>
            <div className="flex-input">
                <Input
                    id='Login'
                    label={t('label.account.login')}
                    name='username'
                    error={this.state.error.username}
                    placeholder='example@mail.com'
                    value={this.state.account.username}
                    onChange={(newValue) => this.changeValue('username', newValue)}
                />
                <Input
                    id='Phone'
                    label={t('label.account.phone')}
                    name='phone'
                    error={this.state.error.phone}
                    placeholder={t('label.account.phone_placeholder')}
                    value={this.state.account.phone}
                    pattern={t('label.account.phone_pattern')}
                    onChange={(newValue) => this.changeValue('phone', newValue)}
                />
                <LanguageSwitcher
                    label={t('label.account.company_language')}
                    menuClass='selectmenu-menu-account'
                    current={this.state.account.language}
                    onChange={(newValue) => this.changeValue('language', newValue)}
                />
                <Input
                    id='CompanyName'
                    label={t('label.account.company_name')}
                    name='company_name'
                    value={this.state.account.companyName}
                    disabled={true}
                />
                <Input
                    id='RegNumber'
                    label={t('label.account.reg_number')}
                    name='regNumber'
                    value={this.state.account.regNumber}
                    disabled={true}
                />
                <Input
                    id='Password'
                    label={t('label.account.password')}
                    name='password'
                    type='password'
                    error={this.state.error.password}
                    placeholder='**************'
                    value={this.state.account.password}
                    maxLength={50}
                    onChange={(newValue) => this.changeValue('password', newValue)}
                />
                <Input
                    id='PasswordAgain'
                    label={t('label.account.repeat_password')}
                    name='password'
                    type='password'
                    error={this.state.error.repeatPassword}
                    placeholder='**************'
                    value={this.state.account.repeatPassword}
                    maxLength={50}
                    onChange={(newValue) => this.changeValue('repeatPassword', newValue)}
                />
                <div id="form-list">
                    <Button className="submit button">
                        {t('label.button.addAddress')}
                    </Button>
                    <Button className="submit button" onClick={this.submitAccount}>
                        {t('label.button.save')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(AccountForm);