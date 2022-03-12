import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IViewVrednovanje, getViewVrednovanjeIdentifier } from '../view-vrednovanje.model';

export type EntityResponseType = HttpResponse<IViewVrednovanje>;
export type EntityArrayResponseType = HttpResponse<IViewVrednovanje[]>;

@Injectable({ providedIn: 'root' })
export class ViewVrednovanjeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/view-vrednovanjes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(viewVrednovanje: IViewVrednovanje): Observable<EntityResponseType> {
    return this.http.post<IViewVrednovanje>(this.resourceUrl, viewVrednovanje, { observe: 'response' });
  }

  update(viewVrednovanje: IViewVrednovanje): Observable<EntityResponseType> {
    return this.http.put<IViewVrednovanje>(
      `${this.resourceUrl}/${getViewVrednovanjeIdentifier(viewVrednovanje) as number}`,
      viewVrednovanje,
      { observe: 'response' }
    );
  }

  partialUpdate(viewVrednovanje: IViewVrednovanje): Observable<EntityResponseType> {
    return this.http.patch<IViewVrednovanje>(
      `${this.resourceUrl}/${getViewVrednovanjeIdentifier(viewVrednovanje) as number}`,
      viewVrednovanje,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IViewVrednovanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IViewVrednovanje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addViewVrednovanjeToCollectionIfMissing(
    viewVrednovanjeCollection: IViewVrednovanje[],
    ...viewVrednovanjesToCheck: (IViewVrednovanje | null | undefined)[]
  ): IViewVrednovanje[] {
    const viewVrednovanjes: IViewVrednovanje[] = viewVrednovanjesToCheck.filter(isPresent);
    if (viewVrednovanjes.length > 0) {
      const viewVrednovanjeCollectionIdentifiers = viewVrednovanjeCollection.map(
        viewVrednovanjeItem => getViewVrednovanjeIdentifier(viewVrednovanjeItem)!
      );
      const viewVrednovanjesToAdd = viewVrednovanjes.filter(viewVrednovanjeItem => {
        const viewVrednovanjeIdentifier = getViewVrednovanjeIdentifier(viewVrednovanjeItem);
        if (viewVrednovanjeIdentifier == null || viewVrednovanjeCollectionIdentifiers.includes(viewVrednovanjeIdentifier)) {
          return false;
        }
        viewVrednovanjeCollectionIdentifiers.push(viewVrednovanjeIdentifier);
        return true;
      });
      return [...viewVrednovanjesToAdd, ...viewVrednovanjeCollection];
    }
    return viewVrednovanjeCollection;
  }
}
