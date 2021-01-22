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

class FeedBackForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: LocalStorage.getEmail() || '',
            emailError: false,
            message: '',
            successMessage: null
        }
    }

    submitFeedBack = () => {
        const {t} = this.props;
        if (this.state.email === '' || VerificationUtils.checkEmail(this.state.email, t)) {
            this.setState({emailError: true});
        } else {
            const data = {
                email: this.state.email,
                message: this.state.message
            };
            Request.doPost(ApiUrls.FEED_BACK, null, data)
                .then(response => {
                    if (response.ok) {
                        this.setState({
                            successMessage: {
                                type: TYPES.SUCCESS,
                                value: t('label.account.fb_success')
                            }
                        })
                    }
                })
        }
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form'>
            <h3>{t('label.account.send_Message')}</h3>
            <div>
                <Message message={this.state.successMessage}/>
            </div>
            <div className='flex-input'>
                <Input
                    id='email'
                    label={t('label.account.email')}
                    name='email'
                    maxLength={50}
                    error={this.state.emailError}
                    placeholder='example@mail.com'
                    value={this.state.email}
                    onChange={(newValue => this.setState({email: newValue, emailError: false}))}
                />
                <TextArea
                    id=''
                    label={t('label.account.message')}
                    name=''
                    maxLength={500}
                    value={this.state.message}
                    onChange={(newValue => this.setState({message: newValue}))}
                />
                <div id='form-list'>
                    <Button className='submit button' id='contact-us' onClick={this.submitFeedBack}>
                        {t('label.button.send')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(FeedBackForm);