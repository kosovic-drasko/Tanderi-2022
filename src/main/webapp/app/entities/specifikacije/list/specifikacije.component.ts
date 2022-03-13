import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecifikacije } from '../specifikacije.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { SpecifikacijeService } from '../service/specifikacije.service';
import { SpecifikacijeDeleteDialogComponent } from '../delete/specifikacije-delete-dialog.component';
import { Account } from '../../../core/auth/account.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AccountService } from '../../../core/auth/account.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'jhi-specifikacije',
  templateUrl: './specifikacije.component.html',
  styleUrls: ['./specifikacije.scss'],
})
export class SpecifikacijeComponent implements OnInit {
  specifikacijes?: HttpResponse<ISpecifikacije[]>;
  account: Account | null = null;
  authSubscription?: Subscription;

  public resourceUrlExcelDownload = SERVER_API_URL + 'api/specifikacije/file';
  public displayedColumns = [
    'sifra postupka',
    'broj partije',
    'atc',
    'inn',
    'farmaceutski oblik',
    'jacina lijeka',
    'trazena kolicina',
    'pakovanje',
    'jedinica mjere',
    'procijenjena vrijednost',
    'delete',
    'edit',
  ];

  public dataSource = new MatTableDataSource<ISpecifikacije>();

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak?: any;
  @ViewChild('fileInput') fileInput: any;
  message: string | undefined;

  constructor(
    protected specifikacijaService: SpecifikacijeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    private accountService: AccountService,
    protected dialog: MatDialog
  ) {}

  public getSifraPostupka(): void {
    this.specifikacijaService.findSiftraPostupak(this.postupak).subscribe((res: ISpecifikacije[]) => {
      this.dataSource.data = res;
    });
  }

  delete(specifikacije: ISpecifikacije[]): void {
    const modalRef = this.modalService.open(SpecifikacijeDeleteDialogComponent, { backdrop: 'static' });
    modalRef.componentInstance.specifikacije = specifikacije;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason: string) => {
      if (reason === 'deleted') {
        this.getSifraPostupka();
      }
    });
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  doFilter = (iznos: string): any => {
    this.dataSource.filter = iznos.trim().toLocaleLowerCase();
  };

  ngOnChanges(): void {
    this.getSifraPostupka();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  ngOnInit(): void {
    this.getSifraPostupka();
  }

  // uploadFile(): any {
  //   const formData = new FormData();
  //   formData.append('uploadfiles', this.fileInput.nativeElement.files[0]);
  //
  //   this.specifikacijaService.UploadExcelSprcifikacije(formData).subscribe((result: { toString: () => string | undefined }) => {
  //     this.message = result.toString();
  //     this.getSifraPostupka();
  //   });
  // }
  DownloadExcel(): void {
    window.location.href = this.resourceUrlExcelDownload;
  }
}
