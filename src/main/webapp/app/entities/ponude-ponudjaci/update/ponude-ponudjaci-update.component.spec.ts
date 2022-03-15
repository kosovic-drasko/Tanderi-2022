import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PonudePonudjaciService } from '../service/ponude-ponudjaci.service';
import { IPonudePonudjaci, PonudePonudjaci } from '../ponude-ponudjaci.model';

import { PonudePonudjaciUpdateComponent } from './ponude-ponudjaci-update.component';

describe('PonudePonudjaci Management Update Component', () => {
  let comp: PonudePonudjaciUpdateComponent;
  let fixture: ComponentFixture<PonudePonudjaciUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ponudePonudjaciService: PonudePonudjaciService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PonudePonudjaciUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PonudePonudjaciUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PonudePonudjaciUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ponudePonudjaciService = TestBed.inject(PonudePonudjaciService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ponudePonudjaci: IPonudePonudjaci = { id: 456 };

      activatedRoute.data = of({ ponudePonudjaci });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ponudePonudjaci));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PonudePonudjaci>>();
      const ponudePonudjaci = { id: 123 };
      jest.spyOn(ponudePonudjaciService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudePonudjaci });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ponudePonudjaci }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ponudePonudjaciService.update).toHaveBeenCalledWith(ponudePonudjaci);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PonudePonudjaci>>();
      const ponudePonudjaci = new PonudePonudjaci();
      jest.spyOn(ponudePonudjaciService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudePonudjaci });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ponudePonudjaci }));
      saveSubject.complete();

      // THEN
      expect(ponudePonudjaciService.create).toHaveBeenCalledWith(ponudePonudjaci);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PonudePonudjaci>>();
      const ponudePonudjaci = { id: 123 };
      jest.spyOn(ponudePonudjaciService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ponudePonudjaci });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ponudePonudjaciService.update).toHaveBeenCalledWith(ponudePonudjaci);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
