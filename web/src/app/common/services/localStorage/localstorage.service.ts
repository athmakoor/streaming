import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { StorageKeys } from './../../constants/storagekeys';

@Injectable({
  providedIn: 'root'
})

export class LocalStorageService {

  constructor() { }

  setSessionId(sessionId): void {
    if (sessionId) {
      localStorage.setItem(StorageKeys.SESSION_KEY, sessionId);
    }
  }

  setContent(key: string, content: any): void {
    localStorage.setItem(key, JSON.stringify(content));
  }

  getSessionId(): string {
    return localStorage.getItem(StorageKeys.SESSION_KEY);
  }

  getContent(key: string): any {
    if (localStorage.getItem(key)) {
      return JSON.parse(localStorage.getItem(key));
    } else {
      return false;
    }
  }

  setPackData(mdn: string, pack: any): void {
    const data = new Map();
    const jsonObject = {};

    data.set(mdn, pack);

    data.forEach((value, key) => {
      jsonObject[key] = value;
    });

    this.setContent(StorageKeys.MSISDN_AVTIVE_PACK, JSON.stringify(jsonObject));
  }

  getPackData(mdn: string): any {
    const data = this.getContent(StorageKeys.MSISDN_AVTIVE_PACK);

    if (data) {
      const packData = JSON.parse(data);
      return packData[mdn];
    }

    return false;
  }
}
