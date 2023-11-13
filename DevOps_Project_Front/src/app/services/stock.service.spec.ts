import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { StockService } from './stock.service';

describe('StockService', () => {
  let service: StockService;
  let httpTestingController: HttpTestingController;
  //setup initialisation
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [StockService],
    });

    service = TestBed.inject(StockService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // After every test, assert that there are no outstanding requests
    httpTestingController.verify();
  });
  //creation service
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch data from API via HTTP GET', () => {
    const mockResponse = [
      { id: 1, title: 'Stock A' },
      { id: 2, title: 'Stock B' },
    ];

    // Make an HTTP request
    service.fetchAllData().subscribe((data) => {
      expect(data).toEqual(mockResponse); // Assert that the response matches the expected data
    });

    // Expect a single request to a specific URL with an HTTP method
    const req = httpTestingController.expectOne(
      'http://localhost:8082/devops/stock'
    );
    expect(req.request.method).toBe('GET');

    // Respond with mock data
    req.flush(mockResponse);
  });

  it('should fetch stock by id', () => {
    const mockResponse = { id: 1, title: 'Stock A' };

    // Make an HTTP request
    service.fetchData(1).subscribe((data) => {
      expect(data).toEqual(mockResponse); // Assert that the response matches the expected data
    });

    // Expect a single request to a specific URL with an HTTP method
    const req = httpTestingController.expectOne(
      'http://localhost:8082/devops/stock/1'
    );
    expect(req.request.method).toBe('GET');

    // Respond with mock data
    req.flush(mockResponse);
  });

  it('should fetch stock by quantity', () => {
    const mockResponse = { id: 1, title: 'Stock A' };

    // Make an HTTP request
    service.fetchQuantity(1).subscribe((data) => {
      expect(data).toEqual(mockResponse); // Assert that the response matches the expected data
    });

    // Expect a single request to a specific URL with an HTTP method
    const req = httpTestingController.expectOne(
      'http://localhost:8082/devops/stock/quantity/1'
    );
    expect(req.request.method).toBe('GET');

    // Respond with mock data
    req.flush(mockResponse);
  });

  it('should add stock', () => {
    const mockResponse = { id: 1, title: 'Stock A' };

    // Make an HTTP request
    service.addStock(mockResponse).subscribe((data) => {
      expect(data).toEqual(mockResponse); // Assert that the response matches the expected data
    });

    // Expect a single request to a specific URL with an HTTP method
    const req = httpTestingController.expectOne(
      'http://localhost:8082/devops/stock'
    );
    expect(req.request.method).toBe('POST');

    // Respond with mock data
    req.flush(mockResponse);
  });
});
