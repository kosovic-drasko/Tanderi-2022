import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IViewVrednovanje } from '../view-vrednovanje.model';
import { ViewVrednovanjeService } from '../service/view-vrednovanje.service';

@Component({
  templateUrl: './view-vrednovanje-delete-dialog.component.html',
})
export class ViewVrednovanjeDeleteDialogComponent {
  viewVrednovanje?: IViewVrednovanje;

  constructor(protected viewVrednovanjeService: ViewVrednovanjeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.viewVrednovanjeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
