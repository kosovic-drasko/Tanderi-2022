import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPrvorangirani, Prvorangirani } from '../prvorangirani.model';
import { PrvorangiraniService } from '../service/prvorangirani.service';

@Component({
  selector: 'jhi-prvorangirani-update',
  templateUrl: './prvorangirani-update.component.html',
})
export class PrvorangiraniUpdateComponent implements OnInit {
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
    nazivPonudjaca: [],
    bodCijena: [],
    bodRok: [],
    bodUkupno: [],
  });

  constructor(protected prvorangiraniService: PrvorangiraniService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prvorangirani }) => {
      this.updateForm(prvorangirani);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prvorangirani = this.createFromForm();
    if (prvorangirani.id !== undefined) {
      this.subscribeToSaveResponse(this.prvorangiraniService.update(prvorangirani));
    } else {
      this.subscribeToSaveResponse(this.prvorangiraniService.create(prvorangirani));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrvorangirani>>): void {
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

  protected updateForm(prvorangirani: IPrvorangirani): void {
    this.editForm.patchValue({
      id: prvorangirani.id,
      sifraPostupka: prvorangirani.sifraPostupka,
      sifraPonude: prvorangirani.sifraPonude,
      brojPartije: prvorangirani.brojPartije,
      atc: prvorangirani.atc,
      inn: prvorangirani.inn,
      zasticeniNaziv: prvorangirani.zasticeniNaziv,
      farmaceutskiOblikLijeka: prvorangirani.farmaceutskiOblikLijeka,
      jacinaLijeka: prvorangirani.jacinaLijeka,
      pakovanje: prvorangirani.pakovanje,
      trazenaKolicina: prvorangirani.trazenaKolicina,
      procijenjenaVrijednost: prvorangirani.procijenjenaVrijednost,
      ponudjenaVrijednost: prvorangirani.ponudjenaVrijednost,
      rokIsporuke: prvorangirani.rokIsporuke,
      nazivPonudjaca: prvorangirani.nazivPonudjaca,
      bodCijena: prvorangirani.bodCijena,
      bodRok: prvorangirani.bodRok,
      bodUkupno: prvorangirani.bodUkupno,
    });
  }

  protected createFromForm(): IPrvorangirani {
    return {
      ...new Prvorangirani(),
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
      nazivPonudjaca: this.editForm.get(['nazivPonudjaca'])!.value,
      bodCijena: this.editForm.get(['bodCijena'])!.value,
      bodRok: this.editForm.get(['bodRok'])!.value,
      bodUkupno: this.editForm.get(['bodUkupno'])!.value,
    };
  }
}
