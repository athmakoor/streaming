import { UrlTree, UrlSegmentGroup, PRIMARY_OUTLET, UrlSegment, Router } from '@angular/router';
import { AuthCookieService } from '../services/cookie/auth-cookie.service';

export class UrlParser {
    cookieService: any;

    constructor(cookieService: AuthCookieService) {
        this.cookieService = cookieService;
    }

    extractValues(router: Router, url: string, type: string): void {
        const tree: UrlTree = router.parseUrl(url);
        const g: UrlSegmentGroup = tree.root.children[PRIMARY_OUTLET];

        if (g) {
            const s: UrlSegment[] = g.segments;
            this.save(s, type);
        }
    }

    save(values: UrlSegment[], type: string): void {
        switch (type) {
            case 'subscribe':
                const p = values[0].path;
                const l = values[1].path;

                this.cookieService.setLocale(l);
                this.cookieService.setProvider(p);

                break;
            case 'games':
                const pp = values[0].path;
                const ll = values[1].path;

                this.cookieService.setLocale(ll);
                this.cookieService.setProvider(pp);
                break;

        }
    }
}
