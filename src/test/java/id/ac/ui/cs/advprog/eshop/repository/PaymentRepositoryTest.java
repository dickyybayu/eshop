package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository testPaymentRepository;
    List<Payment> testPayments;
    Order testOrder;

    @BeforeEach
    void setUp() {
        testPaymentRepository = new PaymentRepository();
        testPayments = new ArrayList<>();

        List<Product> productList = new ArrayList<>();
        Product shampoo = new Product();
        shampoo.setProductId("a1b2c3d4-e5f6-7890-gh12-ijkl34567890");
        shampoo.setProductName("Shampoo Super Fresh");
        shampoo.setProductQuantity(2);
        productList.add(shampoo);

        testOrder = new Order("xyz98765-4321-abcd-efgh-56789ijklmno",
                productList, 1708565000L, "Test User");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP5678XYZ1234");
        Payment payment1 = new Payment("mnop1234-5678-abcd-efgh-ijkl98765432", "VOUCHER", paymentData1);
        testPayments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP8765ZYX4321");
        Payment payment2 = new Payment("qrst5678-9101-uvwx-yzab-cdef45678901", "VOUCHER", paymentData2);
        testPayments.add(payment2);
    }

    @Test
    void testSavePayment() {
        Payment payment = testPayments.get(0);
        Payment result = testPaymentRepository.save(testOrder, payment);

        Payment retrievedPayment = testPaymentRepository.findById(testPayments.get(0).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getStatus(), retrievedPayment.getStatus());
        assertEquals(payment.getMethod(), retrievedPayment.getMethod());
        assertSame(payment.getPaymentData(), retrievedPayment.getPaymentData());
    }

    @Test
    void testFindByIdExists() {
        for (Payment payment : testPayments) {
            testPaymentRepository.save(testOrder, payment);
        }

        Payment retrievedPayment = testPaymentRepository.findById(testPayments.get(0).getId());
        assertEquals(testPayments.get(0).getId(), retrievedPayment.getId());
        assertEquals(testPayments.get(0).getMethod(), retrievedPayment.getMethod());
        assertEquals(testPayments.get(0).getStatus(), retrievedPayment.getStatus());
        assertSame(testPayments.get(0).getPaymentData(), retrievedPayment.getPaymentData());
    }

    @Test
    void testFindByIdNotExists() {
        for (Payment payment : testPayments) {
            testPaymentRepository.save(testOrder, payment);
        }

        Payment retrievedPayment = testPaymentRepository.findById("unknown_id");
        assertNull(retrievedPayment);
    }

    @Test
    void testFindAllPayments() {
        for (Payment payment : testPayments) {
            testPaymentRepository.save(testOrder, payment);
        }

        List<Payment> retrievedPayments = testPaymentRepository.findAll();
        assertEquals(2, retrievedPayments.size());
    }

    @Test
    void testFindOrderExists() {
        Payment payment = testPayments.get(0);
        testPaymentRepository.save(testOrder, payment);

        Payment retrievedPayment = testPaymentRepository.findById(testPayments.get(0).getId());
        Order retrievedOrder = testPaymentRepository.getOrder(retrievedPayment.getId());

        assertEquals(testOrder.getId(), retrievedOrder.getId());
        assertEquals(testOrder.getStatus(), retrievedOrder.getStatus());
        assertEquals(testOrder.getAuthor(), retrievedOrder.getAuthor());
        assertEquals(testOrder.getOrderTime(), retrievedOrder.getOrderTime());
        assertEquals(testOrder.getProducts(), retrievedOrder.getProducts());
    }

    @Test
    void testFindOrderNotExists() {
        Order retrievedOrder = testPaymentRepository.getOrder("unknown_order");
        assertNull(retrievedOrder);
    }
}
