import React from 'react';
import {withTranslation} from 'react-i18next';
import Dropdown from "../../components/input/Dropdown";
import Input from "../../components/input/Input";
import Calendar from "../../components/input/Calendar";
import TextArea from "../../components/input/TextArea";
import Button from "../../components/input/Button";
import ApiUrls from '../../util/ApiUrls';
import Map from "../../components/map/Map";
import Request from '../../util/Request';
import Message, {TYPES} from "../../components/message/Message";

class TenderForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tender: this.props.tender
                || {
                    id: null,
                    description: '',
                    price: null,
                    minVolume: null,
                    maxVolume: null,
                    companyId: null,
                    status: null,
                    creationDate: new Date(),
                    expirationDate: this.getDefaultExpirationDate(),
                    addressId: this.props.addresses[0].id,
                    packaging: null,
                    processing: null,
                    distance: 30000,
                    categoryId: null,
                    productId: null
                },
            error: {}
        };
    }

    getDefaultExpirationDate = () => {
        const date = new Date();
        if (date.getMonth() === 12) {
            date.setMonth(1);
            date.setFullYear(date.getFullYear() + 1);
        } else {
            date.setMonth(date.getMonth() + 1);
        }
        return date;
    };

    validate = () => {
        const {t} = this.props;
        const error = {};
        let message = null;

        if (Number.isNaN(+this.state.tender.price) || this.state.tender.price < 0.01) {
            message = {
                type: TYPES.ERROR,
                value: t('label.tender.price_error')
            };
            error.price = true;
        }

        if (!Number.isInteger(+this.state.tender.minVolume) || this.state.tender.minVolume < 1) {
            message = {
                type: TYPES.ERROR,
                value: t('label.tender.minVolume_error')
            };
            error.minVolume = true;
        }

        if (!Number.isInteger(+this.state.tender.maxVolume)
            || this.state.tender.maxVolume < this.state.tender.minVolume) {
            message = {
                type: TYPES.ERROR,
                value: t('label.tender.maxVolume_error')
            };
            error.maxVolume = true;
        }
        if (!this.state.tender.productId) {
            message = {
                type: TYPES.ERROR,
                value: t('label.tender.product_error')
            };
            error.productId = true;
        }

        if (this.state.tender.expirationDate < new Date()) {
            message = {
                type: TYPES.ERROR,
                value: t('label.tender.expDate_error')
            };
            error.expirationDate = true;
        }

        this.setState({error: error, message: message});
        return message === null;
    };

    save = (status) => {
        if (this.validate()) {
            const tender = {...this.state.tender, status: status};
            const method = tender.id === null ? Request.doPost : Request.doPut;
            method(ApiUrls.TENDER.replace('{id}', tender.id || ''), null, tender)
                .then(async responce => {
                    if (responce.ok) {
                        const id = await responce.text();
                        if (id) {
                            tender.id = JSON.parse(id);
                        }
                        this.props.onSave(tender, !!id);
                    }
                });
        }
    };

    getProduct = () => {
        const {t} = this.props;
        return this.props.products.find(p => p.id === this.state.tender.productId)
            || {name: t('label.tender.list'), id: null, categories: []};
    };

    getProducts = () => {
        const {t} = this.props;
        const list = this.props.products.map(p => {
            return {
                id: p.id,
                name: t(`label.${p.name}.name`),
                active: p.id === this.state.tender.productId
            }
        });

        list.splice(0, 0, {
            name: t('label.tender.list'),
            id: null,
            active: this.state.tender.productId === null
        });
        return list;
    };

    getCategories = () => {
        const {t} = this.props;
        const list = (this.props.products.find(p => p.id === this.state.tender.productId) || {categories: []})
            .categories
            .map(p => {
                return {
                    id: p.id,
                    name: t(`label.${p.name}.name`),
                    active: p.id === this.state.tender.categoryId
                }
            });

        list.splice(0, 0, {
            name: t('label.tender.list'),
            id: null,
            active: this.state.tender.categoryId === null
        });
        return list;
    };

    getAddresses = () => {
        return this.props.addresses
            .map(a => {
                return {
                    name: `${a.country}, ${a.city}, ${a.address}`,
                    id: a.id,
                    active: a.id === this.state.tender.addressId
                }
            });
    };

    getDistances = () => {
        const {t} = this.props;
        return [
            {id: 30000, name: t('label.tender.distance_any'), active: this.state.tender.distance === 30000},
            {id: 400, name: t('label.tender.distance_400'), active: this.state.tender.distance === 400},
            {id: 200, name: t('label.tender.distance_200'), active: this.state.tender.distance === 200},
            {id: 100, name: t('label.tender.distance_100'), active: this.state.tender.distance === 100},
            {id: 50, name: t('label.tender.distance_40'), active: this.state.tender.distance === 50},
            {id: 0, name: t('label.tender.distance_0'), active: this.state.tender.distance === 0}
        ];
    };

    changeTender = (propertyName, value) => {
        this.setState({
            tender: {
                ...this.state.tender,
                [propertyName]: value
            },
            error: {
                ...this.state.error,
                [propertyName]: false
            }
        })
    };

    getCoords = () => {
        const address = this.props.addresses.find(a => a.id === this.state.tender.addressId);
        return {latitude: address.latitude, longitude: address.longitude};
    };

    render() {
        const {t} = this.props;
        return <form className="form" action="#" method="post" name="form">
            <h3>{t('label.tender.tenderForm')}</h3>
            <div>
                <Message message={this.state.message}/>
            </div>
            <div className="flex-two-input">
                <div>
                    <Dropdown
                        id='ProductId'
                        name='productId'
                        elements={this.getProducts()}
                        label={t('label.tender.product')}
                        readOnly={this.state.tender.id !== null}
                        error={this.state.error.productId}
                        onChange={(newId) => this.changeTender('productId', newId)}
                    />

                    <Dropdown
                        id='CategoryId'
                        name='categoryId'
                        elements={this.getCategories()}
                        label={t('label.tender.category')}
                        onChange={(newId) => this.changeTender('categoryId', newId)}
                    />

                    <Input
                        id='Price'
                        label={t('label.tender.max_price')}
                        name='price'
                        placeholder='9.99'
                        value={this.state.tender.price}
                        error={this.state.error.price}
                        onChange={(newValue) => this.changeTender('price', newValue)}
                    />

                    <Input
                        id='MinVolume'
                        label={t('label.tender.min_volume')}
                        name='minVolume'
                        value={this.state.tender.minVolume}
                        error={this.state.error.minVolume}
                        onChange={(newValue) => this.changeTender('minVolume', newValue)}
                    />

                    <Input
                        id='MaxVolume'
                        label={t('label.tender.max_volume')}
                        name='maxVolume'
                        value={this.state.tender.maxVolume}
                        error={this.state.error.maxVolume}
                        onChange={(newValue) => this.changeTender('maxVolume', newValue)}
                    />

                    <Calendar
                        id='ExpDate'
                        label={t('label.tender.exp_date')}
                        value={this.state.tender.expirationDate}
                        error={this.state.error.expirationDate}
                        onChange={(value => this.changeTender('expirationDate', value))}
                    />
                </div>
                <div>
                    <div>
                        <div>
                            <Dropdown
                                id='AddressId'
                                name='addressId'
                                className="address-selector"
                                elements={this.getAddresses()}
                                label={t('label.tender.address')}
                                error={this.state.error.addressId}
                                onChange={(newId) => this.changeTender('addressId', newId)}
                            />

                            <Dropdown
                                id='Distance'
                                name='distance'
                                className="address-selector"
                                elements={this.getDistances()}
                                label={t('label.tender.distance')}
                                error={this.state.error.distance}
                                onChange={(newId) => this.changeTender('distance', newId)}
                            />

                            <Input
                                id='Packaging'
                                label={t('label.tender.packaging')}
                                name='packaging'
                                value={this.state.tender.packaging}
                                error={this.state.error.packaging}
                                onChange={(newValue) => this.changeTender('packaging', newValue)}
                            />

                            <Input
                                id='Processing'
                                label={t('label.tender.processing')}
                                name='processing'
                                value={this.state.tender.processing}
                                error={this.state.error.processing}
                                onChange={(newValue) => this.changeTender('processing', newValue)}
                            />

                            <TextArea
                                id='Description'
                                labe={t('label.tender.description')}
                                name='description'
                                maxLength={1000}
                                value={this.state.tender.description}
                                onChange={(value) => this.changeTender('description', value)}
                            />
                        </div>
                        <div className="forMapTendersTender google-map-container"
                             style={{position: 'relative', overflow: 'hidden'}}>
                            <Map coords={this.getCoords()}/>
                        </div>
                    </div>
                    <div id="form-list">
                        <Button className="submit button gray-button" id="save" onClick={() => this.save('INACTIVE')}>
                            {t('label.button.save')}
                        </Button>
                        <Button className="submit button" id="save-start-trading" onClick={() => this.save('ACTIVE')}>
                            {t('label.button.save_start_trading')}
                        </Button>
                    </div>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(TenderForm);