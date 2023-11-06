import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductService } from '../services/product.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { ProductComponent } from './product.component';

describe('ProductComponent', () => {
  let component: ProductComponent;
  let fixture: ComponentFixture<ProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductComponent],
      imports: [HttpClientModule, FormsModule], // Add the HttpClientModule module
      providers: [ProductService], // Add the ProductService provider
    }).compileComponents();

    fixture = TestBed.createComponent(ProductComponent);
    component = fixture.debugElement.componentInstance;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
