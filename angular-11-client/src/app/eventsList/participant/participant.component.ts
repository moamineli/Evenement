import { Component, OnInit } from '@angular/core';
import {EventsService} from "../../_services/events.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../_services/token-storage.service";

@Component({
  selector: 'app-participant',
  templateUrl: './participant.component.html',
  styleUrls: ['./participant.component.css']
})
export class ParticipantComponent implements OnInit {
  participant: any[] = [];
  idevent : any;
  totalElements: number = 0;
  pageSize: number = 5;
  currentPage: number = 0;
  user:any;
  roles: any;
  constructor(private eventService: EventsService,private tokenStorageService: TokenStorageService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.tokenStorageService.getUser();
    this.roles = this.user.roles;
    this.idevent = this.route.snapshot.queryParamMap.get('id');
     this.idevent = atob(this.idevent);
     this.FindAllParticipant();

  }
  FindAllParticipant() {
    const paginationParams = { page: 0, size: 10 };
    this.eventService.getUserByEvents(this.idevent,paginationParams).subscribe(
      value => this.participant=value.content
    );
  }
  deleteUserFromEvent( iduser: number): void {
    this.eventService.deleteUserFromEvent(iduser, this.idevent).subscribe();
    this.FindAllParticipant();

  }
  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.FindAllParticipant();
  }
}
