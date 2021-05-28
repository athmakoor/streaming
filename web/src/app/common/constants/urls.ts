import { Config } from './config';

export class Urls {
    public static readonly HOST = Config.API_HOST;
    private static readonly API_PREFIX = Urls.HOST + '';
    private static readonly MDN = '/msisdn';
    private static readonly NOTIFICATION = '/notification';

    /*-------------------GAMES-----------------------*/
    private static readonly GAME_API_PREFIX = Urls.API_PREFIX + 'game';
    public static readonly GAME_LIST = Urls.GAME_API_PREFIX + '/all';
    public static readonly GAMES_GROUP_BY_CATEGORIES = Urls.GAME_API_PREFIX + '/home';
    public static readonly GAMES_BY_ID = Urls.GAME_API_PREFIX + '/id/';
    public static readonly GAMES_BY_CATEGORY = Urls.GAME_API_PREFIX + '/category/';
    public static readonly HOME_VIDEOS_API = Urls.API_PREFIX + 'api/video/home';
    public static readonly HOME_VIDEOS_API_CATEGORY = Urls.API_PREFIX + 'api/video/category/';

    /*-------------------------AUTH--------------------*/
    private static readonly AUTH_API_PREFIX = Urls.API_PREFIX + 'auth';
    public static readonly AUTH_API_CHECK_MDN = Urls.AUTH_API_PREFIX + '/check';
    public static readonly CHECK_AND_GENERATE_OTP = Urls.AUTH_API_PREFIX + '/checkAndGenerateOTP';
    public static readonly REGENERATE_OTP = Urls.AUTH_API_PREFIX + '/reGenerateOTP/';
    public static readonly VERIFY_OTP = Urls.AUTH_API_PREFIX + '/verifyOTP';
    public static readonly HE_SOURCE = Urls.AUTH_API_PREFIX + '/he/source/';
    public static readonly DIGEST = Urls.AUTH_API_PREFIX + '/he/digest';
    public static readonly SIGNATURE = Urls.AUTH_API_PREFIX + '/digest';

    /*-------------------------SUBSCRIPTIONS--------------------*/
    private static readonly SUBSCRIBE_API_PREFIX = Urls.API_PREFIX + 'api/subscribe';
    public  static readonly CHECK_SUBSCRIPTION_STATUS = Urls.SUBSCRIBE_API_PREFIX + '/status/';
    public static readonly ADD_SUBSCRIPTION_CONTRACT = Urls.SUBSCRIBE_API_PREFIX + '/AddSubscriptionContract';
    public static readonly TIMWE_CONSENT_URL = Urls.SUBSCRIBE_API_PREFIX + '/timwe/consentUrl';
    public static readonly UPDATE_SUBSCRIPTION_STATUS = Urls.SUBSCRIBE_API_PREFIX + '/timwe/updateStatus';
    public static readonly TIM_UN_SUBSCRIBE = Urls.SUBSCRIBE_API_PREFIX + '/unSubscribe';

     /*------------------------UNSUBSCRIBE-----------------------*/
     private static readonly UNSUBSCRIBE_API_PREFIX = Urls.API_PREFIX + 'auth/unSubscribe';
     public static readonly UN_SUBSCRIBE = Urls.UNSUBSCRIBE_API_PREFIX;

    /*-----------------------COMMON---------------------*/
    private static readonly DATA_URL_PRFIX = Urls.API_PREFIX + 'data';
    public static readonly OPERATORS_AND_COUNTRY_BY_LOCALE = Urls.DATA_URL_PRFIX + '/country-provider-map/';
    public static readonly PACKS_BY_OPERATOR = Urls.DATA_URL_PRFIX + '/subscription-packs/';
    public static readonly OPERATORS_AND_COUNTRY_BY_OPERATORCODE = Urls.DATA_URL_PRFIX + '/country-operator-map/operatorCode';


    public getMdnUrl(provider: string): string {
        return Urls.SUBSCRIBE_API_PREFIX + '/' + provider + '/' + Urls.MDN;
    }

    public getNotificationUrl(provider: string): string {
        return Urls.SUBSCRIBE_API_PREFIX + '/' + provider + '/' + Urls.NOTIFICATION;
    }
}
