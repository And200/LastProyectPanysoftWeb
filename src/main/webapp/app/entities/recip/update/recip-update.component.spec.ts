import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RecipService } from '../service/recip.service';
import { IRecip, Recip } from '../recip.model';

import { RecipUpdateComponent } from './recip-update.component';

describe('Recip Management Update Component', () => {
  let comp: RecipUpdateComponent;
  let fixture: ComponentFixture<RecipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recipService: RecipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RecipUpdateComponent],
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
      .overrideTemplate(RecipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recipService = TestBed.inject(RecipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const recip: IRecip = { id: 456 };

      activatedRoute.data = of({ recip });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(recip));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recip>>();
      const recip = { id: 123 };
      jest.spyOn(recipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recip }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(recipService.update).toHaveBeenCalledWith(recip);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recip>>();
      const recip = new Recip();
      jest.spyOn(recipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recip }));
      saveSubject.complete();

      // THEN
      expect(recipService.create).toHaveBeenCalledWith(recip);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recip>>();
      const recip = { id: 123 };
      jest.spyOn(recipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recip });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recipService.update).toHaveBeenCalledWith(recip);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
