import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataShareService {
  // @ts-ignore
  public evenement = new BehaviorSubject<any[]>(null);


  constructor() { }
  saveevent(params:any[]): void {
    this.evenement.next(params);
  }
}
