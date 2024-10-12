import {Component, inject, Input} from '@angular/core';
import {ModalComponent} from "../modal.component";
import {ErrorService} from '../../../services/error.service';

@Component({
  selector: 'app-error-modal',
  standalone: true,
  templateUrl: './error-modal.component.html',
  styleUrls: ['./error-modal.component.css'],
  imports: [ModalComponent]
})
export class ErrorModalComponent {
  @Input() title!: string;
  @Input() message!: string;
  constructor(private errorService: ErrorService) {}

  onClearError() {
    this.errorService.clearError();
  }
}