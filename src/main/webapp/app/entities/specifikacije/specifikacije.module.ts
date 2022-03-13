import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecifikacijeComponent } from './list/specifikacije.component';
import { SpecifikacijeDetailComponent } from './detail/specifikacije-detail.component';
import { SpecifikacijeUpdateComponent } from './update/specifikacije-update.component';
import { SpecifikacijeDeleteDialogComponent } from './delete/specifikacije-delete-dialog.component';
import { SpecifikacijeRoutingModule } from './route/specifikacije-routing.module';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  imports: [SharedModule, SpecifikacijeRoutingModule, MatTableModule, MatSortModule, MatPaginatorModule],
  declarations: [SpecifikacijeComponent, SpecifikacijeDetailComponent, SpecifikacijeUpdateComponent, SpecifikacijeDeleteDialogComponent],
  entryComponents: [SpecifikacijeDeleteDialogComponent],
  exports: [SpecifikacijeComponent],
})
export class SpecifikacijeModule {}
