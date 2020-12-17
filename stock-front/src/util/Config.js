import ProdConfig from '../config-prod.json';
import TestConfig from '../config-test.json';
import DevConfig from '../local-config.json';

export default {
    getConfig: () => {
        if (process.env.NODE_ENV === 'production') {
            if (window && window.location && window.location.href
                && !window.location.href.startsWith('http://test.yourtrader.eu')
                && !window.location.href.startsWith('https://test.yourtrader.eu')) {

                return ProdConfig;
            } else {
                return TestConfig;
            }
        } else {
            return DevConfig;
        }
    }
}