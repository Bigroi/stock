import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";
import LotForm from "../../../forms/lot/LotForm"
import Message, {TYPES} from "../../../components/message/Message";
import ProductForm from "../../../forms/product/ProductForm";
import CategoryForm from "../../../forms/category/CategoryForm";
import Button from "../../../components/input/Button";

class ProductsStatistics extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        Request.doGet(ApiUrls.PRODUCTS_STATISTICS)
            .then(async response => {
                if (response.ok) {
                    this.setState({data: JSON.parse(await response.text())});
                }
            });
    }

    getCard = (product) => {
        const {t} = this.props;
        return <div className="product"
                    style={{backgroundImage: `url("${product.picture}")`}}
                    key={`product-card-for-${product.id}`}
        >
            <h4>{t(`label.${product.name}.name`)}</h4>
            <Button className="background-blue" onClick={() => this.props.onDetails(product.id)}>
                {t('label.product.details')}
            </Button>
            <div className="about-product">
                <table>
                    <thead>
                    <tr>
                        <td/>
                        <td>{t('label.index.average_price')}</td>
                        <td>{t('label.index.requests_volume')}</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr className="sell-tr">
                        <td>{t('label.index.sell')}</td>
                        <td>{product.sell.price}</td>
                        <td>{product.sell.volume}</td>
                    </tr>
                    <tr className="buy-tr">
                        <td>{t('label.index.buy')}</td>
                        <td>{product.buy.price}</td>
                        <td>{product.sell.volume}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>;
    };

    render() {
        return <div className="product-cont authorised product-page">
            {this.state.data.map(this.getCard)}
        </div>
    }
}

export default withTranslation()(ProductsStatistics);