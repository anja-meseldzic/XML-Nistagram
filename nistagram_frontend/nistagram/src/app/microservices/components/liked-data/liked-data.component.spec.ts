import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LikedDataComponent } from './liked-data.component';

describe('LikedDataComponent', () => {
  let component: LikedDataComponent;
  let fixture: ComponentFixture<LikedDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LikedDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LikedDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
