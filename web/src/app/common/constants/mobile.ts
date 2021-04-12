export class Mobile {
    static data = {
        Kuwait: { pattern: '^[569]{7}$', code: '965' },
        Qatar: { pattern: '^(33|55|66|77){6}$', code: '974' },
        Iraq: { pattern: '^[7]{9}$', code: '964' },
        Egypt: { pattern: '^[0-6]{1}[0-9]{1}[0-9]{7}$', code: '201'},
        Kenya: { pattern: '^(7)[0-9]{8}$', code: '254'},
        UAE: { pattern: '^[0-9]{9}$', code: '971' },
        Algeria: { pattern: '^[0-9]{9}$', code: '213' },
        Tunisia: { pattern: '^[0-9]{9}$', code: '216' },
        Bahrain: { pattern: '^[0-9]{9}$', code: '973' },
        Palestine: { pattern: '^[0-9]{9}$', code: '970' },
        Jordan: { pattern: '^(79|78|77)[0-9]{7}$', code: '962' },
        Oman: { pattern: '^(78|79|92|94|95|96|97)[0-9]{6}$', code: '968' },
        Ksa: { pattern: '^(5)[0-9]{8}$', code: '966' },
        Libya: { pattern: '^(91|92|94|95)[0-9]{7}$', code: '218' }
    };

    static byCountry(country: string): string {
        return Mobile.data[country];
    }
}
