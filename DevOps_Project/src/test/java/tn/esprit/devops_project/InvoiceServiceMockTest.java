package tn.esprit.devops_project;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private InvoiceServiceImpl invoiceServiceImpl;

    @BeforeEach
    void setUp() {
        // Instantiate the service
        invoiceServiceImpl = new InvoiceServiceImpl(invoiceRepository, operatorRepository, invoiceDetailService, supplierRepository);
    }



    @Test
    public void testRetrieveAllInvoices() {
        List<Invoice> expectedInvoices = new ArrayList<>();

        Invoice invoice1 = new Invoice();
        invoice1.setIdInvoice(1L);
        invoice1.setAmountInvoice(100.0f);
        invoice1.setArchived(false);

        Invoice invoice2 = new Invoice();
        invoice2.setIdInvoice(2L);
        invoice2.setAmountInvoice(200.0f);
        invoice2.setArchived(false);

        expectedInvoices.add(invoice1);
        expectedInvoices.add(invoice2);

        when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

        List<Invoice> actualInvoices = invoiceServiceImpl.retrieveAllInvoices();

        assertEquals(expectedInvoices, actualInvoices);
    }

    @Test
    public void testCancelInvoice() {
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));

        invoiceServiceImpl.cancelInvoice(1L);

        assertTrue(invoice.getArchived());

        verify(invoiceRepository, times(1)).save(invoice);

    }

    @Test
    public void testRetrieveInvoice() {
        // Create a sample invoice
        Invoice invoice = new Invoice();
        // Set the expected behavior when findById is called
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));

        // Call the retrieveInvoice method
        Invoice retrievedInvoice = invoiceServiceImpl.retrieveInvoice(1L);

        // Assert that the retrieved invoice matches the expected invoice
        assertEquals(invoice, retrievedInvoice);
    }



    @Test
    public void testGetInvoicesBySupplier() {
        // Create a sample supplier
        Supplier supplier = new Supplier();
        // Set the expected behavior when findById is called for the supplier
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

        // Create a list of invoices associated with the supplier
        List<Invoice> expectedInvoices = new ArrayList<>();

        // Create some invoices associated with the supplier
        Invoice invoice1 = new Invoice();
        invoice1.setIdInvoice(1L);
        invoice1.setSupplier(supplier); // Associate the invoice with the supplier
        expectedInvoices.add(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setIdInvoice(2L);
        invoice2.setSupplier(supplier); // Associate the invoice with the supplier
        expectedInvoices.add(invoice2);

        // Call the getInvoicesBySupplier method
        List<Invoice> actualInvoices = invoiceServiceImpl.getInvoicesBySupplier(1L);

        // Assert that the actual result matches the expected result
        assertEquals(expectedInvoices, actualInvoices);
    }




    @Test
    public void testAssignOperatorToInvoice() {
        // Create a sample operator
        Operator operator = new Operator();
        // Set the expected behavior when findById is called for the operator
        when(operatorRepository.findById(anyLong())).thenReturn(Optional.of(operator));

        // Create a sample invoice
        Invoice invoice = new Invoice();
        // Set the expected behavior when findById is called for the invoice
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));

        // Call the assignOperatorToInvoice method
        invoiceServiceImpl.assignOperatorToInvoice(1L, 2L);

        // Verify that the operator's invoices list is updated
        assertTrue(operator.getInvoices().contains(invoice));

        // Verify that save method is called on the operator repository
        verify(operatorRepository, times(1)).save(operator);
    }


    @Test
    public void testGetTotalAmountInvoiceBetweenDates() {
        // Create sample start and end dates
        Date startDate = new Date();  // Replace with your actual date
        Date endDate = new Date();    // Replace with your actual date

        // Define the behavior of the invoiceRepository mock
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(100.0f);  // Replace with your expected amount

        // Call the getTotalAmountInvoiceBetweenDates method
        float totalAmount = invoiceServiceImpl.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        // Assert that the actual total amount matches the expected total amount
        assertEquals(100.0, totalAmount, 0.01);  // Adjust delta as needed for float comparisons
    }







}


