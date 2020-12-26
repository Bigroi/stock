import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from "../../../util/Request";
import ApiUrls from "../../../util/ApiUrls";
import Table from "../../../components/table/Table";
import Form from "../../../components/form/Form";
import LotForm from "../../../forms/lot/LotForm"
import Message, {TYPES} from "../../../components/message/Message";
import ProductForm from "../../../forms/product/ProductForm";
import Categories from "./Categories";

class Products extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.PRODUCTS_ADMIN)
            .then(async responce => {
                if (responce.ok) {
                    this.setState({data: JSON.parse(await responce.text())});
                }
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.product.name'), weight: 1},
            {name: t('label.product.archive'), weight: 3},
            {name: t('label.product.edit'), weight: 1},
        ]
    };

    changeStatus = (product) => {
        if (product.removed) {
            Request.doPut(ApiUrls.PRODUCT_ADMIN_ACTIVATE.replace('{id}', product.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === product.id).removed = false;
                        this.setState({data: data});
                    }
                });
        } else {
            Request.doPut(ApiUrls.PRODUCT_ADMIN_DEACTIVATE.replace('{id}', product.id))
                .then(responce => {
                    if (responce.ok) {
                        const data = this.state.data.slice();
                        data.find(d => d.id === product.id).removed = true;
                        this.setState({data: data});
                    }
                });
        }
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(p => {
            return [
                t(`label.${p.name}.name`),
                <div className={`swtitch-row-${p.removed ? 'off' : 'on'}`}
                     onClick={() => this.changeStatus(p)}
                />,
                <div className='edit' onClick={() => this.props.onToDetails(p.id)}/>
            ]
        })
    };

    getForm = () => {
        if (this.props.showEmptyForm) {
            return <Form
                className='product-dialogbox'
                onClose={this.props.onCloseForm}
            >
                <ProductForm onSave={this.addProduct}/>
            </Form>
        }
    };

    addProduct = (product) => {
        const products = this.state.data.slice();
        products.splice(0, 0, product);
        this.setState({data: products});
        this.props.onCloseForm();
    };

    render() {
        return <React.Fragment>
            {this.getForm()}
            <Table
                data={this.getData()}
                header={this.getHeader()}
                pageSize={10}
            />
        </React.Fragment>;
    }
}

export default withTranslation()(Products);