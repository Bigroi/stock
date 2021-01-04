import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import Button from '../../../components/input/Button';

class Welcome extends React.Component {


    render() {
        const {t} = this.props;
        return <div className='container'>
            <span className='span_h1'>{t('label.index.description_title')}</span>
            <h1 className='front-desc'>{t('label.index.description')}</h1>
            <div className='front-desc-but'>
                <div className='reg-as'>{t('label.index.regestrate_as_farmer')} </div>
                <div className='reg-but'>
                    <Button className='req-far background-green registration-button'
                            onClick={this.props.onRegistration}
                    >
                        {t('label.index.farmer')}
                    </Button>
                    <div>{t('label.index.regestrate_as_buyer')}</div>
                    <Button className='req-buy background-blue registration-button' onClick={this.props.onRegistration}>
                        {t('label.index.buyer')}
                    </Button>
                </div>
            </div>
        </div>;
    }
}

Welcome.propTypes = {
    onRegistration: PropTypes.func.isRequired
};

export default withTranslation()(Welcome);