import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import Request from '../../util/Request';
import ApiUrls from '../../util/ApiUrls';
import Message, {TYPES} from '../../components/message/Message';


const TELEGRAM_BOT_NAME = "testYourTrader_bot";
const TELEGRAM_BOT_COMMAND = "/start";


class TelegramForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            telegramUserName: '',
            errorMessage: ''
        }
    }

    telegramBindRequest(userEmail) {
        debugger
        const { t } = this.props;
        const data = {};
        data.telegramUserName = this.state.telegramUserName;
        data.userEmail = userEmail;
        Request.doPost(ApiUrls.BOTS_MANAGEMENT.TELEGRAM_BOT_BINDING, null, data)
        .then(async response => {
            debugger
            if (response.ok) {
                console.log(response); // TODO: delete
            } else {
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
        return <form className='form' name='form' id='telegramBot-form'>
            <h3>{t('label.bot.botForm')}</h3>
            <h4> For bind bot with account you need do 4 simple steps: </h4>
            <p> 1) need found this bot "{TELEGRAM_BOT_NAME}" in telegram </p>
            <p> 2) send command to bot: "{TELEGRAM_BOT_COMMAND}"</p>
            <p> 3) need write any message to bot</p>
            <p> 4) below need write user name from telegram</p>
            <div>
                <Message message={this.state.errorMessage}/>
            </div>

            <Input
                id='telegramUserName'
                label={t('label.bot.telegramUserName')}
                type='text'
                placeholder='please enter yout telegram user name'
                maxLength={100}
                value={this.state.telegramUserName}
                onChange={(value) => this.setState({telegramUserName: value})}
            />

            <p> If you completed steps you need press button below and after that your account will be bind with telegram bot.
                Thanks for binded and have a nice a trade.</p> 

            <div>
                <div id='form-list'>
                    <Button id='login' className='submit button' onClick={() => this.telegramBindRequest(localStorage.getItem("yt-email"))}>
                        {t('label.bot.telegramBind')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(TelegramForm);