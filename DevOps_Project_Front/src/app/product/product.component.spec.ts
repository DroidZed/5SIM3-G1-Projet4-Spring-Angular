import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductComponent } from './product.component';
import { ProductService } from '../services/product.service';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

describe('ProductComponent', () => {
  let component: ProductComponent;
  let fixture: ComponentFixture<ProductComponent>;
  let productService: ProductService;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [ProductComponent],
      imports: [HttpClientModule, FormsModule, RouterTestingModule],
      providers: [
        ProductService,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '1' // http://localhost:4200/product/1
              }
            }
          }
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductComponent);
    component = fixture.componentInstance;
    productService = TestBed.inject(ProductService);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have a title with stock title and item count', () => {
    const stockTitle = 'headphones';

    component.stock = { title: stockTitle };
    component.data = [
      { title: 'AirPods', category: 'ELECTRONICS', quantity: 20, price: 250 },
    ];

    fixture.detectChanges();

    const titleElement = fixture.nativeElement.querySelector('h2');
    expect(titleElement.textContent).toContain(`${stockTitle} : Product List | ${component.data.length} Item(s)`);
  });

  it('should open the modal when the "+" button is clicked', () => {

    const modalService = TestBed.inject(NgbModal);
    spyOn(modalService, 'open').and.returnValue({ result: Promise.resolve('closed') } as any);

    const addButton = fixture.nativeElement.querySelector('.btn-success');
    addButton.click();

    expect(modalService.open).toHaveBeenCalledOnceWith(jasmine.any(Object), { ariaLabelledBy: 'modal-basic-title' });
  });

  /*it('should open the modal when the "+" button is clicked', () => {
    spyOn(component, 'open');
  
    const addButton = fixture.nativeElement.querySelector('.btn-success');
    addButton.click();
  
    expect(component.open).toHaveBeenCalled();
  });
  */

  it('should add a product when the "Add" button is clicked in the modal', () => {
    const mockProduct = { title: 'Test Product', category: 'ELECTRONICS', price: 50, quantity: 10 };
    spyOn(productService, 'addProduct').and.returnValue(of(mockProduct));

    component.addProduct(mockProduct);

    expect(productService.addProduct).toHaveBeenCalledWith(mockProduct, '1');
  });

  it('should delete an item when the "X" button is clicked', () => {
    const mockItemId = '1';
    spyOn(window, 'confirm').and.returnValue(true);
    spyOn(productService, 'deleteData').and.returnValue(of([]));

    component.deleteItem(mockItemId);

    expect(window.confirm).toHaveBeenCalledWith('Are you sure you want to delete this item?');
    expect(productService.deleteData).toHaveBeenCalledWith(mockItemId);
  });



});