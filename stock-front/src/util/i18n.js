import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import HttpBackend from 'i18next-http-backend'
import config from './Config'
import ApiUrls from '../util/ApiUrls';

console.log(config.getConfig().apiUrl + "/" + ApiUrls.LABELS);

i18n.use(HttpBackend).use(LanguageDetector).init({
    ns: ["translations"],
    defaultNS: "translations",

    fallbackLng: "EN",
    debug: false,

    keySeparator: false, // we use content as keys

    interpolation: {
        escapeValue: false,
        formatSeparator: ","
    },

    preload: ['EN', 'RU', 'PL'],

    backend: {
        loadPath: config.getConfig().apiUrl + "/" + ApiUrls.LABELS
    },

    react: {
        wait: true,
        useSuspense: false
    }
});

export default i18n;