import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetailAmountRecipDetailComponent } from './detail-amount-recip-detail.component';

describe('DetailAmountRecip Management Detail Component', () => {
  let comp: DetailAmountRecipDetailComponent;
  let fixture: ComponentFixture<DetailAmountRecipDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailAmountRecipDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detailAmountRecip: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetailAmountRecipDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetailAmountRecipDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detailAmountRecip on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detailAmountRecip).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
