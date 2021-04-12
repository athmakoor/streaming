export const environment = {
  production: true
};

export const apiURL = 'http://soccer.mooddit.com/api/';
export const appUrl = 'http://soccer.mooddit.com/';
export const imageBucket = 'https://vkangames.s3.ap-south-1.amazonaws.com/';
export const redirectUrl = appUrl + '/redirect/enriched';
export const providers = {
  ZAINKUWAIT: 'zain-kuwait',
  ZAINIRAQ: 'zain-iraq',
  OOREDOOQATAR: 'ooredoo-qatar',
  TPAY: 'tpay',
  TIMWE: 'timwe'
};
export const unSub = {
  'zain-kuwait' : {
    code: 'UNSUB',
    number: '5666',
    currency: 'FILS',
    pack: '600'
  },
  'zain-iraq' : {
    code: 'UNSUB',
    number: '5667',
    currency: 'IQD',
    pack: '1000'
  },
  'ooredoo-qatar' : {
    code: 'UNSUB',
    number: '5668',
    currency: 'QAR',
    pack: '10'
  },
  tpay : {
    ORANGE: '5033',
    VODAFONE: '6699',
    WE: '4041'
  }
};
