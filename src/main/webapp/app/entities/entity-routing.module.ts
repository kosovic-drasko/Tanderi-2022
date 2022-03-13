import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'postupci',
        data: { pageTitle: 'tenderApp.postupci.home.title' },
        loadChildren: () => import('./postupci/postupci.module').then(m => m.PostupciModule),
      },
      {
        path: 'tenderi-home',
        data: { pageTitle: 'tenderiApp.tenderiHome.home.title' },
        loadChildren: () => import('./tenderi-home/tenderi-home.module').then(m => m.TenderiHomeModule),
      },
      {
        path: 'ponudjaci',
        data: { pageTitle: 'tenderApp.ponudjaci.home.title' },
        loadChildren: () => import('./ponudjaci/ponudjaci.module').then(m => m.PonudjaciModule),
      },
      {
        path: 'specifikacije',
        data: { pageTitle: 'tenderApp.specifikacije.home.title' },
        loadChildren: () => import('./specifikacije/specifikacije.module').then(m => m.SpecifikacijeModule),
      },
      {
        path: 'ponude',
        data: { pageTitle: 'tenderApp.ponude.home.title' },
        loadChildren: () => import('./ponude/ponude.module').then(m => m.PonudeModule),
      },
      {
        path: 'view-vrednovanje',
        data: { pageTitle: 'tenderApp.viewVrednovanje.home.title' },
        loadChildren: () => import('./view-vrednovanje/view-vrednovanje.module').then(m => m.ViewVrednovanjeModule),
      },
      {
        path: 'prvorangirani',
        data: { pageTitle: 'tenderApp.prvorangirani.home.title' },
        loadChildren: () => import('./prvorangirani/prvorangirani.module').then(m => m.PrvorangiraniModule),
      },
      {
        path: 'hvale-ponude',
        data: { pageTitle: 'tenderApp.hvalePonude.home.title' },
        loadChildren: () => import('./hvale-ponude/hvale-ponude.module').then(m => m.HvalePonudeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
