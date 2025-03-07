package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentServiceTest;

    @Mock
    PaymentRepository mockPaymentRepository;

    @Mock
    OrderService mockOrderService;

    Order sampleOrder;
    Map<String, String> testPaymentData1;
    Map<String, String> testPaymentData2;
    List<Payment> testPayments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        List<Product> productSamples = new ArrayList<>();
        Product shampoo = new Product();
        shampoo.setProductId("abcd1234-5678-90ef-ghij-klmn456789op");
        shampoo.setProductName("Shampoo Ultra Clean");
        shampoo.setProductQuantity(3);
        productSamples.add(shampoo);

        sampleOrder = new Order("order-xyz-1234-abcd-5678-90ef-ghij",
                productSamples, 1708568000L, "TestUser");

        testPaymentData1 = new HashMap<>();
        testPaymentData1.put("voucherCode", "TEST1234XYZ5678");
        Payment payment1 = new Payment("pay-uuid-1234-abcd-5678-efgh-ijkl",
                "VOUCHER", testPaymentData1);
        testPayments.add(payment1);

        testPaymentData2 = new HashMap<>();
        testPaymentData2.put("voucherCode", "TEST9876XYZ4321");
        Payment payment2 = new Payment("pay-uuid-5678-mnop-qrst-uvwx-yzab",
                "VOUCHER", testPaymentData2);
        testPayments.add(payment2);
    }

    @Test
    void testAddPayment() {
        when(mockPaymentRepository.save(eq(sampleOrder), any(Payment.class))).thenAnswer(invocation -> {
            return invocation.<Payment>getArgument(1);
        });

        Payment result = paymentServiceTest.addPayment(sampleOrder, "VOUCHER", testPaymentData1);

        assertNotNull(result);
        assertEquals("VOUCHER", result.getMethod());
        assertEquals(testPaymentData1, result.getPaymentData());

        verify(mockPaymentRepository).save(eq(sampleOrder), any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = testPayments.get(0);

        when(mockPaymentRepository.getOrder(payment.getId())).thenReturn(sampleOrder);
        when(mockPaymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(payment);
        when(mockOrderService.updateStatus(anyString(), anyString())).thenReturn(sampleOrder);

        Payment updatedPayment = paymentServiceTest.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        verify(mockOrderService, times(1)).updateStatus(sampleOrder.getId(), OrderStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatusToRejected(){
        Payment payment = testPayments.get(0);

        when(mockPaymentRepository.getOrder(payment.getId())).thenReturn(sampleOrder);
        when(mockPaymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(payment);
        when(mockOrderService.updateStatus(anyString(), anyString())).thenReturn(sampleOrder);

        Payment updatedPayment = paymentServiceTest.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());
        verify(mockOrderService, times(1)).updateStatus(sampleOrder.getId(), OrderStatus.FAILED.getValue());
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = testPayments.get(0);
        mockPaymentRepository.save(sampleOrder, payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentServiceTest.setStatus(payment, "INVALID_STATUS");
        });
    }

    @Test
    void testGetPaymentByIdSuccess() {
        Payment payment = testPayments.get(0);
        doReturn(payment).when(mockPaymentRepository).findById(payment.getId());

        Payment result = paymentServiceTest.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentByIdNotFound() {
        doReturn(null).when(mockPaymentRepository).findById("unknown_id");
        assertNull(paymentServiceTest.getPayment("unknown_id"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(testPayments).when(mockPaymentRepository).findAll();

        List<Payment> results = paymentServiceTest.getAllPayments();

        assertEquals(2, results.size());
    }
}
