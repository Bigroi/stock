import React from 'react';
import {withTranslation} from 'react-i18next';
import Input from "../../components/input/Input";
import Button from "../../components/input/Button";
import ApiUrls from '../../util/ApiUrls';
import Request from '../../util/Request';

class CategoryForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            category: {
                id: null,
                categoryName: '',
                removed: false,
            }
        };
    }

    save = () => {
        const category = {...this.state.category};
        Request.doPost(ApiUrls.PRODUCT_CATEGORY_ADMIN.replace('{productId}', this.props.productId), null, category)
            .then(async response => {
                if (response.ok) {
                    category.id = JSON.parse(await response.text());
                    this.props.onSave(category);
                }
            });
    };

    render() {
        const {t} = this.props;
        return <form className="form" name="form">
            <h3>{t('label.product_category.category_form')}</h3>
            <div className="flex-input">
                <Input
                    id='Name'
                    label={t('label.product_category.name')}
                    name='name'
                    value={this.state.category.categoryName}
                    onChange={(newValue) => this.setState({
                        category: {...this.state.category, categoryName: newValue}
                    })}
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

export default withTranslation()(CategoryForm);