import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPostupci } from '../postupci.model';

import { PostupciService } from '../service/postupci.service';
import { PostupciDeleteDialogComponent } from '../delete/postupci-delete-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'jhi-postupci',
  templateUrl: './postupci.component.html',
  styleUrls: ['./postupci.scss'],
})
export class PostupciComponent implements OnInit {
  postupaks?: IPostupci[];

  public displayedColumns = ['sifra postupka', 'opis postupka', 'vrsta postupka', 'datum objave', 'broj tendera', 'delete', 'edit'];
  public dataSource = new MatTableDataSource<IPostupci>();

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(protected postupciService: PostupciService, protected modalService: NgbModal, public dialog: MatDialog) {}

  loadAll(): void {
    this.postupciService.query().subscribe((res: HttpResponse<IPostupci[]>) => {
      this.dataSource.data = res.body ?? [];
    });
  }

  delete(postupci: IPostupci[]): void {
    const modalRef = this.modalService.open(PostupciDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.postupci = postupci;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason: string) => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
}
