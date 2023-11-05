import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ProductService } from './product.service';

describe('StockService', () => {
  let service: ProductService;
  let httpTestingController: HttpTestingController;

  const apiUrl = 'http://localhost:8082/stock';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductService],
    });

    service = TestBed.inject(ProductService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // After every test, assert that there are no outstanding requests
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch data from API via HTTP GET', () => {
    const mockResponse = [
      {
        id: 1,
        title: 'Product A',
        price: 22.5,
        quantity: 20,
        category: 'BOOKS',
        stock: null,
      },
      {
        id: 2,
        title: 'Product B',
        price: 157,
        quantity: 5,
        category: 'CLOTHING',
        stock: null,
      },
    ];

    // Make an HTTP request
    service.fetchAllData().subscribe((data) => {
      expect(data).toEqual(mockResponse); // Assert that the response matches the expected data
    });

    // Expect a single request to a specific URL with an HTTP method
    const req = httpTestingController.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');

    // Respond with mock data
    req.flush(mockResponse);
  });
});
