// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  // profileBaseUrl: 'http://localhost:8080/api/profile/',
  // mediaBaseUrl: 'http://localhost:8080/api/media/',
  // authBaseUrl: 'http://localhost:8080/api/auth/',
  // campaignBaseUrl: 'http://localhost:8080/api/campaign/',
  // messageBaseUrl: 'http://localhost:8080/api/message/',

  profileBaseUrl: 'http://localhost:8085/',
  mediaBaseUrl: 'http://localhost:8083/',
  authBaseUrl: 'http://localhost:8081/',
  campaignBaseUrl: 'http://localhost:8082/',
  messageBaseUrl: 'http://localhost:8084/',
  notificationBaseUrl: 'http://localhost:8086/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
