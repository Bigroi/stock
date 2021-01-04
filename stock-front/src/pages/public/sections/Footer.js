import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';

class Footer extends React.Component {


    render() {
        const {t} = this.props;
        return <footer>
            <div className='container'>
                <div className='contacts'>
                    <p className='phone'>
                        {t('label.index.phone_prefix')}<span>{t('label.index.phone')}</span>
                    </p>
                    <p className='mail'><a href=''>{t('label.index.email')}</a></p>
                </div>
                <div className='copyright'>
                    {t('label.index.copyright')}
                </div>
            </div>
            <div className='button_up' onClick={this.props.onUp}/>
        </footer>;
    }
}

Footer.propTypes = {
    onUp: PropTypes.func.isRequired
};

export default withTranslation()(Footer);