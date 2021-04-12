import { apiURL, appUrl, imageBucket, providers, redirectUrl, unSub } from 'src/environments/environment';

export const Config = {
    API_HOST: apiURL,
    APP_URL: appUrl,
    TOKEN_EXPIRATION_MILLI_SECONDS: '864000000',
    ERROR_PATH: '404',
    S3_ROOT_WITH_BUCKET: imageBucket,
    TPAY: providers.TPAY,
    TIMWE: providers.TIMWE,
    UNSUBS: unSub,
    REDIRECT_URL: redirectUrl,
    SUPPORTED_COUNTRIES: ['AE', 'EG', 'KE', 'IN'],
    COUNTRY_ZONE: {
        AE: 'ar_AE',
        EG: 'ar_EG',
        KE: 'en_KE',
        IN: 'ar_EG',
    },
    PROVIDER_BY_LOCALE: {
        ar_EG: providers.TPAY,
        en_KE: providers.TPAY,
        ar_AE: providers.TIMWE
    }
};
