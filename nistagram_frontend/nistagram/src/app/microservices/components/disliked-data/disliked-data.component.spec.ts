import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DislikedDataComponent } from './disliked-data.component';

describe('DislikedDataComponent', () => {
  let component: DislikedDataComponent;
  let fixture: ComponentFixture<DislikedDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DislikedDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DislikedDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
