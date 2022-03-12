export interface IPonude {
  id?: number;
  sifraPostupka?: number;
  sifraPonude?: number;
  brojPartije?: number;
  nazivProizvodjaca?: string | null;
  zasticeniNaziv?: string | null;
  ponudjenaVrijednost?: number;
  rokIsporuke?: number | null;
  sifraPonudjaca?: number | null;
  selected?: boolean | null;
}

export class Ponude implements IPonude {
  constructor(
    public id?: number,
    public sifraPostupka?: number,
    public sifraPonude?: number,
    public brojPartije?: number,
    public nazivProizvodjaca?: string | null,
    public zasticeniNaziv?: string | null,
    public ponudjenaVrijednost?: number,
    public rokIsporuke?: number | null,
    public sifraPonudjaca?: number | null,
    public selected?: boolean | null
  ) {
    this.selected = this.selected ?? false;
  }
}

export function getPonudeIdentifier(ponude: IPonude): number | undefined {
  return ponude.id;
}
