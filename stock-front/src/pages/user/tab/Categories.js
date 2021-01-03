import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from '../../../util/Request';
import ApiUrls from '../../../util/ApiUrls';
import Table from '../../../components/table/Table';
import Form from '../../../components/form/Form';
import CategoryForm from '../../../forms/category/CategoryForm';

class Categories extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        return Request.doGet(ApiUrls.PRODUCT_CATEGORIES_ADMIN.replace('{productId}', this.props.productId))
            .then(async response => {
                if (response.ok) {
                    this.setState({data: JSON.parse(await response.text())});
                }
            });
    }

    getHeader = () => {
        const {t} = this.props;
        return [
            {name: t('label.product.name'), weight: 1},
            {name: t('label.product.archive'), weight: 2},
        ]
    };

    changeStatus = (category) => {
        const baseUrl = category.removed
            ? ApiUrls.PRODUCT_CATEGORY_ADMIN_ACTIVATE
            : ApiUrls.PRODUCT_CATEGORY_ADMIN_DEACTIVATE;

        const url = baseUrl
            .replace('{id}', category.id)
            .replace('{productId}', this.props.productId);

        Request.doPut(url)
            .then(responce => {
                if (responce.ok) {
                    const data = this.state.data.slice();
                    data.find(d => d.id === category.id).removed = !category.removed;
                    this.setState({data: data});
                }
            });
    };

    getData = () => {
        const {t} = this.props;
        return this.state.data.map(c => {
            return [
                t(`label.${c.categoryName}.name`),
                <div className={`swtitch-row-${c.removed ? 'off' : 'on'}`}
                     onClick={() => this.changeStatus(c)}
                />
            ]
        })
    };

    getForm = () => {
        if (this.props.showEmptyForm) {
            return <Form
                className='product-dialogbox'
                onClose={this.props.onCloseForm}
            >
                <CategoryForm onSave={this.addCategory} productId={this.props.productId}/>
            </Form>
        }
    };

    addCategory = (category) => {
        const categories = this.state.data.slice();
        categories.splice(0, 0, category);
        this.setState({data: categories});
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

export default withTranslation()(Categories);