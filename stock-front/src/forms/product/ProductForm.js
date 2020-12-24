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

class ProductForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            product: {
                id: null,
                name: '',
                picture: '',
                removed: false,
            }
        };
    }

    save = () => {
        const product = {...this.state.product};
        Request.doPost(ApiUrls.PRODUCT_ADMIN, null, product)
            .then(async responce => {
                if (responce.ok) {
                    product.id = JSON.parse(await responce.text());
                    this.props.onSave(product);
                }
            });
    };

    render() {
        const {t} = this.props;
        return <form className="form" name="form">
            <h3>{t('label.product.productForm')}</h3>
            <div className="flex-input">
                <Input
                    id='Name'
                    label={t('label.product.name')}
                    name='name'
                    value={this.state.product.name}
                    onChange={(newValue) => this.setState({product: {...this.state.product, name: newValue}})}
                />

                <Input
                    id='Picture'
                    label={t('label.product.picture')}
                    name='picture'
                    value={this.state.product.picture}
                    onChange={(newValue) => this.setState({product: {...this.state.product, picture: newValue}})}
                />

                <div id="form-list">
                    <Button className="submit button" id="save" onClick={this.save}>
                        {t('label.button.save')}
                    </Button>
                </div>
            </div>
        </form>
    }
}

export default withTranslation()(ProductForm);