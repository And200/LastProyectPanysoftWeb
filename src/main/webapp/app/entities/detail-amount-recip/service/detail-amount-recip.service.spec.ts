import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetailAmountRecip, DetailAmountRecip } from '../detail-amount-recip.model';

import { DetailAmountRecipService } from './detail-amount-recip.service';

describe('DetailAmountRecip Service', () => {
  let service: DetailAmountRecipService;
  let httpMock: HttpTestingController;
  let elemDefault: IDetailAmountRecip;
  let expectedResult: IDetailAmountRecip | IDetailAmountRecip[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailAmountRecipService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      amountProduct: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DetailAmountRecip', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DetailAmountRecip()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailAmountRecip', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          amountProduct: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetailAmountRecip', () => {
      const patchObject = Object.assign({}, new DetailAmountRecip());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetailAmountRecip', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          amountProduct: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DetailAmountRecip', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDetailAmountRecipToCollectionIfMissing', () => {
      it('should add a DetailAmountRecip to an empty array', () => {
        const detailAmountRecip: IDetailAmountRecip = { id: 123 };
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing([], detailAmountRecip);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailAmountRecip);
      });

      it('should not add a DetailAmountRecip to an array that contains it', () => {
        const detailAmountRecip: IDetailAmountRecip = { id: 123 };
        const detailAmountRecipCollection: IDetailAmountRecip[] = [
          {
            ...detailAmountRecip,
          },
          { id: 456 },
        ];
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing(detailAmountRecipCollection, detailAmountRecip);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailAmountRecip to an array that doesn't contain it", () => {
        const detailAmountRecip: IDetailAmountRecip = { id: 123 };
        const detailAmountRecipCollection: IDetailAmountRecip[] = [{ id: 456 }];
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing(detailAmountRecipCollection, detailAmountRecip);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailAmountRecip);
      });

      it('should add only unique DetailAmountRecip to an array', () => {
        const detailAmountRecipArray: IDetailAmountRecip[] = [{ id: 123 }, { id: 456 }, { id: 7887 }];
        const detailAmountRecipCollection: IDetailAmountRecip[] = [{ id: 123 }];
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing(detailAmountRecipCollection, ...detailAmountRecipArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailAmountRecip: IDetailAmountRecip = { id: 123 };
        const detailAmountRecip2: IDetailAmountRecip = { id: 456 };
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing([], detailAmountRecip, detailAmountRecip2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailAmountRecip);
        expect(expectedResult).toContain(detailAmountRecip2);
      });

      it('should accept null and undefined values', () => {
        const detailAmountRecip: IDetailAmountRecip = { id: 123 };
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing([], null, detailAmountRecip, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailAmountRecip);
      });

      it('should return initial array if no DetailAmountRecip is added', () => {
        const detailAmountRecipCollection: IDetailAmountRecip[] = [{ id: 123 }];
        expectedResult = service.addDetailAmountRecipToCollectionIfMissing(detailAmountRecipCollection, undefined, null);
        expect(expectedResult).toEqual(detailAmountRecipCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
