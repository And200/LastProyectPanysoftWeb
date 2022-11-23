import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailAmountRecip, DetailAmountRecip } from '../detail-amount-recip.model';
import { DetailAmountRecipService } from '../service/detail-amount-recip.service';

@Injectable({ providedIn: 'root' })
export class DetailAmountRecipRoutingResolveService implements Resolve<IDetailAmountRecip> {
  constructor(protected service: DetailAmountRecipService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailAmountRecip> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detailAmountRecip: HttpResponse<DetailAmountRecip>) => {
          if (detailAmountRecip.body) {
            return of(detailAmountRecip.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailAmountRecip());
  }
}
