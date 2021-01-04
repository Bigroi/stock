import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import Button from '../../../components/input/Button';

class ForWhom extends React.Component {


    render() {
        const {t} = this.props;
        return <React.Fragment>
            <div className='for-farmers'>
                <div className='for-farm-cont'>
                    <span className='span_h3'>{t('label.index.for_farmers')}</span>
                    <ul>
                        <li>{t('label.index.for_farmers_li1')}</li>
                        <li>{t('label.index.for_farmers_li2')}</li>
                        <li>{t('label.index.for_farmers_li3')}</li>
                    </ul>
                    <Button className='start-trade background-green registration-button'
                            onClick={this.props.onRegistration}
                    >
                        {t('label.button.start_trade_now')}
                    </Button>
                </div>
            </div>
            <div className='for-buyers'>
                <div className='for-buyers-cont'>
                    <span className='span_h3'>{t('label.index.for_buyers')}</span>
                    <ul>
                        <li>{t('label.index.for_buyers_li1')}</li>
                        <li>{t('label.index.for_buyers_li2')}</li>
                        <li>{t('label.index.for_buyers_li3')}</li>
                    </ul>
                    <Button className='start-buy background-blue registration-button'
                            onClick={this.props.onRegistration}
                    >
                        {t('label.button.start_buy_now')}
                    </Button>
                </div>
            </div>
        </React.Fragment>;
    }
}

ForWhom.propTypes = {
    onRegistration: PropTypes.func.isRequired
};

export default withTranslation()(ForWhom);