import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrvorangiraniComponent } from './list/prvorangirani.component';
import { PrvorangiraniDetailComponent } from './detail/prvorangirani-detail.component';
import { PrvorangiraniUpdateComponent } from './update/prvorangirani-update.component';
import { PrvorangiraniDeleteDialogComponent } from './delete/prvorangirani-delete-dialog.component';
import { PrvorangiraniRoutingModule } from './route/prvorangirani-routing.module';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableExporterModule } from 'mat-table-exporter';
import { JhMaterialModule } from '../../shared/jh-material.module';

@NgModule({
  imports: [
    SharedModule,
    PrvorangiraniRoutingModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatTableExporterModule,
    JhMaterialModule,
  ],
  declarations: [PrvorangiraniComponent, PrvorangiraniDetailComponent, PrvorangiraniUpdateComponent, PrvorangiraniDeleteDialogComponent],
  entryComponents: [PrvorangiraniDeleteDialogComponent],
  exports: [PrvorangiraniComponent],
})
export class PrvorangiraniModule {}
