import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import Request from "../../util/Request";
import ApiUrls from '../../util/ApiUrls';
import Message, {TYPES} from "../../components/message/Message";

/**
 * toRegistration: function
 * onLoginSuccess: function
 */
class LoginForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {
                username: '',
                password: ''
            },
            errorMessage: ''
        }
    }

    loginRequest = () => {
        const {t} = this.props;
        Request.doPost(ApiUrls.AUTHENTICATE, null, this.state)
            .then(async response => {
                if (response.ok) {
                    const authParams = await response.text();
                    this.props.onLoginSuccess(JSON.parse(authParams));
                } else {
                    this.setState({
                        errorMessage: {
                            type: TYPES.ERROR,
                            value: t('label.index.login_error')
                        }
                    })
                }
            })
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form' id='login-form'>
            <h3>{t('label.login.loginForm')}</h3>
            <p>{t('label.login.dontHaveAcc')}
                <span className='go_to_registration' onClick={this.props.toRegistration}>
                    <span>{t('label.login.registerHere')}</span>
                </span>
            </p>
            <div>
                <Message message={this.state.errorMessage}/>
            </div>
            <Input
                id='Login'
                label={t('label.login.login')}
                type='email'
                name='username'
                placeholder='johndoe@mail.xx'
                value={this.state.username}
                onChange={(newValue) => this.setState({username: newValue})}
            />

            <Input
                id='Password'
                label={t('label.login.password')}
                type='password'
                name='password'
                placeholder='***********'
                maxLength={50}
                value={this.state.password}
                onChange={(newValue) => this.setState({password: newValue})}
                underline={<a id='reset'><span>{t('label.login.forgot')}</span></a>}
            />
            <div>
                <div id='form-list'>
                    <Button id='login' className='submit button' onClick={this.loginRequest}>
                        {t('label.button.login')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(LoginForm);