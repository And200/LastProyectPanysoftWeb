import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDetailAmountRecip, DetailAmountRecip } from '../detail-amount-recip.model';
import { DetailAmountRecipService } from '../service/detail-amount-recip.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IRecip } from 'app/entities/recip/recip.model';
import { RecipService } from 'app/entities/recip/service/recip.service';

@Component({
  selector: 'panysoft-detail-amount-recip-update',
  templateUrl: './detail-amount-recip-update.component.html',
})
export class DetailAmountRecipUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  recipsSharedCollection: IRecip[] = [];

  editForm = this.fb.group({
    id: [],
    amountProduct: [null, [Validators.required]],
    product: [null, Validators.required],
    recip: [null, Validators.required],
  });

  constructor(
    protected detailAmountRecipService: DetailAmountRecipService,
    protected productService: ProductService,
    protected recipService: RecipService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailAmountRecip }) => {
      this.updateForm(detailAmountRecip);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailAmountRecip = this.createFromForm();
    if (detailAmountRecip.id !== undefined) {
      this.subscribeToSaveResponse(this.detailAmountRecipService.update(detailAmountRecip));
    } else {
      this.subscribeToSaveResponse(this.detailAmountRecipService.create(detailAmountRecip));
    }
  }

  trackProductById(_index: number, item: IProduct): number {
    return item.id!;
  }

  trackRecipById(_index: number, item: IRecip): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailAmountRecip>>): void {
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

  protected updateForm(detailAmountRecip: IDetailAmountRecip): void {
    this.editForm.patchValue({
      id: detailAmountRecip.id,
      amountProduct: detailAmountRecip.amountProduct,
      product: detailAmountRecip.product,
      recip: detailAmountRecip.recip,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      detailAmountRecip.product
    );
    this.recipsSharedCollection = this.recipService.addRecipToCollectionIfMissing(this.recipsSharedCollection, detailAmountRecip.recip);
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('product')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.recipService
      .query()
      .pipe(map((res: HttpResponse<IRecip[]>) => res.body ?? []))
      .pipe(map((recips: IRecip[]) => this.recipService.addRecipToCollectionIfMissing(recips, this.editForm.get('recip')!.value)))
      .subscribe((recips: IRecip[]) => (this.recipsSharedCollection = recips));
  }

  protected createFromForm(): IDetailAmountRecip {
    return {
      ...new DetailAmountRecip(),
      id: this.editForm.get(['id'])!.value,
      amountProduct: this.editForm.get(['amountProduct'])!.value,
      product: this.editForm.get(['product'])!.value,
      recip: this.editForm.get(['recip'])!.value,
    };
  }
}
