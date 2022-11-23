import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailAmountRecip } from '../detail-amount-recip.model';
import { DetailAmountRecipService } from '../service/detail-amount-recip.service';

@Component({
  templateUrl: './detail-amount-recip-delete-dialog.component.html',
})
export class DetailAmountRecipDeleteDialogComponent {
  detailAmountRecip?: IDetailAmountRecip;

  constructor(protected detailAmountRecipService: DetailAmountRecipService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailAmountRecipService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
