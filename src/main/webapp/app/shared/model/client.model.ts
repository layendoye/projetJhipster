export interface IClient {
  id?: number;
  nci?: string;
  nom?: string;
  telephone?: string;
}

export class Client implements IClient {
  constructor(public id?: number, public nci?: string, public nom?: string, public telephone?: string) {}
}
