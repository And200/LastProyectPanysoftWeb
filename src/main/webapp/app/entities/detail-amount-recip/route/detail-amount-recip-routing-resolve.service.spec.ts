import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDetailAmountRecip, DetailAmountRecip } from '../detail-amount-recip.model';
import { DetailAmountRecipService } from '../service/detail-amount-recip.service';

import { DetailAmountRecipRoutingResolveService } from './detail-amount-recip-routing-resolve.service';

describe('DetailAmountRecip routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DetailAmountRecipRoutingResolveService;
  let service: DetailAmountRecipService;
  let resultDetailAmountRecip: IDetailAmountRecip | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DetailAmountRecipRoutingResolveService);
    service = TestBed.inject(DetailAmountRecipService);
    resultDetailAmountRecip = undefined;
  });

  describe('resolve', () => {
    it('should return IDetailAmountRecip returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailAmountRecip = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailAmountRecip).toEqual({ id: 123 });
    });

    it('should return new IDetailAmountRecip if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailAmountRecip = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDetailAmountRecip).toEqual(new DetailAmountRecip());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DetailAmountRecip })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetailAmountRecip = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetailAmountRecip).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
