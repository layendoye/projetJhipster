import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving: boolean;
  envois = true;
  clients: IClient[];

  users: IUser[];
  dateEnvoisDp: any;
  dateRetraitDp: any;

  editForm = this.fb.group({
    id: [],
    dateEnvois: [],
    montant: [],
    dateRetrait: [],
    frais: [],
    commSysteme: [],
    commExp: [],
    commRetireur: [],
    taxe: [],
    status: [],
    code: [],
    idExp: [],
    idDest: [],
    idUserExp: [],
    idUserRetir: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionService: TransactionService,
    protected clientService: ClientService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
    });
    this.clientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClient[]>) => response.body)
      )
      .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      dateEnvois: transaction.dateEnvois,
      montant: transaction.montant,
      dateRetrait: transaction.dateRetrait,
      frais: transaction.frais,
      commSysteme: transaction.commSysteme,
      commExp: transaction.commExp,
      commRetireur: transaction.commRetireur,
      taxe: transaction.taxe,
      status: transaction.status,
      code: transaction.code,
      idExp: transaction.idExp,
      idDest: transaction.idDest,
      idUserExp: transaction.idUserExp,
      idUserRetir: transaction.idUserRetir
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      dateEnvois: this.editForm.get(['dateEnvois']).value,
      montant: this.editForm.get(['montant']).value,
      dateRetrait: this.editForm.get(['dateRetrait']).value,
      frais: this.editForm.get(['frais']).value,
      commSysteme: this.editForm.get(['commSysteme']).value,
      commExp: this.editForm.get(['commExp']).value,
      commRetireur: this.editForm.get(['commRetireur']).value,
      taxe: this.editForm.get(['taxe']).value,
      status: this.editForm.get(['status']).value,
      code: this.editForm.get(['code']).value,
      idExp: this.editForm.get(['idExp']).value,
      idDest: this.editForm.get(['idDest']).value,
      idUserExp: this.editForm.get(['idUserExp']).value,
      idUserRetir: this.editForm.get(['idUserRetir']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClientById(index: number, item: IClient) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
  unEnvois() {
    if (this.envois) this.envois = false;
    else this.envois = true;
  }
}
