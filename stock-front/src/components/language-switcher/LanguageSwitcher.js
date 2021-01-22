import React from 'react';
import {withTranslation} from 'react-i18next';

/**
 * current: String
 * label: String
 * menuClass: String
 * onChange: function
 */
class LanguageSwitcher extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current: null,
            menuActivated: false
        }
    }

    SUPPORTED_LANGUAGES = ['EN', 'PL', 'RU'];

    getCurrent = () => {
        return this.state.current || this.props.current;
    };

    changeLanguage = (lang) => {
        this.props.onChange(lang);
        this.setState({menuActivated: false, current: lang})
    };

    getMenu = () => {
        const {t} = this.props;
        if (this.state.menuActivated) {
            return <div className={`ui-selectmenu-menu ui-front ui-selectmenu-open ${this.props.menuClass}`}>
                <ul className='ui-menu ui-corner-bottom ui-widget ui-widget-content ui-menu-icons customicons'>
                    {this.SUPPORTED_LANGUAGES.map(l =>
                        <li key={`language-${l}`} className='ui-menu-item' onClick={() => this.changeLanguage(l)}>
                            <div className='ui-menu-item-wrapper'>
                                {t('label.lang.' + l)}<span className={`ui-icon  ${l}`}/>
                            </div>
                        </li>
                    )}
                </ul>
            </div>
        }
    };

    render() {
        const {t} = this.props;
        return <div>
            <label>{this.props.label}</label>
            <div className='languages'
                 onClick={() => this.setState({menuActivated: !this.state.menuActivated})}
                 ref={e => this.rootElement = e}
            >
                <span className='ui-selectmenu-button ui-button ui-widget ui-selectmenu-button-open ui-corner-top'>
                    <span className='ui-selectmenu-icon ui-icon ui-icon-triangle-1-s'/>
                    <span className='ui-selectmenu-text'
                          style={{backgroundImage: `url("/img/${this.getCurrent()}.png")`}}>
                        {t('label.lang.' + this.getCurrent())}
                    </span>
                    {this.getMenu()}
                </span>
            </div>
        </div>
    }

}

export default withTranslation()(LanguageSwitcher);