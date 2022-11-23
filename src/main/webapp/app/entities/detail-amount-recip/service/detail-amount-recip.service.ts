import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailAmountRecip, getDetailAmountRecipIdentifier } from '../detail-amount-recip.model';

export type EntityResponseType = HttpResponse<IDetailAmountRecip>;
export type EntityArrayResponseType = HttpResponse<IDetailAmountRecip[]>;

@Injectable({ providedIn: 'root' })
export class DetailAmountRecipService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detail-amount-recips');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detailAmountRecip: IDetailAmountRecip): Observable<EntityResponseType> {
    return this.http.post<IDetailAmountRecip>(this.resourceUrl, detailAmountRecip, { observe: 'response' });
  }

  update(detailAmountRecip: IDetailAmountRecip): Observable<EntityResponseType> {
    return this.http.put<IDetailAmountRecip>(
      `${this.resourceUrl}/${getDetailAmountRecipIdentifier(detailAmountRecip) as number}`,
      detailAmountRecip,
      { observe: 'response' }
    );
  }

  partialUpdate(detailAmountRecip: IDetailAmountRecip): Observable<EntityResponseType> {
    return this.http.patch<IDetailAmountRecip>(
      `${this.resourceUrl}/${getDetailAmountRecipIdentifier(detailAmountRecip) as number}`,
      detailAmountRecip,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailAmountRecip>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailAmountRecip[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDetailAmountRecipToCollectionIfMissing(
    detailAmountRecipCollection: IDetailAmountRecip[],
    ...detailAmountRecipsToCheck: (IDetailAmountRecip | null | undefined)[]
  ): IDetailAmountRecip[] {
    const detailAmountRecips: IDetailAmountRecip[] = detailAmountRecipsToCheck.filter(isPresent);
    if (detailAmountRecips.length > 0) {
      const detailAmountRecipCollectionIdentifiers = detailAmountRecipCollection.map(
        detailAmountRecipItem => getDetailAmountRecipIdentifier(detailAmountRecipItem)!
      );
      const detailAmountRecipsToAdd = detailAmountRecips.filter(detailAmountRecipItem => {
        const detailAmountRecipIdentifier = getDetailAmountRecipIdentifier(detailAmountRecipItem);
        if (detailAmountRecipIdentifier == null || detailAmountRecipCollectionIdentifiers.includes(detailAmountRecipIdentifier)) {
          return false;
        }
        detailAmountRecipCollectionIdentifiers.push(detailAmountRecipIdentifier);
        return true;
      });
      return [...detailAmountRecipsToAdd, ...detailAmountRecipCollection];
    }
    return detailAmountRecipCollection;
  }
}
