import { AfterViewInit, Component, Input, OnChanges, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPonude, Ponude } from '../ponude.model';

import { PonudeService } from '../service/ponude.service';
import { PonudeDeleteDialogComponent } from '../delete/ponude-delete-dialog.component';
import { Account } from '../../../core/auth/account.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AccountService } from '../../../core/auth/account.service';

@Component({
  selector: 'jhi-ponude',
  templateUrl: './ponude.component.html',
  styleUrls: ['./ponude.component.scss'],
})
export class PonudeComponent implements AfterViewInit, OnChanges {
  account: Account | null = null;
  ukupnaPonudjena?: number;
  ponude?: IPonude[];
  // public resourceUrlExcelDownload = SERVER_API_URL + 'api/specifikacije/file';
  public displayedColumns = [
    'sifra postupka',
    'sifraPonude',
    'brojPartije',
    'sifra ponudjaca',
    'naziv proizvodjaca',
    'zasticeni naziv',
    'ponudjena vrijednost',
    'rok isporuke',
    'edit',
    'delete',
  ];

  public dataSource = new MatTableDataSource<IPonude>();

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak?: any;
  @ViewChild('fileInput') fileInput: any;
  message: string | undefined;

  constructor(
    protected ponudeService: PonudeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  public getSifraPostupka(): void {
    this.ponudeService.findSiftraPostupak(this.postupak).subscribe((res: IPonude[]) => {
      this.dataSource.data = res;
    });
  }
  // public loadAll(): void {
  //   this.ponudeService.query().subscribe((res: HttpResponse<IPonude[]>) => {
  //     this.dataSource.data = res.body ?? [];
  //   });
  // }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  // doFilter = (iznos: string): any => {
  //   this.dataSource.filter = iznos.trim().toLocaleLowerCase();
  // };
  doFilter = (iznos: string): any => {
    this.dataSource.filter = iznos.trim().toLocaleLowerCase();
    this.ukupnaPonudjena = this.dataSource.filteredData.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  };
  getTotalCost(): any {
    return this.ponude?.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  }

  delete(ponude: IPonude): void {
    const modalRef = this.modalService.open(PonudeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ponude = Ponude;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.getSifraPostupka();
      }
    });
  }
  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  // ngOnInit(): void {
  //   this.getSifraPostupka();
  //   // this.loadAll();
  // }
  ngOnChanges(): void {
    this.getSifraPostupka();
    // this.getSifraPostupkaPonudePonudjaci();
  }
}
