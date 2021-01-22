import React from 'react';
import {withTranslation} from 'react-i18next';
import Dropdown from '../../components/input/Dropdown';
import Input from '../../components/input/Input';
import Calendar from '../../components/input/Calendar';
import File from '../../components/input/File';
import TextArea from '../../components/input/TextArea';
import Button from '../../components/input/Button';
import ApiUrls from '../../util/ApiUrls';
import Map from '../../components/map/Map';
import Request from '../../util/Request';
import Message, {TYPES} from '../../components/message/Message';

class LotForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            lot: this.props.lot
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
                    distance: 30000,
                    categoryId: null,
                    alert: null,
                    photo: null
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

        if (Number.isNaN(+this.state.lot.price) || this.state.lot.price < 0.01) {
            message = {
                type: TYPES.ERROR,
                value: t('label.lot.price_error')
            };
            error.price = true;
        }

        if (!Number.isInteger(+this.state.lot.minVolume) || this.state.lot.minVolume < 1) {
            message = {
                type: TYPES.ERROR,
                value: t('label.lot.minVolume_error')
            };
            error.minVolume = true;
        }

        if (!Number.isInteger(+this.state.lot.maxVolume) || this.state.lot.maxVolume < this.state.lot.minVolume) {
            message = {
                type: TYPES.ERROR,
                value: t('label.lot.maxVolume_error')
            };
            error.maxVolume = true;
        }
        if (!this.state.lot.categoryId) {
            message = {
                type: TYPES.ERROR,
                value: t('label.lot.category_error')
            };
            error.categoryId = true;
        }

        if (this.state.lot.expirationDate < new Date()) {
            message = {
                type: TYPES.ERROR,
                value: t('label.lot.expDate_error')
            };
            error.expirationDate = true;
        }

        this.setState({error: error, message: message});
        return message === null;
    };

    save = (status) => {
        if (this.validate()) {
            const lot = {...this.state.lot, status: status};
            const method = lot.id === null ? Request.doPost : Request.doPut;
            method(ApiUrls.LOT.replace('{id}', lot.id || ''), null, lot)
                .then(async responce => {
                    if (responce.ok) {
                        const id = await responce.text();
                        if (id) {
                            lot.id = JSON.parse(id);
                        }
                        this.props.onSave(lot, !!id);
                    }
                });
        }
    };

    getProduct = () => {
        const {t} = this.props;
        return this.props.products.find(p => p.categories.find(c => c.id === this.state.lot.categoryId))
            || {name: t('label.lot.list'), id: null, categories: []};
    };

    getProducts = () => {
        const {t} = this.props;
        const product = this.getProduct();
        const list = this.props.products.map(p => {
            return {
                id: p.id,
                name: t(`label.${p.name}.name`),
                active: p.id === product.id
            }
        });

        list.splice(0, 0, {name: t('label.lot.list'), id: null, active: product.id === null});
        return list;
    };

    getCategories = () => {
        const {t} = this.props;
        const list = (this.props.products.find(p => p.id === this.state.lot.productId)
            || this.getProduct()
            || {categories: []})
            .categories
            .map(p => {
                return {
                    id: p.id,
                    name: t(`label.${p.name}.name`),
                    active: p.id === this.state.lot.categoryId
                }
            });

        list.splice(0, 0, {
            name: t('label.lot.list'), id: null, active: this.state.lot.categoryId === null
        });
        return list;
    };

    getAddresses = () => {
        return this.props.addresses
            .map(a => {
                return {
                    name: `${a.country}, ${a.city}, ${a.address}`,
                    id: a.id,
                    active: a.id === this.state.lot.addressId
                }
            });
    };

    getDistances = () => {
        const {t} = this.props;
        return [
            {id: 30000, name: t('label.lot.distance_any'), active: this.state.lot.distance === 30000},
            {id: 400, name: t('label.lot.distance_400'), active: this.state.lot.distance === 400},
            {id: 200, name: t('label.lot.distance_200'), active: this.state.lot.distance === 200},
            {id: 100, name: t('label.lot.distance_100'), active: this.state.lot.distance === 100},
            {id: 50, name: t('label.lot.distance_40'), active: this.state.lot.distance === 50},
            {id: 0, name: t('label.lot.distance_0'), active: this.state.lot.distance === 0}
        ];
    };

    showPhoto = (event) => {
        event.preventDefault();
        console.log(this.state.lot.photo);
    };

    changeLot = (propertyName, value) => {
        this.setState({
            lot: {
                ...this.state.lot,
                [propertyName]: value
            },
            error: {
                ...this.state.error,
                [propertyName]: false
            }
        })
    };

    getCoords = () => {
        const address = this.props.addresses.find(a => a.id === this.state.lot.addressId);
        return {latitude: address.latitude, longitude: address.longitude};
    };

    render() {
        const {t} = this.props;
        return <form className='form' name='form'>
            <h3>{t('label.lot.lotForm')}</h3>
            <div>
                <Message message={this.state.message}/>
            </div>
            <div className='flex-two-input'>
                <div>
                    <Dropdown
                        id='ProductId'
                        name='productId'
                        elements={this.getProducts()}
                        label={t('label.lot.product')}
                        readOnly={this.state.lot.id !== null}
                        error={this.state.error.productId}
                        onChange={(newId) => this.changeLot('productId', newId)}
                    />

                    <Dropdown
                        id='CategoryId'
                        name='categoryId'
                        elements={this.getCategories()}
                        label={t('label.lot.category')}
                        error={this.state.error.categoryId}
                        onChange={(newId) => this.changeLot('categoryId', newId)}
                    />

                    <Input
                        id='Price'
                        label={t('label.lot.min_price')}
                        name='price'
                        placeholder='9.99'
                        value={this.state.lot.price}
                        error={this.state.error.price}
                        onChange={(newValue) => this.changeLot('price', newValue)}
                    />

                    <Input
                        id='MinVolume'
                        label={t('label.lot.min_volume')}
                        name='minVolume'
                        value={this.state.lot.minVolume}
                        error={this.state.error.minVolume}
                        onChange={(newValue) => this.changeLot('minVolume', newValue)}
                    />

                    <Input
                        id='MaxVolume'
                        label={t('label.lot.max_volume')}
                        name='maxVolume'
                        value={this.state.lot.maxVolume}
                        error={this.state.error.maxVolume}
                        onChange={(newValue) => this.changeLot('maxVolume', newValue)}
                    />

                    <Calendar
                        id='ExpDate'
                        label={t('label.lot.exp_date')}
                        value={this.state.lot.expirationDate}
                        error={this.state.error.expirationDate}
                        onChange={(value => this.changeLot('expirationDate', value))}
                    />
                </div>
                <div>
                    <div>
                        <div>
                            <Dropdown
                                id='AddressId'
                                name='addressId'
                                className='address-selector'
                                elements={this.getAddresses()}
                                label={t('label.lot.address')}
                                error={this.state.error.addressId}
                                onChange={(newId) => this.changeLot('addressId', newId)}
                            />

                            <Dropdown
                                id='Distance'
                                name='distance'
                                className='address-selector'
                                elements={this.getDistances()}
                                label={t('label.lot.distance')}
                                error={this.state.error.distance}
                                onChange={(newId) => this.changeLot('distance', newId)}
                            />

                            {this.state.lot.photo
                                ? <a href='#' onClick={(e) => this.showPhoto(e)}>
                                    {t('label.lot.uploaded_foto')}
                                </a>
                                : ''
                            }
                            <File
                                id='Photo'
                                name='photo'
                                label={t('label.lot.foto')}
                                onChange={(value) => this.changeLot('photo', value)}
                            />

                            <TextArea
                                id='Description'
                                labe={t('label.lot.description')}
                                name='description'
                                maxLength={1000}
                                value={this.state.lot.description}
                                onChange={(value) => this.changeLot('description', value)}
                            />
                        </div>
                        <div className='forMapLotsTender google-map-container'
                             style={{position: 'relative', overflow: 'hidden'}}>
                            <Map coords={this.getCoords()}/>
                        </div>
                    </div>
                    <div id='form-list'>
                        <Button className='submit button gray-button' id='save' onClick={() => this.save('INACTIVE')}>
                            {t('label.button.save')}
                        </Button>
                        <Button className='submit button' id='save-start-trading' onClick={() => this.save('ACTIVE')}>
                            {t('label.button.save_start_trading')}
                        </Button>
                    </div>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(LotForm);