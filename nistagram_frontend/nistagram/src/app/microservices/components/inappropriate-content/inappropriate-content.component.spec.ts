import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InappropriateContentComponent } from './inappropriate-content.component';

describe('InappropriateContentComponent', () => {
  let component: InappropriateContentComponent;
  let fixture: ComponentFixture<InappropriateContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InappropriateContentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InappropriateContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
