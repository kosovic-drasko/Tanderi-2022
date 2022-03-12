import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ViewVrednovanjeComponent } from './list/view-vrednovanje.component';
import { ViewVrednovanjeDetailComponent } from './detail/view-vrednovanje-detail.component';
import { ViewVrednovanjeUpdateComponent } from './update/view-vrednovanje-update.component';
import { ViewVrednovanjeDeleteDialogComponent } from './delete/view-vrednovanje-delete-dialog.component';
import { ViewVrednovanjeRoutingModule } from './route/view-vrednovanje-routing.module';

@NgModule({
  imports: [SharedModule, ViewVrednovanjeRoutingModule],
  declarations: [
    ViewVrednovanjeComponent,
    ViewVrednovanjeDetailComponent,
    ViewVrednovanjeUpdateComponent,
    ViewVrednovanjeDeleteDialogComponent,
  ],
  entryComponents: [ViewVrednovanjeDeleteDialogComponent],
})
export class ViewVrednovanjeModule {}
