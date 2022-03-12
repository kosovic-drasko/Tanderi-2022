import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IViewVrednovanje, ViewVrednovanje } from '../view-vrednovanje.model';
import { ViewVrednovanjeService } from '../service/view-vrednovanje.service';

@Component({
  selector: 'jhi-view-vrednovanje-update',
  templateUrl: './view-vrednovanje-update.component.html',
})
export class ViewVrednovanjeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sifraPostupka: [],
    sifraPonude: [],
    brojPartije: [],
    atc: [],
    inn: [],
    zasticeniNaziv: [],
    farmaceutskiOblikLijeka: [],
    jacinaLijeka: [],
    pakovanje: [],
    trazenaKolicina: [],
    procijenjenaVrijednost: [],
    ponudjenaVrijednost: [],
    rokIsporuke: [],
    nazivProizvodjaca: [],
    nazivPonudjaca: [],
    bodCijena: [],
    bodRok: [],
    bodUkupno: [],
  });

  constructor(
    protected viewVrednovanjeService: ViewVrednovanjeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ viewVrednovanje }) => {
      this.updateForm(viewVrednovanje);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const viewVrednovanje = this.createFromForm();
    if (viewVrednovanje.id !== undefined) {
      this.subscribeToSaveResponse(this.viewVrednovanjeService.update(viewVrednovanje));
    } else {
      this.subscribeToSaveResponse(this.viewVrednovanjeService.create(viewVrednovanje));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IViewVrednovanje>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(viewVrednovanje: IViewVrednovanje): void {
    this.editForm.patchValue({
      id: viewVrednovanje.id,
      sifraPostupka: viewVrednovanje.sifraPostupka,
      sifraPonude: viewVrednovanje.sifraPonude,
      brojPartije: viewVrednovanje.brojPartije,
      atc: viewVrednovanje.atc,
      inn: viewVrednovanje.inn,
      zasticeniNaziv: viewVrednovanje.zasticeniNaziv,
      farmaceutskiOblikLijeka: viewVrednovanje.farmaceutskiOblikLijeka,
      jacinaLijeka: viewVrednovanje.jacinaLijeka,
      pakovanje: viewVrednovanje.pakovanje,
      trazenaKolicina: viewVrednovanje.trazenaKolicina,
      procijenjenaVrijednost: viewVrednovanje.procijenjenaVrijednost,
      ponudjenaVrijednost: viewVrednovanje.ponudjenaVrijednost,
      rokIsporuke: viewVrednovanje.rokIsporuke,
      nazivProizvodjaca: viewVrednovanje.nazivProizvodjaca,
      nazivPonudjaca: viewVrednovanje.nazivPonudjaca,
      bodCijena: viewVrednovanje.bodCijena,
      bodRok: viewVrednovanje.bodRok,
      bodUkupno: viewVrednovanje.bodUkupno,
    });
  }

  protected createFromForm(): IViewVrednovanje {
    return {
      ...new ViewVrednovanje(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
      sifraPonude: this.editForm.get(['sifraPonude'])!.value,
      brojPartije: this.editForm.get(['brojPartije'])!.value,
      atc: this.editForm.get(['atc'])!.value,
      inn: this.editForm.get(['inn'])!.value,
      zasticeniNaziv: this.editForm.get(['zasticeniNaziv'])!.value,
      farmaceutskiOblikLijeka: this.editForm.get(['farmaceutskiOblikLijeka'])!.value,
      jacinaLijeka: this.editForm.get(['jacinaLijeka'])!.value,
      pakovanje: this.editForm.get(['pakovanje'])!.value,
      trazenaKolicina: this.editForm.get(['trazenaKolicina'])!.value,
      procijenjenaVrijednost: this.editForm.get(['procijenjenaVrijednost'])!.value,
      ponudjenaVrijednost: this.editForm.get(['ponudjenaVrijednost'])!.value,
      rokIsporuke: this.editForm.get(['rokIsporuke'])!.value,
      nazivProizvodjaca: this.editForm.get(['nazivProizvodjaca'])!.value,
      nazivPonudjaca: this.editForm.get(['nazivPonudjaca'])!.value,
      bodCijena: this.editForm.get(['bodCijena'])!.value,
      bodRok: this.editForm.get(['bodRok'])!.value,
      bodUkupno: this.editForm.get(['bodUkupno'])!.value,
    };
  }
}
