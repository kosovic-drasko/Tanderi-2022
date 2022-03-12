import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ViewVrednovanjeService } from '../service/view-vrednovanje.service';
import { IViewVrednovanje, ViewVrednovanje } from '../view-vrednovanje.model';

import { ViewVrednovanjeUpdateComponent } from './view-vrednovanje-update.component';

describe('ViewVrednovanje Management Update Component', () => {
  let comp: ViewVrednovanjeUpdateComponent;
  let fixture: ComponentFixture<ViewVrednovanjeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let viewVrednovanjeService: ViewVrednovanjeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ViewVrednovanjeUpdateComponent],
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
      .overrideTemplate(ViewVrednovanjeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ViewVrednovanjeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    viewVrednovanjeService = TestBed.inject(ViewVrednovanjeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const viewVrednovanje: IViewVrednovanje = { id: 456 };

      activatedRoute.data = of({ viewVrednovanje });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(viewVrednovanje));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ViewVrednovanje>>();
      const viewVrednovanje = { id: 123 };
      jest.spyOn(viewVrednovanjeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewVrednovanje });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: viewVrednovanje }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(viewVrednovanjeService.update).toHaveBeenCalledWith(viewVrednovanje);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ViewVrednovanje>>();
      const viewVrednovanje = new ViewVrednovanje();
      jest.spyOn(viewVrednovanjeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewVrednovanje });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: viewVrednovanje }));
      saveSubject.complete();

      // THEN
      expect(viewVrednovanjeService.create).toHaveBeenCalledWith(viewVrednovanje);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ViewVrednovanje>>();
      const viewVrednovanje = { id: 123 };
      jest.spyOn(viewVrednovanjeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ viewVrednovanje });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(viewVrednovanjeService.update).toHaveBeenCalledWith(viewVrednovanje);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
