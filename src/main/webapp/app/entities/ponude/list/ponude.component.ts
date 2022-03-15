import { AfterViewInit, Component, Input, OnChanges, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPonude } from '../ponude.model';

import { PonudeService } from '../service/ponude.service';
import { PonudeDeleteDialogComponent } from '../delete/ponude-delete-dialog.component';
import { Account } from '../../../core/auth/account.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AccountService } from '../../../core/auth/account.service';
import { IPonudePonudjaci } from 'app/entities/ponude-ponudjaci/ponude-ponudjaci.model';

@Component({
  selector: 'jhi-ponude',
  templateUrl: './ponude.component.html',
  styleUrls: ['./ponude.component.scss'],
})
export class PonudeComponent implements AfterViewInit, OnChanges {
  ponude_ponudjaci?: IPonudePonudjaci[];
  ponude?: IPonude[];
  account: Account | null = null;
  // authSubscription?: Subscription;
  ukupnaPonudjena?: number;
  nadjiPonudjaca?: any;
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

  constructor(
    protected ponudeService: PonudeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  getTotalCost(): any {
    return this.ponude?.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  }

  public getSifraPostupka(): void {
    this.ponudeService.findSiftraPostupak(this.postupak).subscribe((res: IPonude[]) => {
      this.dataSource.data = res;
    });
  }

  public getSifraPonude(): void {
    this.ponudeService.findSiftraPonude(this.nadjiPonudjaca).subscribe((res: IPonude[]) => {
      this.dataSource.data = res;
      this.getTotalCost();
    });
  }

  public getSifraPostupkaPonudePonudjaci(): void {
    this.ponudeService.findSiftraPostupakPonudePonudjaci(this.postupak).subscribe((res: IPonudePonudjaci[]) => {
      this.ponude_ponudjaci = res;
      // eslint-disable-next-line no-console
      console.log('to je ---------', res);
    });
  }

  // deleteSifra(): void {
  //   this.ponudeService.deleteSifraPonude(this.nadjiPonudjaca).subscribe();
  //   this.getSifraPostupka();
  // }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  doFilter = (iznos: string): any => {
    this.dataSource.filter = iznos.trim().toLocaleLowerCase();
    this.ukupnaPonudjena = this.dataSource.filteredData.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  };

  ngOnChanges(): void {
    this.getSifraPostupka();
    this.getSifraPostupkaPonudePonudjaci();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  public getSifraPostupkaPonudes(): void {
    this.ponudeService.findSiftraPostupak(this.postupak).subscribe((res: IPonude[]) => {
      this.ponude = res;
    });
  }

  // uploadFile(): any {
  //   const formData = new FormData();
  //   formData.append('uploadfiles', this.fileInput.nativeElement.files[0]);
  //
  //   this.ponudeService.UploadExcel(formData).subscribe((result: { toString: () => string | undefined }) => {
  //     this.message = result.toString();
  //     this.getSifraPostupka();
  //   });
  // }
  // DownloadExcel(): void {
  //   window.location.href = this.resourceUrlExcelDownload;
  // }
  delete(ponude: IPonude[]): void {
    const modalRef = this.modalService.open(PonudeDeleteDialogComponent, { backdrop: 'static' });
    modalRef.componentInstance.ponude = ponude;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason: string) => {
      if (reason === 'deleted') {
        this.getSifraPostupka();
      }
    });
  }

  open(content: any): any {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
  }

  // open1(content1: any): any {
  //   this.modalService.open(content1, { ariaLabelledBy: 'modal-basic-title' });
  // }
  //
  // updateSelected(id: number): any {
  //   this.ponudeService.updatePersonSelected(id);
  // }
  //
  // deleteSelected(): void {
  //   this.ponudeService.deleteSelected();
  //   this.getSifraPostupka();
  // }
}
