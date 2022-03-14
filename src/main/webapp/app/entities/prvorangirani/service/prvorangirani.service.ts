import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrvorangirani, getPrvorangiraniIdentifier } from '../prvorangirani.model';

export type EntityResponseType = HttpResponse<IPrvorangirani>;
export type EntityArrayResponseType = HttpResponse<IPrvorangirani[]>;

@Injectable({ providedIn: 'root' })
export class PrvorangiraniService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prvorangiranis');
  protected resourceUrlPostupak = this.applicationConfigService.getEndpointFor('api/prvorangirani');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(prvorangirani: IPrvorangirani): Observable<EntityResponseType> {
    return this.http.post<IPrvorangirani>(this.resourceUrl, prvorangirani, { observe: 'response' });
  }
  findPostupak(sifraPostupka: number): any {
    return this.http.get<IPrvorangirani>(`${this.resourceUrlPostupak}/${sifraPostupka}`);
  }

  update(prvorangirani: IPrvorangirani): Observable<EntityResponseType> {
    return this.http.put<IPrvorangirani>(`${this.resourceUrl}/${getPrvorangiraniIdentifier(prvorangirani) as number}`, prvorangirani, {
      observe: 'response',
    });
  }

  partialUpdate(prvorangirani: IPrvorangirani): Observable<EntityResponseType> {
    return this.http.patch<IPrvorangirani>(`${this.resourceUrl}/${getPrvorangiraniIdentifier(prvorangirani) as number}`, prvorangirani, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrvorangirani>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrvorangirani[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPrvorangiraniToCollectionIfMissing(
    prvorangiraniCollection: IPrvorangirani[],
    ...prvorangiranisToCheck: (IPrvorangirani | null | undefined)[]
  ): IPrvorangirani[] {
    const prvorangiranis: IPrvorangirani[] = prvorangiranisToCheck.filter(isPresent);
    if (prvorangiranis.length > 0) {
      const prvorangiraniCollectionIdentifiers = prvorangiraniCollection.map(
        prvorangiraniItem => getPrvorangiraniIdentifier(prvorangiraniItem)!
      );
      const prvorangiranisToAdd = prvorangiranis.filter(prvorangiraniItem => {
        const prvorangiraniIdentifier = getPrvorangiraniIdentifier(prvorangiraniItem);
        if (prvorangiraniIdentifier == null || prvorangiraniCollectionIdentifiers.includes(prvorangiraniIdentifier)) {
          return false;
        }
        prvorangiraniCollectionIdentifiers.push(prvorangiraniIdentifier);
        return true;
      });
      return [...prvorangiranisToAdd, ...prvorangiraniCollection];
    }
    return prvorangiraniCollection;
  }
}
