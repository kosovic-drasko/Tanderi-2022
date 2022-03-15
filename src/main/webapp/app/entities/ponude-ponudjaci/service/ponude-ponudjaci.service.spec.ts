import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPonudePonudjaci, PonudePonudjaci } from '../ponude-ponudjaci.model';

import { PonudePonudjaciService } from './ponude-ponudjaci.service';

describe('PonudePonudjaci Service', () => {
  let service: PonudePonudjaciService;
  let httpMock: HttpTestingController;
  let elemDefault: IPonudePonudjaci;
  let expectedResult: IPonudePonudjaci | IPonudePonudjaci[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PonudePonudjaciService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      sifraPostupka: 0,
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

    it('should create a PonudePonudjaci', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PonudePonudjaci()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PonudePonudjaci', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sifraPostupka: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PonudePonudjaci', () => {
      const patchObject = Object.assign({}, new PonudePonudjaci());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PonudePonudjaci', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          sifraPostupka: 1,
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

    it('should delete a PonudePonudjaci', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPonudePonudjaciToCollectionIfMissing', () => {
      it('should add a PonudePonudjaci to an empty array', () => {
        const ponudePonudjaci: IPonudePonudjaci = { id: 123 };
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], ponudePonudjaci);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should not add a PonudePonudjaci to an array that contains it', () => {
        const ponudePonudjaci: IPonudePonudjaci = { id: 123 };
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [
          {
            ...ponudePonudjaci,
          },
          { id: 456 },
        ];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ponudePonudjaci);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PonudePonudjaci to an array that doesn't contain it", () => {
        const ponudePonudjaci: IPonudePonudjaci = { id: 123 };
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [{ id: 456 }];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ponudePonudjaci);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should add only unique PonudePonudjaci to an array', () => {
        const ponudePonudjaciArray: IPonudePonudjaci[] = [{ id: 123 }, { id: 456 }, { id: 65316 }];
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [{ id: 123 }];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, ...ponudePonudjaciArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ponudePonudjaci: IPonudePonudjaci = { id: 123 };
        const ponudePonudjaci2: IPonudePonudjaci = { id: 456 };
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], ponudePonudjaci, ponudePonudjaci2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ponudePonudjaci);
        expect(expectedResult).toContain(ponudePonudjaci2);
      });

      it('should accept null and undefined values', () => {
        const ponudePonudjaci: IPonudePonudjaci = { id: 123 };
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing([], null, ponudePonudjaci, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ponudePonudjaci);
      });

      it('should return initial array if no PonudePonudjaci is added', () => {
        const ponudePonudjaciCollection: IPonudePonudjaci[] = [{ id: 123 }];
        expectedResult = service.addPonudePonudjaciToCollectionIfMissing(ponudePonudjaciCollection, undefined, null);
        expect(expectedResult).toEqual(ponudePonudjaciCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
