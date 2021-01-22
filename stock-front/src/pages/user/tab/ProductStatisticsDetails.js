import React from 'react';
import {withTranslation} from 'react-i18next';
import Request from '../../../util/Request';
import ApiUrls from '../../../util/ApiUrls';
import Button from '../../../components/input/Button';

class ProductsStatistics extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            product: {
                name: '',
                picture: '',
                rows: []
            }
        }
    }

    componentDidMount() {
        Request.doGet(ApiUrls.PRODUCTS_STATISTICS_DETAILS.replace('{id}', this.props.productId))
            .then(async response => {
                if (response.ok) {
                    this.setState({product: JSON.parse(await response.text())});
                }
            });
    }

    render() {
        const {t} = this.props;
        return <div className='white-div'>
            <div className='header-white-div' style={{backgroundImage: `url('${this.state.product.picture}')`}}>
                <form className='form form-tradeOffers' name='form'>
                    <ul>
                        <li>
                            <input type='text' disabled={true} value={t(`label.${this.state.product.name}.name`)}/>
                        </li>
                    </ul>
                </form>
            </div>

            <div className='table-tradeOffers'>
                <div id='main-table_wrapper' className='dataTables_wrapper no-footer'>
                    <table id='main-table' className='display responsive nowrap dataTable no-footer dtr-inline'>
                        <thead>
                        <tr role='row'>
                            <th>{t('label.product.price')}</th>
                            <th>{t('label.product.lot_volume')}</th>
                            <th>{t('label.product.tender_volume')}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.product.rows.map(r =>
                            <tr key={`product-depails-for-${r.price}`}>
                                <td>{r.price}</td>
                                <td>{r.lotVolume}</td>
                                <td>{r.tenderVolume}</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>

            <form className='form form-tradeOffers-button '>
                <ul>
                    <li>
                        <Button className='submit fs-submit gray-button' onClick={this.props.onBack}>
                            {t('label.button.back')}
                        </Button>
                    </li>
                </ul>
            </form>
        </div>
    }
}

export default withTranslation()(ProductsStatistics);