import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";

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

    componentDidMount() {
        const lots = Request.doGet(ApiUrls.LOT_LIST);
        const products = Request.doGet(ApiUrls.PRODUCT_LIST);
        const addresses = Request.doGet(ApiUrls.ADDRESS_LIST);

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
            {name: t('label.lot.product'), weight: 6},
            {name: t('label.lot.status'), weight: 5},
            {name: t('label.lot.min_price'), weight: 1},
            {name: t('label.lot.max_volume'), weight: 2},
            {name: t('label.lot.exp_date'), weight: 4},
            {name: t('label.lot.creation_date'), weight: 3},
            {name: t('label.lot.edit'), weight: 7},
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

    changeStatus = (id, status) => {
        const url = status === 'ACTIVE'
            ? ApiUrls.LOT_DEACTIVATE
            : ApiUrls.LOT_ACTIVATE;
        Request.doPut(url.replace("{id}, id"))
            .then(responce => {
                if (responce.ok) {
                    const data = this.state.data.slice();
                    data.find(l => l.id === id)
                        .status = status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
                    this.setState({data: data});
                }
            })
    };

    getProduct = (categoryId) => {
        return this.state.products.find(p => p.categories.find(c => c.id === categoryId));
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(l => {
            return [
                t(`label.product.${this.getProduct(l.categoryId).name}`),
                <div className={`swtitch-row-${l.status === 'ACTIVE' ? 'on' : 'off'}`}
                     onClick={() => this.changeStatus(l.id, l.status)}
                />,
                l.minPrice,
                l.maxVolume,
                l.expirationDate,
                l.creationDate,
                <React.Fragment>
                    <div className='edit'/>
                    <div className='remove' onClick={() => this.remove(l.id)}/>
                </React.Fragment>
            ]
        })
    };

    getForm = () => {
        if (this.state.editLot) {
            return <Form
                map={true}
                className='lot-dialogbox'
                onClose={() => this.setState({editLot: null})}
            >
                <LotForm lot={{...this.state.editLot}}/>
            </Form>
        }
    };

    render() {
        return <Table
            data={this.getData()}
            header={this.getHeader}
            pageSize={10}
        />;
    }
}

export default withTranslation()(Lots);