// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false
};

/* this is postman mock server url.Replace it with api server url
export const apiURL = 'https://b118a84c-76b8-4084-b2cd-8b0c9a18b7a7.mock.pstmn.io/api/';*/
export const apiURL = 'http://localhost:8080/';
export const appUrl = 'http://soccer.mooddit.com/';
export const imageBucket = 'https://vkangames.s3.ap-south-1.amazonaws.com/';
export const redirectUrl = appUrl + 'redirect/enriched';
export const providers = {
  TPAY: 'tpay',
  TIMWE: 'timwe'
};
export const unSub = {
  tpay : {
    ORANGE: '5033',
    VODAFONE: '6699',
    WE: '4041'
  }
};


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
