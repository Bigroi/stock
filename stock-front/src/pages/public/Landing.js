import React from 'react';
import {withTranslation} from 'react-i18next';
import PropTypes from 'prop-types';
import Form from '../../components/form/Form';
import LoginForm from '../../forms/login/LoginForm';
import RegistrationForm from '../../forms/registration/RegistrationForm';
import Request from '../../util/Request';
import ApiUrls from '../../util/ApiUrls';
import Header from './sections/Header';
import Welcome from './sections/Welcome';
import Advantages from './sections/Advantages';
import Products from './sections/Products';
import ForWhom from './sections/ForWhom';
import AboutUs from './sections/AboutUs';
import Questions from './sections/Questions';
import PreviewPlatform from './sections/PreviewPlatform';
import Footer from './sections/Footer';

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

    onRegistration = () => {
        this.setState({form: 'registration'});
    };

    onLogin = () => {
        this.setState({form: 'login'});
    };

    render() {
        return <div className='wrapper'>
            {this.getForm()}
            <div className='content'>
                <Header onLogin={this.onLogin} onRegistration={this.onRegistration}/>
                <main>
                    <div className='front' id='ex1'>
                        <Welcome onRegistration={this.onRegistration}/>
                    </div>
                    <div className='advantages' id='ex2'>
                        <Advantages/>
                    </div>
                    <div className='products' id='ex3'>
                        <Products onRegistration={this.onRegistration} products={this.state.products}/>
                    </div>
                    <div className='for-whom' id='ex4'>
                        <ForWhom onRegistration={this.onRegistration}/>
                    </div>
                    <div className='about-us' id='about-us'>
                        <AboutUs/>
                    </div>
                    <div className='question' id=''>
                        <Questions/>
                    </div>
                    <div className='preview-platform'>
                        <PreviewPlatform onRegistration={this.onRegistration}/>
                    </div>
                </main>
            </div>
            <Footer onUp={() => window.scrollTo(0, 0)}/>
        </div>
    }
}

Landing.propTypes = {
    onLoginSuccess: PropTypes.func.isRequired
};

export default withTranslation()(Landing);