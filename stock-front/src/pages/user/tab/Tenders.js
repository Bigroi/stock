import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";
import LotForm from "../../../forms/lot/LotForm"
import Message, {TYPES} from "../../../components/message/Message";
import TenderForm from "../../../forms/tender/TenderForm";

class Tenders extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            products: [],
            addresses: [],
            editTender: null
        }
    }

    loadData = (url) => {
        return Request.doGet(url)
            .then(async response => {
                if (response.ok) {
                    return JSON.parse(await response.text());
                }
            })
    };

    componentDidMount() {
        const tenders = this.loadData(ApiUrls.TENDER_LIST);
        const products = this.loadData(ApiUrls.PRODUCT_LIST);
        const addresses = this.loadData(ApiUrls.ADDRESS_LIST);

        Promise.all([tenders, products, addresses])
            .then(values => {
                this.setState({
                    data: values[0],
                    products: values[1],
                    addresses: values[2]
                });
                Request.doPut(ApiUrls.ALERTS_TENDERS)
                    .then(response => {
                        if (response.ok) {
                            this.props.resetAlerts();
                        }
                    });
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.tender.product'), weight: 2},
            {name: t('label.tender.status'), weight: 3},
            {name: t('label.v.max_price'), weight: 7},
            {name: t('label.tender.max_volume'), weight: 6},
            {name: t('label.tender.exp_date'), weight: 4},
            {name: t('label.tender.creation_date'), weight: 5},
            {name: t('label.tender.edit'), weight: 1},
        ]
    };

    remove = (id) => {
        Request.doDelete(ApiUrls.TENDER.replace("{id}", id))
            .then(responce => {
                if (responce.ok) {
                    this.setState({data: this.state.data.filter(l => l.id !== id)});
                }
            });
    };

    getProduct = (productId) => {
        return this.state.products.find(p => p.id === productId);
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

    changeStatus = (tender) => {
        const {t} = this.props;
        if (tender.status === 'ACTIVE') {
            Request.doPut(ApiUrls.TENDER_DEACTIVATE.replace('{id}', tender.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === tender.id).status = 'INACTIVE';
                        this.setState({
                            data: data,
                            message: {
                                type: TYPES.SUCCESS,
                                value: t('label.bid.deactivated')
                            }
                        });
                    }
                });
        } else if (new Date(tender.expirationDate) >= new Date()) {
            Request.doPut(ApiUrls.TENDER_ACTIVATE.replace('{id}', tender.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === tender.id).status = 'ACTIVE';
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
                    value: t('label.tender.expDate_error')
                }
            });
        }
        setTimeout(() => this.setState({message: null}), 3000);
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(p => {
            return [
                t(`label.${this.getProduct(p.productId).name}.name`),
                <div className={`swtitch-row-${p.status === 'ACTIVE' ? 'on' : 'off'}`}
                     onClick={() => this.changeStatus(p)}
                />,
                p.price,
                p.maxVolume,
                typeof p.expirationDate === 'string' ? p.expirationDate : this.formatDate(p.expirationDate),
                typeof p.creationDate === 'string' ? p.creationDate : this.formatDate(p.creationDate),
                <React.Fragment>
                    <div className='edit' onClick={() => this.setState({editTender: p})}/>
                    <div className='remove' onClick={() => this.remove(p.id)}/>
                </React.Fragment>
            ]
        })
    };

    closeForm = () => {
        this.setState({editTender: null});
        this.props.onCloseForm();
    };

    getForm = () => {
        if (this.state.editTender || this.props.showEmptyForm) {
            return <Form
                className='tender-dialogbox'
                onClose={this.closeForm}
            >
                <TenderForm
                    tender={this.state.editTender ? {...this.state.editTender} : null}
                    products={this.state.products}
                    addresses={this.state.addresses}
                    onSave={this.updateTenderList}
                />
            </Form>
        }
    };

    updateTenderList = (tender, isNew) => {
        const {t} = this.props;
        const tenders = this.state.data.slice();
        if (isNew) {
            tenders.splice(0, 0, tender);
        } else {
            const tenderFromList = tenders.find(l => l.id === tender.id);
            Object.keys(tenderFromList)
                .forEach(propertyName => tenderFromList[propertyName] = tender[propertyName]);
        }
        this.setState({
            data: tenders,
            message: {
                type: TYPES.SUCCESS,
                value: t(tender.status === 'ACTIVE' ? 'label.bid.activated-morning' : 'label.bid.deactivated')
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

export default withTranslation()(Tenders);