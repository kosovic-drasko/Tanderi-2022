import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IViewVrednovanje, ViewVrednovanje } from '../view-vrednovanje.model';

import { ViewVrednovanjeService } from './view-vrednovanje.service';

describe('ViewVrednovanje Service', () => {
  let service: ViewVrednovanjeService;
  let httpMock: HttpTestingController;
  let elemDefault: IViewVrednovanje;
  let expectedResult: IViewVrednovanje | IViewVrednovanje[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ViewVrednovanjeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      sifraPostupka: 0,
      sifraPonude: 0,
      brojPartije: 0,
      atc: 'AAAAAAA',
      inn: 'AAAAAAA',
      zasticeniNaziv: 'AAAAAAA',
      farmaceutskiOblikLijeka: 'AAAAAAA',
      jacinaLijeka: 'AAAAAAA',
      pakovanje: 'AAAAAAA',
      trazenaKolicina: 0,
      procijenjenaVrijednost: 0,
      ponudjenaVrijednost: 0,
      rokIsporuke: 0,
      nazivProizvodjaca: 'AAAAAAA',
      nazivPonudjaca: 'AAAAAAA',
      bodCijena: 0,
      bodRok: 0,
      bodUkupno: 0,
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

    it('should create a ViewVrednovanje', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ViewVrednovanje()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ViewVrednovanje', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sifraPostupka: 1,
          sifraPonude: 1,
          brojPartije: 1,
          atc: 'BBBBBB',
          inn: 'BBBBBB',
          zasticeniNaziv: 'BBBBBB',
          farmaceutskiOblikLijeka: 'BBBBBB',
          jacinaLijeka: 'BBBBBB',
          pakovanje: 'BBBBBB',
          trazenaKolicina: 1,
          procijenjenaVrijednost: 1,
          ponudjenaVrijednost: 1,
          rokIsporuke: 1,
          nazivProizvodjaca: 'BBBBBB',
          nazivPonudjaca: 'BBBBBB',
          bodCijena: 1,
          bodRok: 1,
          bodUkupno: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ViewVrednovanje', () => {
      const patchObject = Object.assign(
        {
          sifraPostupka: 1,
          sifraPonude: 1,
          brojPartije: 1,
          atc: 'BBBBBB',
          zasticeniNaziv: 'BBBBBB',
          pakovanje: 'BBBBBB',
          trazenaKolicina: 1,
          rokIsporuke: 1,
          nazivProizvodjaca: 'BBBBBB',
          nazivPonudjaca: 'BBBBBB',
          bodUkupno: 1,
        },
        new ViewVrednovanje()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ViewVrednovanje', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sifraPostupka: 1,
          sifraPonude: 1,
          brojPartije: 1,
          atc: 'BBBBBB',
          inn: 'BBBBBB',
          zasticeniNaziv: 'BBBBBB',
          farmaceutskiOblikLijeka: 'BBBBBB',
          jacinaLijeka: 'BBBBBB',
          pakovanje: 'BBBBBB',
          trazenaKolicina: 1,
          procijenjenaVrijednost: 1,
          ponudjenaVrijednost: 1,
          rokIsporuke: 1,
          nazivProizvodjaca: 'BBBBBB',
          nazivPonudjaca: 'BBBBBB',
          bodCijena: 1,
          bodRok: 1,
          bodUkupno: 1,
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

    it('should delete a ViewVrednovanje', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addViewVrednovanjeToCollectionIfMissing', () => {
      it('should add a ViewVrednovanje to an empty array', () => {
        const viewVrednovanje: IViewVrednovanje = { id: 123 };
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], viewVrednovanje);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should not add a ViewVrednovanje to an array that contains it', () => {
        const viewVrednovanje: IViewVrednovanje = { id: 123 };
        const viewVrednovanjeCollection: IViewVrednovanje[] = [
          {
            ...viewVrednovanje,
          },
          { id: 456 },
        ];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, viewVrednovanje);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ViewVrednovanje to an array that doesn't contain it", () => {
        const viewVrednovanje: IViewVrednovanje = { id: 123 };
        const viewVrednovanjeCollection: IViewVrednovanje[] = [{ id: 456 }];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, viewVrednovanje);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should add only unique ViewVrednovanje to an array', () => {
        const viewVrednovanjeArray: IViewVrednovanje[] = [{ id: 123 }, { id: 456 }, { id: 53601 }];
        const viewVrednovanjeCollection: IViewVrednovanje[] = [{ id: 123 }];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, ...viewVrednovanjeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const viewVrednovanje: IViewVrednovanje = { id: 123 };
        const viewVrednovanje2: IViewVrednovanje = { id: 456 };
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], viewVrednovanje, viewVrednovanje2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(viewVrednovanje);
        expect(expectedResult).toContain(viewVrednovanje2);
      });

      it('should accept null and undefined values', () => {
        const viewVrednovanje: IViewVrednovanje = { id: 123 };
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing([], null, viewVrednovanje, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(viewVrednovanje);
      });

      it('should return initial array if no ViewVrednovanje is added', () => {
        const viewVrednovanjeCollection: IViewVrednovanje[] = [{ id: 123 }];
        expectedResult = service.addViewVrednovanjeToCollectionIfMissing(viewVrednovanjeCollection, undefined, null);
        expect(expectedResult).toEqual(viewVrednovanjeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
