import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from '../../components/input/Input';
import Button from '../../components/input/Button';
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';

class AddressForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            address: this.props.address
                || {
                    id: null,
                    address: '',
                    city: '',
                    country: '',
                    latitude: 0,
                    longitude: 0
                },
            error: {}
        };
    }

    validate = () => {
        const error = {};

        if (this.state.address.address === '') {
            error.address = true;
        }

        if (this.state.address.city === '') {
            error.city = true;
        }

        if (this.state.address.country === '') {
            error.country = true;
        }

        this.setState({error: error});
        return Object.values(error).filter(error => !error).length === 0;
    };

    save = () => {
        if (this.validate()) {
            const address = {...this.state.address};
            const method = address.id === null ? Request.doPost : Request.doPut;
            method(ApiUrls.ADDRESS.replace('{id}', ''), null, address)
                .then(async response => {
                    if (response.ok) {
                        const id = await response.text();
                        if (id) {
                            address.id = JSON.parse(id);
                        }
                        this.props.onSave(address, !!id);
                    }
                });
        }
    };

    changeAddress = (propertyName, value) => {
        this.setState({
            address: {
                ...this.state.address,
                [propertyName]: value
            },
            error: {
                ...this.state.error,
                [propertyName]: false
            }
        });
        this.changeAddressCoords(propertyName, value);
    };

    changeAddressCoords = (propertyName, value) => {
        const address = {
            city: this.state.address.city,
            country: this.state.address.country,
            address: this.state.address.address,
            [propertyName]: value
        };

        if (this.state.timer) {
            clearTimeout(this.state.timer);
        }
        const timer = setTimeout(() =>
            this.props.onAddressChanged(address).then(coords =>
                this.setState({
                    address: {
                        ...this.state.address,
                        language: coords.language,
                        longitude: coords.longitude
                    }
                })
            ), 1000);

        this.setState({timer: timer})
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form'>
            <h3>{t('label.address.title')}</h3>
            <div className='flex-input'>
                <Input
                    id='Country'
                    name='country'
                    className='country'
                    label={t('label.address.country')}
                    error={this.state.error.country}
                    value={this.state.address.country}
                    maxLength={50}
                    onChange={(newValue) => this.changeAddress('country', newValue)}
                />

                <Input
                    id='City'
                    name='city'
                    className='city'
                    label={t('label.address.city')}
                    error={this.state.error.city}
                    value={this.state.address.city}
                    maxLength={50}
                    onChange={(newValue) => this.changeAddress('city', newValue)}
                />

                <Input
                    id='Address'
                    name='address'
                    className='address'
                    label={t('label.address.address')}
                    error={this.state.error.address}
                    value={this.state.address.address}
                    maxLength={50}
                    onChange={(newValue) => this.changeAddress('address', newValue)}
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

export default withTranslation()(AddressForm);