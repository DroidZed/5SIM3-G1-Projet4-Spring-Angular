package tn.esprit.devops_project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    private ProductServiceImpl productService;

    private StockServiceImpl stockService;

    private Product prod;

    private Stock stock;

    @BeforeEach
    void setUpTests() {
        productService = new ProductServiceImpl(productRepository, stockRepository);
        stockService = new StockServiceImpl(stockRepository);

        stock = new Stock(1L, "test stock", null);

        prod = new Product(
                1L,
                "test",
                12.5f,
                10,
                ProductCategory.BOOKS,
                null
        );
    }

    @Test
    void testRetrieveAllProductWhenNoProductsExist() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> actualProducts = productService.retreiveAllProduct();

        // Assert
        Assertions.assertEquals(productList, actualProducts);
        Assertions.assertTrue(actualProducts.isEmpty());
        Mockito.verify(productRepository).findAll();
    }

    @Test
    void testRetrieveProductById() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(prod));
        Product result = productService.retrieveProduct(1L);

        Assertions.assertEquals(result, prod);

        Mockito.verify(productRepository).findById(1L);
    }

    @Test
    void testAddProduct() {
        Mockito.when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        Assertions.assertEquals(stock, result);

        Mockito.verify(stockRepository).save(stock);

        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Mockito.when(productRepository.save(prod)).thenReturn(prod);

        Product result2 = productService.addProduct(prod, 1L);

        Assertions.assertEquals(result2, prod);

        Mockito.verify(productRepository).save(prod);
    }

    @Test
    void testDeleteProduct() {
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());
        productService.deleteProduct(1L);
        Mockito.verify(productRepository).deleteById(1L);
    }

    @Test
    void testRetrieveProductByCategory() {
        ArrayList<Product> productList = new ArrayList<>();
        Mockito.when(productRepository.findByCategory(ProductCategory.BOOKS)).thenReturn(productList);

        // Act
        List<Product> actualProducts = productService.retrieveProductByCategory(ProductCategory.BOOKS);

        // Assert
        Assertions.assertEquals(productList, actualProducts);
        Assertions.assertTrue(actualProducts.isEmpty());
        Mockito.verify(productRepository).findByCategory(ProductCategory.BOOKS);
    }

    @Test
    void testRetreiveProductStock() {
        ArrayList<Product> productList = new ArrayList<>();
        Mockito.when(productRepository.findByStockIdStock(Mockito.anyLong())).thenReturn(productList);

        // Act
        List<Product> actualProducts = productService.retreiveProductStock(1L);

        // Assert
        Assertions.assertEquals(productList, actualProducts);
        Assertions.assertTrue(actualProducts.isEmpty());
        Mockito.verify(productRepository).findByStockIdStock(1L);
    }
}

