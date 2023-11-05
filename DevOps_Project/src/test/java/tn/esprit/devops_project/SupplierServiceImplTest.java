package tn.esprit.devops_project;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    private SupplierServiceImpl supplierService;

    Supplier supp;

    Supplier updatedSupp;

    @BeforeEach
    void setUp() {
        supplierService = new SupplierServiceImpl(supplierRepository);
        supp = new Supplier(
                1L,
                "20",
                "label test",
                SupplierCategory.CONVENTIONNE,
                null
        );

        updatedSupp = new Supplier(
                1L,
                "30",
                "label test2",
                SupplierCategory.ORDINAIRE,
                null
        );
    }

    @Test
    void testRetrieveAllSuppliers() {

        // Arrange
        ArrayList<Supplier> suppliers = new ArrayList<>();

        // Act
        Mockito.when(supplierRepository.findAll()).thenReturn(suppliers);

        // Assert
        Assertions.assertEquals(suppliers, supplierService.retrieveAllSuppliers());

        // verify
        Mockito.verify(supplierRepository).findAll();
    }

    @Test
    void testDeleteSuppliers() {
        Mockito.doNothing().when(supplierRepository).deleteById(Mockito.anyLong());
        supplierService.deleteSupplier(1L);
        Mockito.verify(supplierRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testAddOperator() {
        Mockito.when(supplierRepository.save(supp)).thenReturn(supp);
        Supplier supp2 = supplierService.addSupplier(supp);
        assertEquals("20", supp2.getCode());
        assertEquals("label test", supp2.getLabel());
        assertEquals(SupplierCategory.CONVENTIONNE, supp2.getSupplierCategory());
        assertEquals(supp, supp2);
        Mockito.verify(supplierRepository, Mockito.times(1)).save(supp);
    }

    @Test
    void testRetrieveSupplier() {

        // Act
        Mockito.when(supplierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(supp));

        // Assert
        Assertions.assertEquals(supp, supplierService.retrieveSupplier(1L));

        // verify
        Mockito.verify(supplierRepository).findById(Mockito.anyLong());
    }

    @Test
    void testUpdateSupplier() {
        Mockito.when(supplierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(supp));
        Mockito.when(supplierRepository.save(updatedSupp)).thenReturn(updatedSupp);
        Supplier supplier2 = supplierService.updateSupplier(updatedSupp);
        assertEquals("30", supplier2.getCode());
        assertEquals("label test2", supplier2.getLabel());
        assertEquals(SupplierCategory.ORDINAIRE, supplier2.getSupplierCategory());
        assertEquals(updatedSupp, supplier2);
        Mockito.verify(supplierRepository, Mockito.times(1)).save(updatedSupp);
    }
}
