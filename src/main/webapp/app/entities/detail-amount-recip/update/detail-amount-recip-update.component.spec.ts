import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetailAmountRecipService } from '../service/detail-amount-recip.service';
import { IDetailAmountRecip, DetailAmountRecip } from '../detail-amount-recip.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IRecip } from 'app/entities/recip/recip.model';
import { RecipService } from 'app/entities/recip/service/recip.service';

import { DetailAmountRecipUpdateComponent } from './detail-amount-recip-update.component';

describe('DetailAmountRecip Management Update Component', () => {
  let comp: DetailAmountRecipUpdateComponent;
  let fixture: ComponentFixture<DetailAmountRecipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailAmountRecipService: DetailAmountRecipService;
  let productService: ProductService;
  let recipService: RecipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetailAmountRecipUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DetailAmountRecipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailAmountRecipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailAmountRecipService = TestBed.inject(DetailAmountRecipService);
    productService = TestBed.inject(ProductService);
    recipService = TestBed.inject(RecipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const detailAmountRecip: IDetailAmountRecip = { id: 456 };
      const product: IProduct = { id: 65455 };
      detailAmountRecip.product = product;

      const productCollection: IProduct[] = [{ id: 81557 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Recip query and add missing value', () => {
      const detailAmountRecip: IDetailAmountRecip = { id: 456 };
      const recip: IRecip = { id: 37861 };
      detailAmountRecip.recip = recip;

      const recipCollection: IRecip[] = [{ id: 5943 }];
      jest.spyOn(recipService, 'query').mockReturnValue(of(new HttpResponse({ body: recipCollection })));
      const additionalRecips = [recip];
      const expectedCollection: IRecip[] = [...additionalRecips, ...recipCollection];
      jest.spyOn(recipService, 'addRecipToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      expect(recipService.query).toHaveBeenCalled();
      expect(recipService.addRecipToCollectionIfMissing).toHaveBeenCalledWith(recipCollection, ...additionalRecips);
      expect(comp.recipsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailAmountRecip: IDetailAmountRecip = { id: 456 };
      const product: IProduct = { id: 78368 };
      detailAmountRecip.product = product;
      const recip: IRecip = { id: 50508 };
      detailAmountRecip.recip = recip;

      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(detailAmountRecip));
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.recipsSharedCollection).toContain(recip);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailAmountRecip>>();
      const detailAmountRecip = { id: 123 };
      jest.spyOn(detailAmountRecipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailAmountRecip }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailAmountRecipService.update).toHaveBeenCalledWith(detailAmountRecip);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailAmountRecip>>();
      const detailAmountRecip = new DetailAmountRecip();
      jest.spyOn(detailAmountRecipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailAmountRecip }));
      saveSubject.complete();

      // THEN
      expect(detailAmountRecipService.create).toHaveBeenCalledWith(detailAmountRecip);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailAmountRecip>>();
      const detailAmountRecip = { id: 123 };
      jest.spyOn(detailAmountRecipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailAmountRecip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailAmountRecipService.update).toHaveBeenCalledWith(detailAmountRecip);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRecipById', () => {
      it('Should return tracked Recip primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRecipById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
