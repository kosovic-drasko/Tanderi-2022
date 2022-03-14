import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ViewVrednovanjeComponent } from './list/view-vrednovanje.component';
import { ViewVrednovanjeDetailComponent } from './detail/view-vrednovanje-detail.component';
import { ViewVrednovanjeUpdateComponent } from './update/view-vrednovanje-update.component';
import { ViewVrednovanjeDeleteDialogComponent } from './delete/view-vrednovanje-delete-dialog.component';
import { ViewVrednovanjeRoutingModule } from './route/view-vrednovanje-routing.module';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableExporterModule } from 'mat-table-exporter';

@NgModule({
  imports: [SharedModule, ViewVrednovanjeRoutingModule, MatTableModule, MatPaginatorModule, MatSortModule, MatTableExporterModule],
  declarations: [
    ViewVrednovanjeComponent,
    ViewVrednovanjeDetailComponent,
    ViewVrednovanjeUpdateComponent,
    ViewVrednovanjeDeleteDialogComponent,
  ],
  entryComponents: [ViewVrednovanjeDeleteDialogComponent],
  exports: [ViewVrednovanjeComponent],
})
export class ViewVrednovanjeModule {}
