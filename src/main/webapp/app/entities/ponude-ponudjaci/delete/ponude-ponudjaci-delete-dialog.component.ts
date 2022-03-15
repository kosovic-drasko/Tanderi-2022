import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPonudePonudjaci } from '../ponude-ponudjaci.model';
import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';

@Component({
  templateUrl: './ponude-ponudjaci-delete-dialog.component.html',
})
export class PonudePonudjaciDeleteDialogComponent {
  ponudePonudjaci?: IPonudePonudjaci;

  constructor(protected ponudePonudjaciService: PonudePonudjaciService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ponudePonudjaciService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
