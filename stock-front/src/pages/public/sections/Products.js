import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import Button from '../../../components/input/Button';
import Slider from "../../../components/Slider";

class Products extends React.Component {

    renderProducts = () => {
        const {t} = this.props;
        return this.props.products.map(p =>
            <div className='product'
                 style={{
                     backgroundImage: `url("${p.picture}")`,
                     float: 'left',
                     listStyle: 'none',
                     position: 'relative',
                 }}>
                <h4>{t(`label.${p.name}.name`)}</h4>
                <div className='about-product'>
                    <div className='sell-product'>
                        <h5>{t('label.index.sell')}</h5>
                        <p className='count'>{p.sell.volume}</p><p
                        className='desc-count'>{t('label.index.requests_volume')}</p>
                        <p className='count'>{p.sell.price}</p><p
                        className='desc-count'>{t('label.index.average_price')}</p>
                        <Button className='background-green' onClick={this.props.onRegistration}>
                            {t('label.index.sell')} {t(`label.${p.name}.name`)}
                        </Button>
                    </div>
                    <div className='buy-product'>
                        <h5>{t('label.index.buy')}</h5>
                        <p className='count'>{p.buy.volume}</p><p
                        className='desc-count'>{t('label.index.requests_volume')}</p>
                        <p className='count'>{p.buy.price}</p><p
                        className='desc-count'>{t('label.index.average_price')}</p>
                        <Button className='background-blue' onClick={this.props.onRegistration}>
                            {t('label.index.buy')} {t(`label.${p.name}.name`)}
                        </Button>
                    </div>
                </div>
            </div>
        );
    };

    render() {
        const {t} = this.props;
        return <div className='container'>
            <span className='span_h3'>{t('label.index.products')}</span>
            <Slider elements={this.renderProducts()}/>
        </div>;
    }
}

Products.propTypes = {
    products: PropTypes.arrayOf(PropTypes.object).isRequired,
    onRegistration: PropTypes.func.isRequired
};

export default withTranslation()(Products);