import dayjs from 'dayjs/esm';

export interface IPostupci {
  id?: number;
  sifraPostupka?: number;
  brojTendera?: string | null;
  opisPostupka?: string;
  vrstaPostupka?: string;
  datumObjave?: dayjs.Dayjs | null;
  datumOtvaranja?: dayjs.Dayjs | null;
}

export class Postupci implements IPostupci {
  constructor(
    public id?: number,
    public sifraPostupka?: number,
    public brojTendera?: string | null,
    public opisPostupka?: string,
    public vrstaPostupka?: string,
    public datumObjave?: dayjs.Dayjs | null,
    public datumOtvaranja?: dayjs.Dayjs | null
  ) {}
}

export function getPostupciIdentifier(postupci: IPostupci): number | undefined {
  return postupci.id;
}
