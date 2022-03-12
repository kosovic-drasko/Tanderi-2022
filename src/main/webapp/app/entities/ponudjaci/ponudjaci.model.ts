export interface IPonudjaci {
  id?: number;
  nazivPonudjaca?: string | null;
  odgovorno_lice?: string | null;
  adresa_ponudjaca?: string | null;
  banka_racun?: string | null;
}

export class Ponudjaci implements IPonudjaci {
  constructor(
    public id?: number,
    public nazivPonudjaca?: string | null,
    public odgovorno_lice?: string | null,
    public adresa_ponudjaca?: string | null,
    public banka_racun?: string | null
  ) {}
}

export function getPonudjaciIdentifier(ponudjaci: IPonudjaci): number | undefined {
  return ponudjaci.id;
}
