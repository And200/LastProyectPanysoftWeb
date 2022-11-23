import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetailSaleService } from '../service/detail-sale.service';
import { IDetailSale, DetailSale } from '../detail-sale.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IPresentation } from 'app/entities/presentation/presentation.model';
import { PresentationService } from 'app/entities/presentation/service/presentation.service';

import { DetailSaleUpdateComponent } from './detail-sale-update.component';

describe('DetailSale Management Update Component', () => {
  let comp: DetailSaleUpdateComponent;
  let fixture: ComponentFixture<DetailSaleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailSaleService: DetailSaleService;
  let productService: ProductService;
  let presentationService: PresentationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetailSaleUpdateComponent],
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
      .overrideTemplate(DetailSaleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailSaleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailSaleService = TestBed.inject(DetailSaleService);
    productService = TestBed.inject(ProductService);
    presentationService = TestBed.inject(PresentationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const detailSale: IDetailSale = { id: 456 };
      const product: IProduct = { id: 45817 };
      detailSale.product = product;

      const productCollection: IProduct[] = [{ id: 57654 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Presentation query and add missing value', () => {
      const detailSale: IDetailSale = { id: 456 };
      const presentation: IPresentation = { id: 50678 };
      detailSale.presentation = presentation;

      const presentationCollection: IPresentation[] = [{ id: 47048 }];
      jest.spyOn(presentationService, 'query').mockReturnValue(of(new HttpResponse({ body: presentationCollection })));
      const additionalPresentations = [presentation];
      const expectedCollection: IPresentation[] = [...additionalPresentations, ...presentationCollection];
      jest.spyOn(presentationService, 'addPresentationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      expect(presentationService.query).toHaveBeenCalled();
      expect(presentationService.addPresentationToCollectionIfMissing).toHaveBeenCalledWith(
        presentationCollection,
        ...additionalPresentations
      );
      expect(comp.presentationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailSale: IDetailSale = { id: 456 };
      const product: IProduct = { id: 59054 };
      detailSale.product = product;
      const presentation: IPresentation = { id: 17170 };
      detailSale.presentation = presentation;

      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(detailSale));
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.presentationsSharedCollection).toContain(presentation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailSale>>();
      const detailSale = { id: 123 };
      jest.spyOn(detailSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailSale }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailSaleService.update).toHaveBeenCalledWith(detailSale);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailSale>>();
      const detailSale = new DetailSale();
      jest.spyOn(detailSaleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailSale }));
      saveSubject.complete();

      // THEN
      expect(detailSaleService.create).toHaveBeenCalledWith(detailSale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DetailSale>>();
      const detailSale = { id: 123 };
      jest.spyOn(detailSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailSaleService.update).toHaveBeenCalledWith(detailSale);
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

    describe('trackPresentationById', () => {
      it('Should return tracked Presentation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPresentationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
