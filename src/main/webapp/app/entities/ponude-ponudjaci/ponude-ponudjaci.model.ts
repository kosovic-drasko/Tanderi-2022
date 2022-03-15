export interface IPonudePonudjaci {
  id?: number;
  sifraPostupka?: number | null;
}

export class PonudePonudjaci implements IPonudePonudjaci {
  constructor(public id?: number, public sifraPostupka?: number | null) {}
}

export function getPonudePonudjaciIdentifier(ponudePonudjaci: IPonudePonudjaci): number | undefined {
  return ponudePonudjaci.id;
}
