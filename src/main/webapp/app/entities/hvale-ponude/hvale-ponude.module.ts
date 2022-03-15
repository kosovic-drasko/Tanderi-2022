import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HvalePonudeComponent } from './list/hvale-ponude.component';
import { HvalePonudeDetailComponent } from './detail/hvale-ponude-detail.component';
import { HvalePonudeUpdateComponent } from './update/hvale-ponude-update.component';
import { HvalePonudeDeleteDialogComponent } from './delete/hvale-ponude-delete-dialog.component';
import { HvalePonudeRoutingModule } from './route/hvale-ponude-routing.module';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatTableExporterModule } from 'mat-table-exporter';
import { JhMaterialModule } from '../../shared/jh-material.module';

@NgModule({
  imports: [
    SharedModule,
    HvalePonudeRoutingModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatTableExporterModule,
    JhMaterialModule,
  ],
  declarations: [HvalePonudeComponent, HvalePonudeDetailComponent, HvalePonudeUpdateComponent, HvalePonudeDeleteDialogComponent],
  entryComponents: [HvalePonudeDeleteDialogComponent],
  exports: [HvalePonudeComponent],
})
export class HvalePonudeModule {}
