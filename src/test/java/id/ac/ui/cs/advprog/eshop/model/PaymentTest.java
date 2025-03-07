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
        testPaymentData.put("voucherCode", "ESHOP1234ABC5678");
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
        Payment payment = new Payment("a123b456-c789-0123-d456-ef78901234gh", "INVALID_METHOD",
                testPaymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
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
        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "VOUCHER", testPaymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidFieldKey() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("notVoucher", "SHOP5678XYZ1234");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "VOUCHER", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeTooShort() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("voucherCode", "X");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "VOUCHER", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeWrongPrefix() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("voucherCode", "STORE5678XYZ1234");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "VOUCHER", promoData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPromoCodeMissingDigits() {
        Map<String, String> promoData = new HashMap<>();
        promoData.put("voucherCode", "SHOPABCDEFGHJKLMN");

        Payment payment = new Payment("a1b2c3d4-e5f6-7890-ghij-klmnopqrstuv",
                "VOUCHER", promoData);

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

    @Test
    void shouldAcceptValidBankTransfer() {
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("bankName", "Mandiri");
        transactionDetails.put("referenceCode", "PROMO123");

        Payment transaction = new Payment("a123b456-c789-0123-d456-7890efghijkl",
                "BANK_TRANSFER", transactionDetails);

        assertEquals(PaymentStatus.SUCCESS.getValue(), transaction.getStatus());
    }

    @Test
    void shouldRejectBankTransferWithEmptyFields() {
        Map<String, String> missingRefCode = new HashMap<>();
        missingRefCode.put("bankName", "Mandiri");
        missingRefCode.put("referenceCode", "");

        Payment transactionMissingRef = new Payment("123e4567-e89b-12d3-a456-426614174001",
                "BANK_TRANSFER", missingRefCode);

        Map<String, String> missingBank = new HashMap<>();
        missingBank.put("bankName", "");
        missingBank.put("referenceCode", "PROMO123");

        Payment transactionMissingBank = new Payment("987f6543-d21a-34b5-c678-9abcde123456",
                "BANK_TRANSFER", missingBank);

        assertEquals(PaymentStatus.REJECTED.getValue(), transactionMissingRef.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), transactionMissingBank.getStatus());
    }

    @Test
    void shouldRejectBankTransferWithNullValues() {
        Map<String, String> nullRefCode = new HashMap<>();
        nullRefCode.put("bankName", "Mandiri");
        nullRefCode.put("referenceCode", null);

        Payment transactionNullRef = new Payment("9f8e7d6c-5b4a-3210-f987-654321abcdef",
                "BANK_TRANSFER", nullRefCode);

        Map<String, String> nullBank = new HashMap<>();
        nullBank.put("bankName", null);
        nullBank.put("referenceCode", "PROMO123");

        Payment transactionNullBank = new Payment("abcdef12-3456-7890-ghij-klmnopqrstuv",
                "BANK_TRANSFER", nullBank);

        assertEquals(PaymentStatus.REJECTED.getValue(), transactionNullRef.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), transactionNullBank.getStatus());
    }

    @Test
    void shouldRejectBankTransferWithInvalidData() {
        Map<String, String> incorrectDetails = new HashMap<>();
        incorrectDetails.put("voucherCode", "ESHOP5678XYZ1234");

        Payment invalidTransaction = new Payment("1a2b3c4d-5e6f-7890-ghij-klmnopqrstuv",
                "BANK_TRANSFER", incorrectDetails);

        assertEquals(PaymentStatus.REJECTED.getValue(), invalidTransaction.getStatus());
    }
}
