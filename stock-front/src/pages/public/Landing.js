import React from 'react';
import {withTranslation} from 'react-i18next';
import LanguageSwitcher from '../../components/language-switcher/LanguageSwitcher';
import i18n from 'i18next';
import Button from '../../components/input/Button';
import Form from '../../components/form/Form';
import LoginForm from '../../forms/login/LoginForm';
import RegistrationForm from "../../forms/registration/RegistrationForm";
import Request from "../../util/Request";
import ApiUrls from '../../util/ApiUrls';
import Slider from "../../components/Slider";

/**
 * onLoginSuccess: function
 */
class Landing extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            form: null,
            products: []
        }
    }

    componentDidMount() {
        Request.doGet(ApiUrls.PRODUCTS_STATISTICS)
            .then(async response => {
                if (response.ok) {
                    this.setState({products: JSON.parse(await response.text())});
                }
            });
    }

    getForm = () => {
        if (this.state.form === 'login') {
            return <Form
                className='login-dialogbox'
                onClose={() => this.setState({form: null})}
            >
                <LoginForm
                    toRegistration={() => this.setState({form: 'registration'})}
                    onLoginSuccess={this.props.onLoginSuccess}
                />
            </Form>
        } else if (this.state.form === 'registration') {
            return <Form
                className='registration-dialogbox'
                map={true}
                onClose={() => this.setState({form: null})}
            >
                <RegistrationForm
                    toLogin={() => this.setState({form: 'login'})}
                    onRegistrationSuccess={this.props.onLoginSuccess}
                />
            </Form>
        }
    };

    renderProducts = () => {
        const {t} = this.props;
        return this.state.products.map(p =>
            <div className="product"
                 style={{
                     backgroundImage: `url("${p.picture}")`,
                     float: 'left',
                     listStyle: 'none',
                     position: 'relative',
                 }}>
                <h4>{t(`label.${p.name}.name`)}</h4>
                <div className="about-product">
                    <div className="sell-product">
                        <h5>{t('label.index.sell')}</h5>
                        <p className="count">{p.sell.volume}</p><p
                        className="desc-count">{t('label.index.requests_volume')}</p>
                        <p className="count">{p.sell.price}</p><p
                        className="desc-count">{t('label.index.average_price')}</p>
                        <Button className="background-green" onClick={() => this.setState({form: 'registration'})}>
                            {t('label.index.sell')} {t(`label.${p.name}.name`)}
                        </Button>
                    </div>
                    <div className="buy-product">
                        <h5>{t('label.index.buy')}</h5>
                        <p className="count">{p.buy.volume}</p><p
                        className="desc-count">{t('label.index.requests_volume')}</p>
                        <p className="count">{p.buy.price}</p><p
                        className="desc-count">{t('label.index.average_price')}</p>
                        <Button className="background-blue" onClick={() => this.setState({form: 'registration'})}>
                            {t('label.index.buy')} {t(`label.${p.name}.name`)}
                        </Button>
                    </div>
                </div>
            </div>
        );
    };

    render() {
        const {t} = this.props;
        return <div className='wrapper'>
            {this.getForm()}
            <div className='content'>
                <header>
                    <div className='container'>
                        <img className='logo' src='/img/logo.png' alt='YourTrader' title='YourTrader'/>
                        <nav className='main-page-nav'>
                            <a href='#ex2'>{t('label.index.benefits')}</a>
                            <a href='#ex3'>{t('label.index.products')}</a>
                            <a href='#ex4'>{t('label.index.for_whom')}</a>
                            <a href='#ex5'>{t('label.index.try_it_now')}</a>
                        </nav>
                        <div className='buttons-login'>
                            <Button className='registration background-blue registration-button'
                                    onClick={() => this.setState({form: 'registration'})}>
                                {t('label.navigation.regestration')}
                            </Button>
                            <Button className='sign-in login-button' onClick={() => this.setState({form: 'login'})}>
                                {t('label.navigation.login')}
                            </Button>
                        </div>
                        <LanguageSwitcher
                            menuClass='selectmenu-menu-landing'
                            onChange={lang => i18n.changeLanguage(lang)}
                            current={i18n.language}
                        />
                    </div>
                </header>
                <main>
                    <div className='front' id='ex1'>
                        <div className='container'>
                            <span className='span_h1'>{t('label.index.description_title')}</span>
                            <h1 className='front-desc'>{t('label.index.description')}</h1>
                            <div className='front-desc-but'>
                                <div className='reg-as'>{t('label.index.regestrate_as_farmer')} </div>
                                <div className='reg-but'>
                                    <Button
                                        className='req-far background-green registration-button'
                                        onClick={() => this.setState({form: 'registration'})}
                                    >
                                        {t('label.index.farmer')}
                                    </Button>
                                    <div>{t('label.index.regestrate_as_buyer')}</div>
                                    <Button
                                        className='req-buy background-blue registration-button'
                                        onClick={() => this.setState({form: 'registration'})}
                                    >
                                        {t('label.index.buyer')}
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='advantages' id='ex2'>
                        <div className='container'>
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
                        </div>
                    </div>
                    <div className='products' id='ex3'>
                        <div className='container'>
                            <span className='span_h3'>{t('label.index.products')}</span>
                            <Slider elements={this.renderProducts()}/>
                        </div>
                    </div>
                    <div className='for-whom' id='ex4'>
                        <div className='for-farmers'>
                            <div className='for-farm-cont'>
                                <span className='span_h3'>{t('label.index.for_farmers')}</span>
                                <ul>
                                    <li>{t('label.index.for_farmers_li1')}</li>
                                    <li>{t('label.index.for_farmers_li2')}</li>
                                    <li>{t('label.index.for_farmers_li3')}</li>
                                </ul>
                                <Button
                                    className='start-trade background-green registration-button'
                                    onClick={() => this.setState({form: 'registration'})}
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
                                <Button
                                    className='start-buy background-blue registration-button'
                                    onClick={() => this.setState({form: 'registration'})}
                                >
                                    {t('label.button.start_buy_now')}
                                </Button>
                            </div>
                        </div>
                    </div>
                    <div className='about-us' id='about-us'>
                        <div className='container'>
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
                        </div>
                    </div>
                    <div className='question' id=''>
                        <div className='container'>
                            <div>
                                <span className='span_h3'>{t('label.index.frequently_asked_question')}</span>
                                <p className='answer'>{t('label.index.not_found_answer')}</p>
                                <p className='mail'><a href=''>info@yourtrader.eu</a></p>
                            </div>
                            <div>
                                <div className='question-answer'>
                                    <div>
                                        <div className='header-question'>
                                            {t('label.index.header_question_one')}
                                            <div className='question-close'/>
                                        </div>
                                        <div className='body-answer'>
                                            {t('label.index.body_answer_one')}
                                        </div>
                                    </div>
                                </div>
                                <div className='question-answer'>
                                    <div>
                                        <div className='header-question'>
                                            {t('label.index.header_question_two')}
                                            <div className='question-close'/>
                                        </div>
                                        <div className='body-answer'>
                                            {t('label.index.body_answer_two')}
                                        </div>
                                    </div>
                                </div>
                                <div className='question-answer'>
                                    <div>
                                        <div className='header-question'>
                                            {t('label.index.header_question_three')}
                                            <div className='question-close'/>
                                        </div>
                                        <div className='body-answer'>
                                            {t('label.index.body_answer_three')}
                                        </div>
                                    </div>
                                </div>
                                <div className='question-answer'>
                                    <div>
                                        <div className='header-question'>
                                            {t('label.index.header_question_four')}
                                            <div className='question-close'/>
                                        </div>
                                        <div className='body-answer'>
                                            {t('label.index.body_answer_four')}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='preview-platform'>
                        <div className='container'>
                            <div>
                                <span className='span_h3'>{t('label.index.responsive_title')}</span>
                                <p>{t('label.index.responsive_description')}</p>
                                <Button
                                    className='register background-green registration-button'
                                    onClick={() => this.setState({form: 'registration'})}
                                >
                                    {t('label.button.registrate')}
                                </Button>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
            <footer>
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
                <div className='button_up' onClick={() => window.scrollTo(0, 0)}/>
            </footer>
        </div>
    }
}

export default withTranslation()(Landing);