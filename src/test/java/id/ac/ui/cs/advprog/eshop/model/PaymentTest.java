package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
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
        payment.setStatus(PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToInvalidValue() {
        Payment payment = new Payment("a123b456-c789-0123-d456-ef78901234gh", "VOUCHER", testPaymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }

    @Test
    void testValidPromoCode() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("promoCode", "SHOP5678XYZ1234");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "PROMO", promoData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidFieldKey() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("wrongKey", "SHOP5678XYZ1234");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "PROMO", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeTooShort() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("promoCode", "X");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "PROMO", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeWrongPrefix() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("promoCode", "STORE5678XYZ1234");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "PROMO", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeMissingDigits() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("promoCode", "SHOPABCDEFGHJKLMN");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "PROMO", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeIsNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

    }
}
