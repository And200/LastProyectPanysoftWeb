import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRecip, Recip } from '../recip.model';
import { RecipService } from '../service/recip.service';

@Component({
  selector: 'panysoft-recip-update',
  templateUrl: './recip-update.component.html',
})
export class RecipUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameRecip: [null, [Validators.required, Validators.maxLength(30)]],
    estimatedPricePreparation: [null, [Validators.required]],
  });

  constructor(protected recipService: RecipService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recip }) => {
      this.updateForm(recip);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recip = this.createFromForm();
    if (recip.id !== undefined) {
      this.subscribeToSaveResponse(this.recipService.update(recip));
    } else {
      this.subscribeToSaveResponse(this.recipService.create(recip));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecip>>): void {
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

  protected updateForm(recip: IRecip): void {
    this.editForm.patchValue({
      id: recip.id,
      nameRecip: recip.nameRecip,
      estimatedPricePreparation: recip.estimatedPricePreparation,
    });
  }

  protected createFromForm(): IRecip {
    return {
      ...new Recip(),
      id: this.editForm.get(['id'])!.value,
      nameRecip: this.editForm.get(['nameRecip'])!.value,
      estimatedPricePreparation: this.editForm.get(['estimatedPricePreparation'])!.value,
    };
  }
}
