import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetailAmountRecipComponent } from './list/detail-amount-recip.component';
import { DetailAmountRecipDetailComponent } from './detail/detail-amount-recip-detail.component';
import { DetailAmountRecipUpdateComponent } from './update/detail-amount-recip-update.component';
import { DetailAmountRecipDeleteDialogComponent } from './delete/detail-amount-recip-delete-dialog.component';
import { DetailAmountRecipRoutingModule } from './route/detail-amount-recip-routing.module';

@NgModule({
  imports: [SharedModule, DetailAmountRecipRoutingModule],
  declarations: [
    DetailAmountRecipComponent,
    DetailAmountRecipDetailComponent,
    DetailAmountRecipUpdateComponent,
    DetailAmountRecipDeleteDialogComponent,
  ],
  entryComponents: [DetailAmountRecipDeleteDialogComponent],
})
export class DetailAmountRecipModule {}
