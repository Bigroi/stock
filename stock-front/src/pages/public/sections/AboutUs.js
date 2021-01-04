import React from 'react';
import {withTranslation} from 'react-i18next';

class AboutUs extends React.Component {


    render() {
        const {t} = this.props;
        return <div className='container'>
            <div>
                <span className='span_h3'>{t('label.index.about_us_title')}</span>
                <p>{t('label.index.about_us_text_one')}</p>
                <p>{t('label.index.about_us_text_two')}</p>
            </div>
            <div>
                <video width='640' height='480' controls>
                    <source src='/video/demo.mp4' type='video/mp4'/>
                </video>
            </div>
        </div>;
    }
}

export default withTranslation()(AboutUs);