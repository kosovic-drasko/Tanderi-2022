import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPonudePonudjaci } from '../ponude-ponudjaci.model';

export type EntityResponseType = HttpResponse<IPonudePonudjaci>;
export type EntityArrayResponseType = HttpResponse<IPonudePonudjaci[]>;

@Injectable({ providedIn: 'root' })
export class PonudePonudjaciService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ponude-ponudjacis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ponudePonudjaci: IPonudePonudjaci): Observable<EntityResponseType> {
    return this.http.post<IPonudePonudjaci>(this.resourceUrl, ponudePonudjaci, { observe: 'response' });
  }

  update(ponudePonudjaci: IPonudePonudjaci): Observable<EntityResponseType> {
    return this.http.put<IPonudePonudjaci>(
      `${this.resourceUrl}/${getPonudePonudjaciIdentifier(ponudePonudjaci) as number}`,
      ponudePonudjaci,
      { observe: 'response' }
    );
  }

  partialUpdate(ponudePonudjaci: IPonudePonudjaci): Observable<EntityResponseType> {
    return this.http.patch<IPonudePonudjaci>(
      `${this.resourceUrl}/${getPonudePonudjaciIdentifier(ponudePonudjaci) as number}`,
      ponudePonudjaci,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPonudePonudjaci>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPonudePonudjaci[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPonudePonudjaciToCollectionIfMissing(
    ponudePonudjaciCollection: IPonudePonudjaci[],
    ...ponudePonudjacisToCheck: (IPonudePonudjaci | null | undefined)[]
  ): IPonudePonudjaci[] {
    const ponudePonudjacis: IPonudePonudjaci[] = ponudePonudjacisToCheck.filter(isPresent);
    if (ponudePonudjacis.length > 0) {
      const ponudePonudjaciCollectionIdentifiers = ponudePonudjaciCollection.map(
        ponudePonudjaciItem => getPonudePonudjaciIdentifier(ponudePonudjaciItem)!
      );
      const ponudePonudjacisToAdd = ponudePonudjacis.filter(ponudePonudjaciItem => {
        const ponudePonudjaciIdentifier = getPonudePonudjaciIdentifier(ponudePonudjaciItem);
        if (ponudePonudjaciIdentifier == null || ponudePonudjaciCollectionIdentifiers.includes(ponudePonudjaciIdentifier)) {
          return false;
        }
        ponudePonudjaciCollectionIdentifiers.push(ponudePonudjaciIdentifier);
        return true;
      });
      return [...ponudePonudjacisToAdd, ...ponudePonudjaciCollection];
    }
    return ponudePonudjaciCollection;
  }
}
function getPonudePonudjaciIdentifier(ponudePonudjaci: IPonudePonudjaci): number {
  throw new Error('Function not implemented.');
}
