import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScriptService {
  private scripts: any = {};
  private myScripts = [];
  heLoaded: Subject<boolean> = new Subject<boolean>();
  constructor() { }

  add(n: string, url: string): void {
    this.myScripts.push({name: n, src: url});
    this.myScripts.forEach((script) => {
      this.scripts[script.name] = {};
      this.scripts[script.name].src = script.src;
    });
  }

  isScriptAdded(): any {
    return this.scripts.heSource;
  }

  load(): any {
    const promises: any[] = [];
    this.myScripts.forEach((script) => promises.push(this.loadScript(script.name)));
    return Promise.all(promises);
  }

  loadScript(name: string): any {
    return new Promise((resolve, reject) => {
      if (this.scripts[name]?.loaded) {
        resolve({ script: name, loaded: true, status: 'Already Loaded' });
      } else {
        if (!document.getElementById('heSource')) {
          const script = document.createElement('script');
          script.id = 'heSource';
          script.type = 'text/javascript';
          script.src = decodeURI(this.scripts[name].src);

          script.onload = () => {
            this.scripts[name].loaded = true;
            resolve({ script: name, loaded: true, status: 'Loaded' });
          };

          script.onerror = (error: any) => resolve({ script: name, loaded: false, status: 'Loaded' });
          document.getElementsByTagName('head')[0].appendChild(script);
        } else {
          const script  = document.getElementById('heSource');
          script.setAttribute('src', decodeURI(this.scripts[name].src));
        }
      }
    });
  }

  getHeLoaded(): any {
    return this.heLoaded;
  }
}
