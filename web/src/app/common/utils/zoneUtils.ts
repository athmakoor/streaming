import { Config } from '../constants/config';

declare var moment: any;

export class ZoneUtils {

    getCountryLocale(): any {
        let locale: any;

        const zone = moment.tz.guess(true);
        const zoneAlias = this.getZoneAlliases(zone);
        console.log('Zone Detected: --->' + zone);

        const serviceCountries = Config.SUPPORTED_COUNTRIES;

        this.getZoneAlliases(zone);

        for (const c of serviceCountries) {
            const zoneList = moment.tz.zonesForCountry(c);

            if (zoneList.includes(zone) || zoneList.includes(zoneAlias)) {
                console.log('Zones of Country: --->' + c + '--->' + zoneList);
                locale = Config.COUNTRY_ZONE[c];

                break;
            }
        }

        return locale;
    }

    getZoneAlliases(zone: string): any {
        let sZone = zone.toLocaleLowerCase();
        sZone = sZone.replace('/', '_');

        let alias = moment.tz._links[sZone];
        alias = alias.replace('_', '/');
        alias = alias.split('/').map((s) => s.charAt(0).toUpperCase() + s.substring(1)).join('/');

        return alias;
    }
}
