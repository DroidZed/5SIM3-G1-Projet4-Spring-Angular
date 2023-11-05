import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { StockComponent } from './stock.component';
import { StockService } from '../services/stock.service';
import { FormsModule } from '@angular/forms';

describe('StockComponent', () => {
  let component: StockComponent;
  let fixture: ComponentFixture<StockComponent>;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [FormsModule],
      providers: [StockService],
      declarations: [StockComponent]
    }).compileComponents().then(() => {
      fixture = TestBed.createComponent(StockComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call onButtonClick when clicked', fakeAsync(() => {
    spyOn(component, 'showForm');

    let showFormBtn = fixture.debugElement.nativeElement.querySelector('#showForm');
    let inputlabel = fixture.debugElement.nativeElement.querySelector("#input-label");
    showFormBtn.click();

    tick();

    expect(component.showForm()).toHaveBeenCalled();
    expect(inputlabel).toBeTruthy();
  }));


});
