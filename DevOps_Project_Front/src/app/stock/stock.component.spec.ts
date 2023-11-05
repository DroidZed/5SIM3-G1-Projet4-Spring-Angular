import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { StockComponent } from './stock.component';
import { StockService } from '../services/stock.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

describe('StockComponent', () => {
  let component: StockComponent;
  let fixture: ComponentFixture<StockComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, HttpClientModule, MatDialogModule, MatButtonModule],
      providers: [StockService],
      declarations: [StockComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(StockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call showForm when clicked', () => {
    spyOn(component, 'showForm');

    let showFormBtn = fixture.debugElement.nativeElement.querySelector('#showForm');
    let inputlabel = fixture.debugElement.nativeElement.querySelector("#input-label");

    component.showForm();

    expect(component.showForm).toHaveBeenCalled();
    expect(inputlabel).toBeDefined();
  });


  it('should call submitForm when clicked', () => {
    spyOn(component, 'showForm');

    let showFormBtn = fixture.debugElement.nativeElement.querySelector('#showForm');
    let inputlabel = fixture.debugElement.nativeElement.querySelector("#input-label");

    component.showForm();

    spyOn(component, 'submitForm');

    let submitFormBtn = fixture.debugElement.nativeElement.querySelector('#submitForm');

    component.submitForm();

    expect(component.showForm).toHaveBeenCalled();
    expect(inputlabel).toBeDefined();

    expect(component.submitForm).toHaveBeenCalled();
  });


});
