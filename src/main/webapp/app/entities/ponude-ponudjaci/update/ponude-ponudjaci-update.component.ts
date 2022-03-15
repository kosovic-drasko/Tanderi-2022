import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPonudePonudjaci, PonudePonudjaci } from '../ponude-ponudjaci.model';
import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';

@Component({
  selector: 'jhi-ponude-ponudjaci-update',
  templateUrl: './ponude-ponudjaci-update.component.html',
})
export class PonudePonudjaciUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sifraPostupka: [],
  });

  constructor(
    protected ponudePonudjaciService: PonudePonudjaciService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ponudePonudjaci }) => {
      this.updateForm(ponudePonudjaci);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ponudePonudjaci = this.createFromForm();
    if (ponudePonudjaci.id !== undefined) {
      this.subscribeToSaveResponse(this.ponudePonudjaciService.update(ponudePonudjaci));
    } else {
      this.subscribeToSaveResponse(this.ponudePonudjaciService.create(ponudePonudjaci));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPonudePonudjaci>>): void {
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

  protected updateForm(ponudePonudjaci: IPonudePonudjaci): void {
    this.editForm.patchValue({
      id: ponudePonudjaci.id,
      sifraPostupka: ponudePonudjaci.sifraPostupka,
    });
  }

  protected createFromForm(): IPonudePonudjaci {
    return {
      ...new PonudePonudjaci(),
      id: this.editForm.get(['id'])!.value,
      sifraPostupka: this.editForm.get(['sifraPostupka'])!.value,
    };
  }
}
