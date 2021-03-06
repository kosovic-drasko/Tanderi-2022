import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPonude, Ponude } from '../ponude.model';
import { PonudeService } from '../service/ponude.service';
import { PonudjaciService } from '../../ponudjaci/service/ponudjaci.service';
import { IPonudjaci } from '../../ponudjaci/ponudjaci.model';

@Component({
  selector: 'jhi-ponude-update',
  templateUrl: './ponude-update.component.html',
})
export class PonudeUpdateComponent implements OnInit {
  isSaving = false;
  ponudjacisCollection: IPonudjaci[] = [];
  editForm = this.fb.group({
    id: [],
    sifraPostupka: [null, [Validators.required]],
    sifraPonude: [null, [Validators.required]],
    brojPartije: [null, [Validators.required]],
    nazivProizvodjaca: [],
    zasticeniNaziv: [],
    ponudjenaVrijednost: [null, [Validators.required]],
    rokIsporuke: [],
    sifraPonudjaca: [],
    ponudjaci: [],
    selected: [],
    jedinicnaCijena: [],
  });

  constructor(
    protected ponudeService: PonudeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected ponudjaciService: PonudjaciService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ponude }) => {
      this.updateForm(ponude);

      this.loadRelationshipsOptions();
    });
  }
  protected loadRelationshipsOptions(): void {
    this.ponudjaciService
      .query({ filter: 'ponude-is-null' })
      .pipe(map((res: HttpResponse<IPonudjaci[]>) => res.body ?? []))
      .pipe(
        map((ponudjacis: IPonudjaci[]) =>
          this.ponudjaciService.addPonudjaciToCollectionIfMissing(ponudjacis, this.editForm.get('ponudjaci')!.value)
        )
      )
      .subscribe((ponudjacis: IPonudjaci[]) => (this.ponudjacisCollection = ponudjacis));
  }
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ponude = this.createFromForm();
    if (ponude.id !== undefined) {
      this.subscribeToSaveResponse(this.ponudeService.update(ponude));
    } else {
      this.subscribeToSaveResponse(this.ponudeService.create(ponude));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPonude>>): void {
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

  protected updateForm(ponude: IPonude): void {
    this.editForm.patchValue({
      id: ponude.id,
      sifraPostupka: ponude.sifraPostupka,
      sifraPonude: ponude.sifraPonude,
      brojPartije: ponude.brojPartije,
      nazivProizvodjaca: ponude.nazivProizvodjaca,
      zasticeniNaziv: ponude.zasticeniNaziv,
      ponudjenaVrijednost: ponude.ponudjenaVrijednost,
      rokIsporuke: ponude.rokIsporuke,
      sifraPonudjaca: ponude.sifraPonudjaca,
      ponudjaci: ponude.ponudjaci,
      selected: ponude.selected,
      jedinicnaCijena: ponude.jedinicnaCijena,
    });
  }

  protected createFromForm(): IPonude {
    return {
      ...new Ponude(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
      sifraPonude: this.editForm.get(['sifraPonude'])!.value,
      brojPartije: this.editForm.get(['brojPartije'])!.value,
      nazivProizvodjaca: this.editForm.get(['nazivProizvodjaca'])!.value,
      zasticeniNaziv: this.editForm.get(['zasticeniNaziv'])!.value,
      ponudjenaVrijednost: this.editForm.get(['ponudjenaVrijednost'])!.value,
      rokIsporuke: this.editForm.get(['rokIsporuke'])!.value,
      sifraPonudjaca: this.editForm.get(['sifraPonudjaca'])!.value,
      ponudjaci: this.editForm.get(['ponudjaci'])!.value,
      selected: this.editForm.get(['selected'])!.value,
      jedinicnaCijena: this.editForm.get(['jedinicnaCijena'])!.value,
    };
  }
}
