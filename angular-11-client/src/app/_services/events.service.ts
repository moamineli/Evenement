import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
const apiUrl = 'http://localhost:8085/api/events';
@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(private http: HttpClient) { }



  getEvents(page: number, size: number): Observable<any> {
    const paginationParams = {
      page: page,
      size: size
    };
    return this.http.post<any>(apiUrl, paginationParams)
      .pipe(
      );
  }

  getMyEvents(userid: number,paginationParams: any): Observable<any> {
    return this.http.post<any>(apiUrl+`/findEventsForUser/${userid}`, paginationParams)
      .pipe(
      );
  }
  addEvent(event: any): Observable<any> {
    return this.http.post<any>(apiUrl+'/add', event);
  }
  cancelEvent(id: number): Observable<any> {
    return this.http.put<any>(apiUrl+`/cancel/${id}`, {});
  }


  getEventsByLieu(lieu: string, paginationParams: any): Observable<any> {
    return this.http.post(apiUrl+`/byLieu/${lieu}`, paginationParams);
  }

  addUserToEvent(idUser: number, idEvent: number): Observable<any> {
    return this.http.put<any>(apiUrl+`/addUserToEvent/${idUser}/${idEvent}`, {});
  }
  deleteUserFromEvent(idUser: number, idEvent: number): Observable<any> {
    return this.http.put<any>(apiUrl+`/deleteUserFromEvent/${idUser}/${idEvent}`, {});
  }
  getUserByEvents(eventid: number,paginationParams: any): Observable<any> {
    return this.http.post<any>(apiUrl+`/findUsersByEvent/${eventid}`, paginationParams)
      .pipe(
      );
  }
}

