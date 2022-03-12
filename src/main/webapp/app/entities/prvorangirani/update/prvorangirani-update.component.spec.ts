import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrvorangiraniService } from '../service/prvorangirani.service';
import { IPrvorangirani, Prvorangirani } from '../prvorangirani.model';

import { PrvorangiraniUpdateComponent } from './prvorangirani-update.component';

describe('Prvorangirani Management Update Component', () => {
  let comp: PrvorangiraniUpdateComponent;
  let fixture: ComponentFixture<PrvorangiraniUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prvorangiraniService: PrvorangiraniService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrvorangiraniUpdateComponent],
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
      .overrideTemplate(PrvorangiraniUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrvorangiraniUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prvorangiraniService = TestBed.inject(PrvorangiraniService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prvorangirani: IPrvorangirani = { id: 456 };

      activatedRoute.data = of({ prvorangirani });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(prvorangirani));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Prvorangirani>>();
      const prvorangirani = { id: 123 };
      jest.spyOn(prvorangiraniService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prvorangirani });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prvorangirani }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(prvorangiraniService.update).toHaveBeenCalledWith(prvorangirani);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Prvorangirani>>();
      const prvorangirani = new Prvorangirani();
      jest.spyOn(prvorangiraniService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prvorangirani });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prvorangirani }));
      saveSubject.complete();

      // THEN
      expect(prvorangiraniService.create).toHaveBeenCalledWith(prvorangirani);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Prvorangirani>>();
      const prvorangirani = { id: 123 };
      jest.spyOn(prvorangiraniService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prvorangirani });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prvorangiraniService.update).toHaveBeenCalledWith(prvorangirani);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
