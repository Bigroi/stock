import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import Request from '../../util/Request';
import ApiUrls from '../../util/ApiUrls';
import Message, {TYPES} from '../../components/message/Message';
import i18next from 'i18next';
import CheckBox from '../../components/checkbox/CheckBox';
import Map from '../../components/map/Map';

/**
 * toLogin: function
 * onRegistrationSuccess: function
 * onAddressChanged: function
 */
class RegistrationForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userData: {
                username: '',
                password: '',
                passwordRepeat: '',
                companyName: '',
                phone: '',
                regNumber: '',
                city: '',
                country: '',
                address: '',
                latitude: 0,
                longitude: 0
            },
            errorFields: {
                username: false,
                password: false,
                passwordRepeat: false,
                companyName: false,
                phone: false,
                regNumber: false,
                city: false,
                country: false,
                address: false
            },
            agree: false,
            agreeError: false,
            step: 1,
            message: null
        }
    }

    register = () => {
        const {t} = this.props;
        if (this.validateEmptyFields() & this.validateAgree()) {
            const data = {...this.state.userData, language: i18next.language};
            Request.doPost(ApiUrls.REGISTRATION, null, data)
                .then(async response => {
                    const text = await response.text();
                    if (response.ok) {
                        const json = JSON.parse(text);
                        this.props.onRegistrationSuccess(json);
                    } else {
                        this.setState({
                            message: {
                                type: TYPES.ERROR,
                                value: t(text)
                            }
                        })
                    }
                });
        }
    };

    validateAgree = () => {
        this.setState({agreeError: !this.state.agree});
        return this.state.agree;
    };

    validateEmptyFields = () => {
        const errorFieldNames = [];
        if (this.state.step === 1) {
            if (this.state.userData.username === '') {
                errorFieldNames.push('username');
            }
            if (this.state.userData.password === '') {
                errorFieldNames.push('password');
            }
            if (this.state.userData.passwordRepeat === '') {
                errorFieldNames.push('passwordRepeat');
            }
            if (this.state.userData.companyName === '') {
                errorFieldNames.push('companyName');
            }
            if (this.state.userData.phone === '') {
                errorFieldNames.push('phone');
            }
            if (this.state.userData.regNumber === '') {
                errorFieldNames.push('regNumber');
            }
        } else {
            if (this.state.userData.city === '') {
                errorFieldNames.push('city');
            }
            if (this.state.userData.country === '') {
                errorFieldNames.push('country');
            }
            if (this.state.userData.address === '') {
                errorFieldNames.push('address');
            }
        }
        if (errorFieldNames.length === 0) {
            return true;
        } else {
            const errorFields = {};
            errorFieldNames.forEach(name => errorFields[name] = true);
            this.setState({errorFields: errorFields});
            return false;
        }
    };

    toStep2 = () => {
        const {t} = this.props;
        const noEmptyFields = this.validateEmptyFields();
        if (this.state.userData.password !== this.state.userData.passwordRepeat) {
            this.setState({
                message: {
                    type: TYPES.ERROR,
                    value: t('label.account.error_password')
                }
            });
        } else if (noEmptyFields) {
            this.setState({step: 2, message: null});
        }
    };

    setFieldValue = (fieldName, value) => {
        this.setState({
            userData: {
                ...this.state.userData,
                [fieldName]: value
            },
            errorFields: {
                ...this.state.errorFields,
                [fieldName]: value.length === 0
            }
        });

        if (fieldName === 'city' || fieldName === 'country' || fieldName === 'address') {
            this.changeAddress(fieldName, value);
        }
    };

    changeAddress = (fieldName, value) => {
        const address = {
            city: this.state.userData.city,
            country: this.state.userData.country,
            address: this.state.userData.address,
            [fieldName]: value
        };

        if (this.state.timer) {
            clearTimeout(this.state.timer);
        }
        const timer = setTimeout(() =>
            this.props.onAddressChanged(address).then(coords =>
                this.setState({
                    userData: {
                        ...this.state.userData,
                        latitude: coords.latitude,
                        longitude: coords.longitude
                    }
                })
            ), 1000);

        this.setState({timer: timer})
    };

    getFirstPart = () => {
        const {t} = this.props;
        return <div className='registration-first-part'>
            <div className='flex-input'>
                <Input
                    id='Login'
                    label={t('label.account.login')}
                    type='email'
                    name='username'
                    placeholder='johndoe@mail.xx'
                    value={this.state.userData.username}
                    error={this.state.errorFields.username}
                    onChange={(newValue) => this.setFieldValue('username', newValue)}
                />

                <Input
                    id='Phone'
                    label={t('label.account.phone')}
                    type='text'
                    name='phone'
                    placeholder={t('label.account.phone_placeholder')}
                    value={this.state.userData.phone}
                    error={this.state.errorFields.phone}
                    onChange={(newValue) => this.setFieldValue('phone', newValue)}
                    pattern={t('label.account.phone_pattern')}
                />

                <Input
                    id='CompanyName'
                    label={t('label.account.company_name')}
                    type='text'
                    name='companyName'
                    placeholder='Stock ltd'
                    maxLength={100}
                    value={this.state.userData.companyName}
                    error={this.state.errorFields.companyName}
                    onChange={(newValue) => this.setFieldValue('companyName', newValue)}
                />

                <Input
                    id='RegNumber'
                    label={t('label.account.reg_number')}
                    type='text'
                    name='RegNumber'
                    placeholder={t('label.account.reg_number_placeholder')}
                    pattern={t('label.account.reg_number_pattern')}
                    value={this.state.userData.regNumber}
                    error={this.state.errorFields.regNumber}
                    onChange={(newValue) => this.setFieldValue('regNumber', newValue)}
                />

                <Input
                    id='Password'
                    label={t('label.account.password')}
                    type='password'
                    name='password'
                    placeholder='***********'
                    maxLength={50}
                    value={this.state.userData.password}
                    error={this.state.errorFields.password}
                    onChange={(newValue) => this.setFieldValue('password', newValue)}
                />
                <Input
                    id='PasswordAgain'
                    label={t('label.account.repeat_password')}
                    type='password'
                    name='passwordRepeat'
                    placeholder='***********'
                    maxLength={50}
                    value={this.state.userData.passwordRepeat}
                    error={this.state.errorFields.passwordRepeat}
                    onChange={(newValue) => this.setFieldValue('passwordRepeat', newValue)}
                />
            </div>
            <Button id='to-second-step' onClick={this.toStep2}>
                {t('label.button.continueButton')}
            </Button>
            <p className='second-step'>{t('label.account.secondStepReg')}</p>
        </div>
    };

    getSecondPart = () => {
        const {t} = this.props;
        return <div className='registration-second-part'>
            <div className='flex-input'>
                <Input
                    id='City'
                    className='city'
                    label={t('label.account.city')}
                    type='text'
                    name='city'
                    placeholder='Minsk'
                    maxLength={100}
                    value={this.state.userData.city}
                    error={this.state.errorFields.city}
                    onChange={(newValue) => this.setFieldValue('city', newValue)}
                />
                <Input
                    id='Country'
                    className='country'
                    label={t('label.account.country')}
                    type='text'
                    name='city'
                    placeholder='Belarus'
                    maxLength={50}
                    value={this.state.userData.country}
                    error={this.state.errorFields.country}
                    onChange={(newValue) => this.setFieldValue('country', newValue)}
                />
                <Input
                    id='Address'
                    label={t('label.account.address')}
                    type='text'
                    name='city'
                    placeholder='str. Nezalejnasti'
                    maxLength={200}
                    value={this.state.userData.address}
                    error={this.state.errorFields.address}
                    onChange={(newValue) => this.setFieldValue('address', newValue)}
                />

                <div className="forMap google-map-container">
                    <Map coords={{latitude: this.state.userData.latitude, longitude: this.state.userData.longitude}}/>
                </div>

                <CheckBox
                    value={this.state.agree}
                    error={this.state.agreeError}
                    onChange={(value) => this.setState({agree: value, agreeError: !value})}
                >
                    <span>{t('label.account.accept')}<a>
                        <span>{t('label.account.termsOfUse')}</span>
                    </a></span>
                </CheckBox>
            </div>
            <Button id='go-back' onClick={() => this.setState({step: 1})}>
                {t('label.button.back')}
            </Button>
            <div id='form-list'>
                <Button id='finish-registration' onClick={this.register}>{t('label.button.finishRegistration')}</Button>
            </div>
        </div>
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form' id='registration-form'>
            <h3>{t('label.account.registration')}</h3>
            <p>{t('label.account.haveAcc')}
                <span className='go_to_login' onClick={this.props.toLogin}>
                    <span>{t('label.account.logHere')}</span>
                </span>
            </p>
            <div>
                <Message message={this.state.message}/>
            </div>
            {this.state.step === 1
                ? this.getFirstPart()
                : this.getSecondPart()}
        </form>
    }

}

export default withTranslation()(RegistrationForm);