package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (method == null || method.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (paymentData == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.validateData();
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void validateData() {
        boolean isValid = false;
        switch (this.method) {
            case "VOUCHER":
                isValid = validateVoucherMethod();
                break;

            default:
                break;
        }

        if (isValid) {
            status = PaymentStatus.SUCCESS.getValue();
        } else {
            status = PaymentStatus.REJECTED.getValue();
        }
    }

    private boolean validateVoucherMethod() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null) {
            return false;
        }

        if (checkVoucherCode(voucherCode)) {
            return true;
        }

        return false;
    }

    private boolean checkVoucherCode(String voucherCode) {
        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        String code = voucherCode.substring(5);
        int numericCharCount = 0;
        for (char character : code.toCharArray()) {
            if (Character.isDigit(character)) {
                numericCharCount++;
            }
        }

        if (numericCharCount != 8) {
            return false;
        }

        return true;
    }
}