import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrvorangirani } from '../prvorangirani.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { PrvorangiraniService } from '../service/prvorangirani.service';
import { PrvorangiraniDeleteDialogComponent } from '../delete/prvorangirani-delete-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { PonudeService } from '../../ponude/service/ponude.service';

@Component({
  selector: 'jhi-prvorangirani',
  templateUrl: './prvorangirani.component.html',
  styleUrls: ['./prvorangirani.component.scss'],
})
export class PrvorangiraniComponent implements OnChanges, AfterViewInit {
  prvorangiranis?: IPrvorangirani[];
  // ponude_ponudjaci?: IPonudePonudjaci[];
  ukupnaPonudjena?: number | null | undefined;
  ukupnaProcijenjena?: number | null | undefined;
  nadjiPonudjaca?: any;
  public displayedColumns = [
    'sifra postupka',
    'sifra ponude',
    'broj partije',
    'atc',
    'inn',
    'zasticeni naziv',
    'farmaceutski oblik',
    'jacina lijeka',
    'pakovanje',
    'procijenjena vrijednost',
    'kolicina',
    'ponudjena vrijednost',
    'rok isporuke',
    'naziv ponudjaca',
    'naziv proizvodjaca',
    'bod cijena',
    'bod rok',
    'bod ukupno',
  ];

  public dataSource = new MatTableDataSource<IPrvorangirani>();
  sifraPostupka?: any;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @Input() postupak: any;

  constructor(protected prvorangiraniService: PrvorangiraniService, protected ponudeService: PonudeService) {}

  public getAllPrvorangiraniPostupak(): void {
    this.prvorangiraniService.findPostupak(this.postupak).subscribe((res: IPrvorangirani[]) => {
      this.dataSource.data = res;
      this.prvorangiranis = res;
      this.getTotalCost();
    });

    // public getSifraPostupkaPonudePonudjaci(): void {
    //   this.ponudeService.findSiftraPostupakPonudePonudjaci(this.postupak).subscribe((res: IPonudePonudjaci[]) => {
    //     // this.ponude_ponudjaci = res;
    //   });
  }

  // public getSifraPonude(): void {
  //   this.prvorangiraniService.findPonude(this.nadjiPonudjaca).subscribe((res: IPrvorangirani[]) => {
  //     this.dataSource.data = res;
  //     this.prvorangiranis = res;
  //     this.getTotalCost();
  //     this.getTotalCostProcijenjena();
  //   });
  // }
  doFilter = (iznos: string): any => {
    this.dataSource.filter = iznos.trim().toLocaleLowerCase();
    this.ukupnaPonudjena = this.dataSource.filteredData.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
    this.ukupnaProcijenjena = this.dataSource.filteredData.map(t => t.procijenjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  };

  ngOnChanges(): void {
    this.getAllPrvorangiraniPostupak();
    this.getTotalCost();
    this.getTotalCostProcijenjena();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  getTotalCost(): number {
    return <number>this.prvorangiranis?.map(t => t.ponudjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  }

  getTotalCostProcijenjena(): any {
    return this.prvorangiranis?.map(t => t.procijenjenaVrijednost).reduce((acc, value) => acc! + value!, 0);
  }
}
