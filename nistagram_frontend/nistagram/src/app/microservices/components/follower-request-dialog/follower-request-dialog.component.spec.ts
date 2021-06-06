import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowerRequestDialogComponent } from './follower-request-dialog.component';

describe('FollowerRequestDialogComponent', () => {
  let component: FollowerRequestDialogComponent;
  let fixture: ComponentFixture<FollowerRequestDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FollowerRequestDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowerRequestDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
