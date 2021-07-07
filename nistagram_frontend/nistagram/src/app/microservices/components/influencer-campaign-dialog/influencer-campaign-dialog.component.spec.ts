import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluencerCampaignDialogComponent } from './influencer-campaign-dialog.component';

describe('InfluencerCampaignDialogComponent', () => {
  let component: InfluencerCampaignDialogComponent;
  let fixture: ComponentFixture<InfluencerCampaignDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfluencerCampaignDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InfluencerCampaignDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
