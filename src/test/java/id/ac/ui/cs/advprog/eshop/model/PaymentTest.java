package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Order testOrder;
    private Map<String, String> testPaymentData;

    @BeforeEach
    void setUp() {
        testPaymentData = new HashMap<>();
        testPaymentData.put("voucherCode", "ESHOP5678XYZ1234");
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment("a123b456-c789-0123-d456-ef78901234gh", "VOUCHER", testPaymentData);

        assertEquals("a123b456-c789-0123-d456-ef78901234gh", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(testPaymentData, payment.getPaymentData());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a123b456-c789-0123-d456-ef78901234gh", "INVALID_METHOD", testPaymentData);
        });
    }

    @Test
    void testCreatePaymentWithNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a123b456-c789-0123-d456-ef78901234gh", "VOUCHER", null);
        });
    }

    @Test
    void testSetPaymentStatusToRejected() {
        Payment payment = new Payment("a123b456-c789-0123-d456-ef78901234gh", "VOUCHER", testPaymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToInvalidValue() {
        Payment payment = new Payment("a123b456-c789-0123-d456-ef78901234gh", "VOUCHER", testPaymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }
}
