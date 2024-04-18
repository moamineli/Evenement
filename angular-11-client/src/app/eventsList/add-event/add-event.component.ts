import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EventsService} from "../../_services/events.service";
import {Router} from "@angular/router";
import {DataShareService} from "../../_services/data-share.service";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
  // @ts-ignore
  eventForm: FormGroup;
  eventtoedit:any;
  constructor(private fb: FormBuilder, private eventService: EventsService,private route: Router,private datashared:DataShareService) {
  }

  ngOnInit(): void {
    this.createForm();
    this.edit();
  }

  createForm() {
    this.eventForm = this.fb.group({
      titre: ['', Validators.required],
      description: ['', Validators.required],
      dateDebut: ['', Validators.required],
      dateFin: ['', Validators.required],
      lieu: ['', Validators.required],
      capacite: ['', Validators.required]
    });
  }
edit():void{
this.datashared.evenement.subscribe(value => this.eventtoedit=value);
    if(this.eventtoedit){
      this.eventForm = this.fb.group({
        id: [this.eventtoedit.id, Validators.required],
        titre: [this.eventtoedit.titre, Validators.required],
        description: [this.eventtoedit.description, Validators.required],
        dateDebut: [this.eventtoedit.dateDebut, Validators.required],
        dateFin: [this.eventtoedit.dateFin, Validators.required],
        lieu: [this.eventtoedit.lieu, Validators.required],
        capacite: [this.eventtoedit.capacite, Validators.required]
      });
    }
}

  onSubmit() {
      const eventData = this.eventForm.value;
      this.eventService.addEvent(eventData).subscribe(
        (result) => {
          this.route.navigate(['/events']);
          console.log(' added successfully:', result);
        },
        (error) => {
          console.error('Failed to add :', error);
        }
      );


    this.datashared.evenement = new BehaviorSubject<any[]>([]);

  }

}
