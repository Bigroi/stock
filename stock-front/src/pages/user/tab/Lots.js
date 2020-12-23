import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";
import LotForm from "../../../forms/lot/LotForm"
import Message, {TYPES} from "../../../components/message/Message";

class Lots extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            products: [],
            addresses: [],
            editLot: null
        }
    }

    loadData = (url) => {
        return Request.doGet(url)
            .then(async responce => {
                if (responce.ok) {
                    return JSON.parse(await responce.text());
                }
            })
    };

    componentDidMount() {
        const lots = this.loadData(ApiUrls.LOT_LIST);
        const products = this.loadData(ApiUrls.PRODUCT_LIST);
        const addresses = this.loadData(ApiUrls.ADDRESS_LIST);

        Promise.all([lots, products, addresses])
            .then(values => {
                this.setState({
                    data: values[0],
                    products: values[1],
                    addresses: values[2]
                });
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.lot.product'), weight: 2},
            {name: t('label.lot.status'), weight: 3},
            {name: t('label.lot.min_price'), weight: 7},
            {name: t('label.lot.max_volume'), weight: 6},
            {name: t('label.lot.exp_date'), weight: 4},
            {name: t('label.lot.creation_date'), weight: 5},
            {name: t('label.lot.edit'), weight: 1},
        ]
    };

    remove = (id) => {
        Request.doDelete(ApiUrls.LOT.replace("{id}", id))
            .then(responce => {
                if (responce.ok) {
                    this.setState({data: this.state.data.filter(l => l.id !== id)});
                }
            });
    };

    getProduct = (categoryId) => {
        return this.state.products.find(p => p.categories.find(c => c.id === categoryId));
    };

    formatDate = (date) => {
        let month = '' + (date.getMonth() + 1);
        let day = '' + date.getDate()
        let year = date.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        return [year, month, day].join('-');
    };

    changeStatus = (lot) => {
        const {t} = this.props;
        if (lot.status === 'ACTIVE') {
            Request.doPut(ApiUrls.LOT_DEACTIVATE.replace('{id}', lot.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === lot.id).status = 'INACTIVE';
                        this.setState({
                            data: data,
                            message: {
                                type: TYPES.SUCCESS,
                                value: t('label.bid.deactivated')
                            }
                        });
                    }
                });
        } else if (new Date(lot.expirationDate) >= new Date()) {
            Request.doPut(ApiUrls.LOT_ACTIVATE.replace('{id}', lot.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === lot.id).status = 'ACTIVE';
                        this.setState({
                            data: data,
                            message: {
                                type: TYPES.SUCCESS,
                                value: t('label.bid.activated-morning')
                            }
                        });
                    }
                });
        } else {
            this.setState({
                message: {
                    type: TYPES.ERROR,
                    value: t('label.lot.expDate_error')
                }
            });
        }
        //setTimeout(() => this.setState({message: null}), 3000);
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(l => {
            return [
                t(`label.${this.getProduct(l.categoryId).name}.name`),
                <div className={`swtitch-row-${l.status === 'ACTIVE' ? 'on' : 'off'}`}
                     onClick={() => this.changeStatus(l)}
                />,
                l.price,
                l.maxVolume,
                typeof l.expirationDate === 'string' ? l.expirationDate : this.formatDate(l.expirationDate),
                typeof l.creationDate === 'string' ? l.creationDate : this.formatDate(l.creationDate),
                <React.Fragment>
                    <div className='edit' onClick={() => this.setState({editLot: l})}/>
                    <div className='remove' onClick={() => this.remove(l.id)}/>
                </React.Fragment>
            ]
        })
    };

    closeForm = () => {
        this.setState({editLot: null});
        this.props.onCloseForm();
    };

    getForm = () => {
        if (this.state.editLot || this.props.showEmptyForm) {
            return <Form
                map={true}
                className='lot-dialogbox'
                onClose={this.closeForm}
            >
                <LotForm
                    lot={this.state.editLot ? {...this.state.editLot} : null}
                    products={this.state.products}
                    addresses={this.state.addresses}
                    onSave={this.updateLotList}
                />
            </Form>
        }
    };

    updateLotList = (lot, isNew) => {
        const {t} = this.props;
        const lots = this.state.data.slice();
        if (isNew) {
            lots.splice(0, 0, lot);
        } else {
            const lotFromList = lots.find(l => l.id === lot.id);
            Object.keys(lotFromList)
                .forEach(propertyName => lotFromList[propertyName] = lot[propertyName]);
        }
        this.setState({
            data: lots,
            message: {
                type: TYPES.SUCCESS,
                value: t(lot.status === 'ACTIVE' ? 'label.bid.activated-morning' : 'label.bid.deactivated')
            }
        });
        setTimeout(() => this.setState({message: null}), 3000);
        this.closeForm();
    };

    render() {
        return <React.Fragment>
            {this.getForm()}
            <Table
                data={this.getData()}
                header={this.getHeader()}
                message={this.state.message}
                pageSize={10}
            />
        </React.Fragment>;
    }
}

export default withTranslation()(Lots);