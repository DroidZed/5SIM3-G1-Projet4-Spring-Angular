package tn.esprit.devops_project;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;

    private StockServiceImpl stockService;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stockService = new StockServiceImpl(stockRepository);
        stock = new Stock(1L, "test", null);
    }

    @Test
    @Order(1)
    @DisplayName("Create stock")
    void testAddStock() {
        Mockito.when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        Assertions.assertEquals(stock, result);

        Mockito.verify(stockRepository).save(stock);
    }

    @Test
    @Order(2)
    @DisplayName("Get stock by id")
    void testRetrieveStockById() {

        Mockito.when(stockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stock));

        Stock result = stockService.retrieveStock(1L);

        Assertions.assertNotNull(result);
    }

    @Test
    @Order(3)
    @DisplayName("Get all stock")
    void testRetrieveAllStock() {
        // Arrange
        List<Stock> allStock = new ArrayList<>();

        Mockito.when(stockRepository.findAll()).thenReturn(allStock);

        // Act
        List<Stock> retrieved = stockService.retrieveAllStock();

        // Assert
        Assertions.assertEquals(allStock, retrieved);
        Assertions.assertEquals(0, retrieved.size());
        Mockito.verify(stockRepository, Mockito.times(1)).findAll();
    }
}
