import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';

class LabelForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            label: this.props.label ||
                {
                    name: '',
                    labels: {RU: '', PL: '', EN: ''}
                }
        };
    }

    save = () => {
        const label = {...this.state.label};
        const method = this.props.label ? Request.doPut : Request.doPost;
        method(ApiUrls.LABEL_ADMIN.replace('{name}', label.name), null, label)
            .then(async response => {
                if (response.ok) {
                    this.props.onSave(label, !!this.props.label);
                }
            });
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form'>
            <h3>{t('label.labels.labelForm')}</h3>
            <div className='flex-input'>
                <Input
                    id='Name'
                    label={t('label.labels.name')}
                    name='name'
                    value={this.state.label.name}
                    onChange={(newValue) => this.setState({
                        label: {...this.state.label, name: newValue}
                    })}
                />

                <Input
                    id='EN'
                    label={t('label.labels.english')}
                    name='en'
                    value={this.state.label.labels.EN}
                    onChange={(newValue) => this.setState({
                        label: {...this.state.label, labels: {...this.state.label.labels, EN: newValue}}
                    })}
                />

                <Input
                    id='RU'
                    label={t('label.labels.russian')}
                    name='ru'
                    value={this.state.label.labels.RU}
                    onChange={(newValue) => this.setState({
                        label: {...this.state.label, labels: {...this.state.label.labels, RU: newValue}}
                    })}
                />

                <Input
                    id='RU'
                    label={t('abel.labels.poland')}
                    name='ru'
                    value={this.state.label.labels.PL}
                    onChange={(newValue) => this.setState({
                        label: {...this.state.label, labels: {...this.state.label.labels, PL: newValue}}
                    })}
                />

                <div id='form-list'>
                    <Button className='submit button' id='save' onClick={this.save}>
                        {t('label.button.save')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(LabelForm);