import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ProductListComponent } from './product-list.component';
import { ProductService } from '../services/product.service';
import { FormsModule } from '@angular/forms';

describe('ProductListComponent', () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductListComponent],
      imports: [HttpClientModule, FormsModule], // Add the HttpClientModule module
      providers: [ProductService], // Add the ProductService provider
    }).compileComponents();

    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.debugElement.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });


  it("should have a title in a h2 tag", () => {
    const title = fixture.debugElement.nativeElement.querySelector('h2');
    expect(title.textContent).toContain('ALL PRODUCTS');
  });

  it("should have a dropdown list containing items", () => {
    const dropdown = fixture.debugElement.nativeElement.querySelector('select');
    expect(dropdown.textContent).toContain('All Categories');
    expect(dropdown.textContent).toContain('ELECTRONICS');
    expect(dropdown.textContent).toContain('CLOTHING');
    expect(dropdown.textContent).toContain('BOOKS');
  });
});
