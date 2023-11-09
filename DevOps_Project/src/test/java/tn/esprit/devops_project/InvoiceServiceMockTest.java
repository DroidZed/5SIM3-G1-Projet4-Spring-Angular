package tn.esprit.devops_project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.*;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;
import tn.esprit.devops_project.services.OperatorServiceImpl;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class InvoiceServiceMockTest {
    @Mock
    private InvoiceDetailRepository invoiceDetailService;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private OperatorRepository operatorRepository;

    private InvoiceServiceImpl invoiceService;
    //
    private SupplierServiceImpl supplierService;
    private OperatorServiceImpl operatorService;
    private Operator operat;
    private Supplier supp;
    private Invoice invo;
    private Invoice invo2;
    private InvoiceDetail invoiced;

    @BeforeEach
    void setUp() {
        // Instantiate the service
        invoiceService = new InvoiceServiceImpl(invoiceRepository, operatorRepository, invoiceDetailService, supplierRepository);
        // Set<InvoiceDetail> invoiceDetailSet = new HashSet<>();
        //
        operatorService = new OperatorServiceImpl(operatorRepository);
        operat = new Operator(
                1L,
                "fname",
                "lname",
                "password",
                new HashSet<>()
        );
        //
        supplierService = new SupplierServiceImpl(supplierRepository);
        supp = new Supplier(
                1L,
                "20",
                "label test",
                SupplierCategory.CONVENTIONNE,
                new HashSet<>()
        );
        invo = new Invoice(
                1L,
                50F, 150F,
                new Date(),
                new Date(),
                false,
                new HashSet<>(),
                supp
        );

        invo2= new Invoice(
                2L,
                50F, 150F,
                new Date(),
                new Date(),
                false,
                new HashSet<>(),
                supp
        );
    }

    @Test
    public void testRetrieveAllInvoices() {
        List<Invoice> expectedInvoices = new ArrayList<>();

        expectedInvoices.add(invo);
        expectedInvoices.add(invo2);

        when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

        List<Invoice> actualInvoices = invoiceService.retrieveAllInvoices();

        assertEquals(expectedInvoices, actualInvoices);
        Mockito.verify(invoiceRepository).findAll();
    }

    @Test
    public void testCancelInvoice() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invo));

        invoiceService.cancelInvoice(1L);

        assertTrue(invo.getArchived());

        verify(invoiceRepository, times(1)).save(invo);

    }

    @Test
    public void testRetrieveInvoice() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invo));

        Invoice retrievedInvoice = invoiceService.retrieveInvoice(1L);

        // Assert that the retrieved invoice matches the expected invoice
        assertEquals(invo, retrievedInvoice);
    }

    @Test
    public void testGetInvoicesBySupplier() {
        Supplier supplier = new Supplier();
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

        // Create a set of invoices associated with the supplier
        Set<Invoice> expectedInvoices = new HashSet<>();
        Invoice invoice1 = new Invoice();
        invoice1.setIdInvoice(1L);
        Invoice invoice2 = new Invoice();
        invoice2.setIdInvoice(2L);
        expectedInvoices.add(invoice1);
        expectedInvoices.add(invoice2);
        supplier.setInvoices(expectedInvoices);

        Set<Invoice> actualInvoices = new HashSet<>(invoiceService.getInvoicesBySupplier(1L));

        // Assert that the actual invoices match the expected invoices
        assertEquals(expectedInvoices, actualInvoices);
    }

    @Test
    public void testAssignOperatorToInvoice() {
        when(operatorRepository.findById(anyLong())).thenReturn(Optional.of(operat));
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invo));

        invoiceService.assignOperatorToInvoice(1L, 1L);

        // Verify that the operator has been assigned to the invoice
        assertTrue(operat.getInvoices().contains(invo));
        verify(operatorRepository, times(1)).save(operat);
    }

    @Test
    public void testGetTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();

        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(100.0f);  //expected amount

        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        // Assert that the actual total amount matches the expected total amount
        assertEquals(100.0, totalAmount, 0.01);
    }
}


