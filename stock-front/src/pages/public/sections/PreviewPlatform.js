import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import Button from '../../../components/input/Button';

class PreviewPlatform extends React.Component {


    render() {
        const {t} = this.props;
        return <div className='container'>
            <div>
                <span className='span_h3'>{t('label.index.responsive_title')}</span>
                <p>{t('label.index.responsive_description')}</p>
                <Button className='register background-green registration-button' onClick={this.props.onRegistration}>
                    {t('label.button.registrate')}
                </Button>
            </div>
        </div>;
    }
}

PreviewPlatform.propTypes = {
    onRegistration: PropTypes.func.isRequired
};

export default withTranslation()(PreviewPlatform);