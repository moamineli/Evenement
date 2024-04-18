import { Component, OnInit } from '@angular/core';
import {EventsService} from "../../_services/events.service";
import {Router, Routes} from "@angular/router";
import {DataShareService} from "../../_services/data-share.service";
import {BehaviorSubject} from "rxjs";
import {TokenStorageService} from "../../_services/token-storage.service";

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})

export class EventsListComponent implements OnInit {
  events: any[] = [];
  totalElements: number = 0;
  pageSize: number = 5;
  currentPage: number = 0;
  lieu: string = '';
  user :any;
  roles!: string;
  constructor(private eventService: EventsService,private tokenStorageService: TokenStorageService, private route: Router, private dataShareService : DataShareService) { }

  ngOnInit(): void {
    this.user = this.tokenStorageService.getUser();
    this.getEvents();
    this.roles = this.user.roles;
  }
  getEvents(): void {
    this.eventService.getEvents(this.currentPage, this.pageSize)
      .subscribe(response => {
          this.events = response.content;
        this.totalElements = response.totalElements;
      });
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
      this.getEvents();
  }

  editEvent(event: any): void {
    this.dataShareService.saveevent(event);
    this.route.navigate(["/add"]);

  }
  addEvent(): void {
    this.dataShareService.evenement = new BehaviorSubject<any[]>([]);
    this.route.navigate(['/add']);

  }

  getEventsByLieu() {
    const paginationParams = { page: 0, size: 10 };
    this.eventService.getEventsByLieu(this.lieu, paginationParams)
      .subscribe((data: any) => {
        this.events = data.content;
      });
  }
  cancelEvent(eventId: number): void {
    this.eventService.cancelEvent(eventId).subscribe(value => console.log('event canceled'));
this.getEvents();
  }


  addUserToEvent(event: any): void {
    this.eventService.addUserToEvent(this.user.id, event.id).subscribe(() => {
      this.isUserRegistered(event);
      this.getEvents();
    });


  }
  deleteUserFromEvent( event: any): void {
    this.eventService.deleteUserFromEvent(this.user.id, event.id).subscribe(() => {
      this.isUserRegistered(event);
      this.getEvents();
    });
  }
  myEvents(): void {
    const paginationParams = { page: 0, size: 10 };
    this.eventService.getMyEvents(this.user.id, paginationParams)
      .subscribe((data: any) => {
        this.events = data.content;
      });
  }
  getParticipant(id : any): void {
    this.route.navigate(['/participant'], { queryParams: { id: btoa(id) } });
  }
  isUserRegistered(event: any): boolean {
    return event.listeUser.some((user: any) => user.id === user.id);
  }
}
