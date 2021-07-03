import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterAgentAdminComponent } from './register-agent-admin.component';

describe('RegisterAgentAdminComponent', () => {
  let component: RegisterAgentAdminComponent;
  let fixture: ComponentFixture<RegisterAgentAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterAgentAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterAgentAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
