import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrvorangirani } from '../prvorangirani.model';
import { PrvorangiraniService } from '../service/prvorangirani.service';

@Component({
  templateUrl: './prvorangirani-delete-dialog.component.html',
})
export class PrvorangiraniDeleteDialogComponent {
  prvorangirani?: IPrvorangirani;

  constructor(protected prvorangiraniService: PrvorangiraniService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prvorangiraniService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
