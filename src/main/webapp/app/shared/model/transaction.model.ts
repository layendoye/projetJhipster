import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';
import { IUser } from 'app/core/user/user.model';

export interface ITransaction {
  id?: number;
  dateEnvois?: Moment;
  montant?: number;
  dateRetrait?: Moment;
  frais?: number;
  commSysteme?: number;
  commExp?: number;
  commRetireur?: number;
  taxe?: number;
  status?: string;
  code?: string;
  idExp?: IClient;
  idDest?: IClient;
  idUserExp?: IUser;
  idUserRetir?: IUser;
  nciRecp?: string;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public dateEnvois?: Moment,
    public montant?: number,
    public dateRetrait?: Moment,
    public frais?: number,
    public commSysteme?: number,
    public commExp?: number,
    public commRetireur?: number,
    public taxe?: number,
    public status?: string,
    public code?: string,
    public idExp?: IClient,
    public idDest?: IClient,
    public idUserExp?: IUser,
    public idUserRetir?: IUser,
    public nciRecp?: string
  ) {}
}
