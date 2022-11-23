import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IProvider } from 'app/entities/provider/provider.model';
import { ProviderService } from 'app/entities/provider/service/provider.service';
import { IPresentation } from 'app/entities/presentation/presentation.model';
import { PresentationService } from 'app/entities/presentation/service/presentation.service';

@Component({
  selector: 'panysoft-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;

  categoriesSharedCollection: ICategory[] = [];
  providersSharedCollection: IProvider[] = [];
  presentationsSharedCollection: IPresentation[] = [];

  editForm = this.fb.group({
    id: [],
    productName: [null, [Validators.required, Validators.maxLength(50)]],
    buyPrice: [null, [Validators.required]],
    category: [null, Validators.required],
    provider: [null, Validators.required],
    presentation: [null, Validators.required],
  });

  constructor(
    protected productService: ProductService,
    protected categoryService: CategoryService,
    protected providerService: ProviderService,
    protected presentationService: PresentationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  trackCategoryById(_index: number, item: ICategory): number {
    return item.id!;
  }

  trackProviderById(_index: number, item: IProvider): number {
    return item.id!;
  }

  trackPresentationById(_index: number, item: IPresentation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productName: product.productName,
      buyPrice: product.buyPrice,
      category: product.category,
      provider: product.provider,
      presentation: product.presentation,
    });

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      product.category
    );
    this.providersSharedCollection = this.providerService.addProviderToCollectionIfMissing(
      this.providersSharedCollection,
      product.provider
    );
    this.presentationsSharedCollection = this.presentationService.addPresentationToCollectionIfMissing(
      this.presentationsSharedCollection,
      product.presentation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.providerService
      .query()
      .pipe(map((res: HttpResponse<IProvider[]>) => res.body ?? []))
      .pipe(
        map((providers: IProvider[]) =>
          this.providerService.addProviderToCollectionIfMissing(providers, this.editForm.get('provider')!.value)
        )
      )
      .subscribe((providers: IProvider[]) => (this.providersSharedCollection = providers));

    this.presentationService
      .query()
      .pipe(map((res: HttpResponse<IPresentation[]>) => res.body ?? []))
      .pipe(
        map((presentations: IPresentation[]) =>
          this.presentationService.addPresentationToCollectionIfMissing(presentations, this.editForm.get('presentation')!.value)
        )
      )
      .subscribe((presentations: IPresentation[]) => (this.presentationsSharedCollection = presentations));
  }

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      buyPrice: this.editForm.get(['buyPrice'])!.value,
      category: this.editForm.get(['category'])!.value,
      provider: this.editForm.get(['provider'])!.value,
      presentation: this.editForm.get(['presentation'])!.value,
    };
  }
}
