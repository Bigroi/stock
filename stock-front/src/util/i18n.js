import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import HttpBackend from 'i18next-http-backend'
import config from './Config'
import ApiUrls from '../util/ApiUrls';

const supportedLanguages = ['EN', 'RU', 'PL'];

i18n.use(HttpBackend).use(LanguageDetector).init({
    ns: ['translations'],
    defaultNS: 'translations',

    fallbackLng: 'EN',
    debug: false,

    keySeparator: false, // we use content as keys

    interpolation: {
        escapeValue: false,
        formatSeparator: ','
    },

    preload: supportedLanguages,

    backend: {
        loadPath: config.getConfig().apiUrl + '/' + ApiUrls.LABELS
    },

    react: {
        wait: true,
        useSuspense: false
    }
});

export const languageTranslator = () => {
    return i18n.languages.map(l => l.toUpperCase()).find(l => supportedLanguages.find(sl => sl === l));
};

export default i18n;