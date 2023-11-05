package tn.esprit.devops_project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    private OperatorServiceImpl operatorService;

    private Operator operator;
    private Operator oldOperator;

    @BeforeEach
    void setUp() {
        operatorService = new OperatorServiceImpl(operatorRepository);
        operator = new Operator(
                1L,
                "fname",
                "lname",
                "password",
                null);

        oldOperator = new Operator(
                1L,
                "fname2",
                "lname2",
                "password2",
                null);
    }

    // test Get method
    @Test
    void testRetrieveOperator() {
        Mockito.when(operatorRepository.findById(1L)).thenReturn(Optional.of(operator));
        Operator operator2 = operatorService.retrieveOperator(1L);
        assertEquals("fname", operator2.getFname());
        assertEquals("lname", operator2.getLname());
        assertEquals("password", operator2.getPassword());
        assertEquals(operator, operator2);
        Mockito.verify(operatorRepository, Mockito.times(1)).findById(1L);
    }

    // test Post method
    @Test
    void testAddOperator() {
        Mockito.when(operatorRepository.save(operator)).thenReturn(operator);
        Operator operator2 = operatorService.addOperator(operator);
        assertEquals("fname", operator2.getFname());
        assertEquals("lname", operator2.getLname());
        assertEquals("password", operator2.getPassword());
        assertEquals(operator, operator2);
        Mockito.verify(operatorRepository, Mockito.times(1)).save(operator);
    }

    // test Put method
    @Test
    void testUpdateOperator() {
        Mockito.when(operatorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(operator));
         Mockito.when(operatorRepository.save(oldOperator)).thenReturn(oldOperator);
        Operator operator2 = operatorService.updateOperator(oldOperator);
       
        assertEquals("fname2", operator2.getFname());
        assertEquals("lname2", operator2.getLname());
        assertEquals("password2", operator2.getPassword());
        assertEquals(oldOperator, operator2);
        Mockito.verify(operatorRepository, Mockito.times(1)).save(oldOperator);
    }

    // test Delete method
    @Test
    void testDeleteOperator() {
        Mockito.doNothing().when(operatorRepository).deleteById(Mockito.anyLong());
        operatorService.deleteOperator(1L);
        Mockito.verify(operatorRepository, Mockito.times(1)).deleteById(1L);
    }

    // test Get RetrieveAllmethod
    @Test
    void testRetrieveAllOperators() {
        Mockito.when(operatorRepository.findAll()).thenReturn(java.util.Arrays.asList(operator));
        assertEquals(1, operatorService.retrieveAllOperators().size());
        Mockito.verify(operatorRepository, Mockito.times(1)).findAll();
    }
}
