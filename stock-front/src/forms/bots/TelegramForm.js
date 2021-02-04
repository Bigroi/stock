import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import Request from '../../util/Request';
import ApiUrls from '../../util/ApiUrls';
import Message, {TYPES} from '../../components/message/Message';
import utils from '../../util/LocalStorage';


class TelegramForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            telegramUserName: '',
            errorMessage: ''
        }
    }

    telegramBindRequest(userEmail) {
        const { t } = this.props;
        const data = {};
        data.telegramUserName = this.state.telegramUserName;
        data.userEmail = userEmail;
        Request.doPost(ApiUrls.BOT_MANAGEMENT, null, data)
        .then(async response => {
            if (!response.ok) {
                this.setState({
                    errorMessage: {
                        type: TYPES.ERROR,
                        value:  "incorrect telegram user name" // t('label.index.login_error')
                    }
                })
            }
        })
    };

    render() {
        const {t} = this.props;
        return (
            <form className='form' name='form' id='telegramBot-form'>
                <h3>{t('label.bot.formActivation')}</h3>
                <h4> {t('label.bot.steps')} </h4>
                <p> 1) {t('label.bot.step-one')}</p>
                <p> 2) {t('label.bot.step-two')}</p>
                <p> 3) {t('label.bot.step-three')}</p>
                <p> 4) {t('label.bot.step-four')}</p>
                <div>
                    <Message message={this.state.errorMessage}/>
                </div>

                <Input
                    id='telegramUserName'
                    label={t('label.bot.telegramUserName')}
                    type='text'
                    maxLength={100}
                    value={this.state.telegramUserName}
                    onChange={(value) => this.setState({telegramUserName: value})}
                />

                <p>{t('label.bot.gratitude')}</p> 

                <div>
                    <div id='form-list'>
                        <Button id='login' className='submit button' onClick={() => this.telegramBindRequest(utils.getEmail())}>
                            {t('label.bot.telegramBind')}
                        </Button>
                    </div>
                </div>
            </form>
        );
    }
}

export default withTranslation()(TelegramForm);