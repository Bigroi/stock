import React from 'react';
import {withTranslation} from 'react-i18next';

class Advantages extends React.Component {


    render() {
        const {t} = this.props;
        return <div className='container'>
            <div className='advantage'>
                <div className='adv-icon'>
                    <img src='/img/organic.png' alt='YourTrader' title='YourTrader'/>
                </div>
                <p className='adv-title'>{t('label.index.speed_title')}</p>
                <p className='adv-desc'>{t('label.index.speed_description')}</p>
            </div>
            <div className='advantage'>
                <div className='adv-icon'>
                    <img src='/img/trusted.png' alt='YourTrader' title='YourTrader'/>
                </div>
                <p className='adv-title'>{t('label.index.trust_title')}</p>
                <p className='adv-desc'>{t('label.index.trust_description')}</p>
            </div>
            <div className='advantage'>
                <div className='adv-icon'>
                    <img src='/img/deal.png' alt='YourTrader' title='YourTrader'/>
                </div>
                <p className='adv-title'>{t('label.index.deal_title')}</p>
                <p className='adv-desc'>{t('label.index.deal_description')}</p>
            </div>
            <div className='advantage'>
                <div className='adv-icon'>
                    <img src='/img/platform.png' alt='YourTrader' title='YourTrader'/>
                </div>
                <p className='adv-title'>{t('label.index.convenient_title')}</p>
                <p className='adv-desc'>{t('label.index.convenient_description')}</p>
            </div>
        </div>;
    }
}

export default withTranslation()(Advantages);