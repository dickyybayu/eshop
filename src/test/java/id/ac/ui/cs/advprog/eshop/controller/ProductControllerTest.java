package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for createProductPage()
    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));

        assertEquals("createProduct", viewName);
    }

    // Test for createProductPost() when product is valid
    @Test
    void testCreateProductPostValid() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Product A");
        product.setProductQuantity(100);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.create(product)).thenReturn(product);

        String result = productController.createProductPost(product, bindingResult);
        assertEquals("redirect:list", result);
        verify(productService).create(product);
    }

    // Test for createProductPost() when product has validation errors
    @Test
    void testCreateProductPostInvalid() {
        Product product = new Product();

        // Simulate validation error
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = productController.createProductPost(product, bindingResult);
        assertEquals("createProduct", result);
    }

    // Test for productListPage()
    @Test
    void testProductListPage() {
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product A");

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product B");

        when(productService.findAll()).thenReturn(List.of(product1, product2));

        String result = productController.productListPage(model);
        assertEquals("productList", result);
        verify(productService).findAll();
        verify(model).addAttribute(eq("products"), anyList());
    }

    // Test for editProductPage()
    @Test
    void testEditProductPage() {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Product A");

        when(productService.findById(productId)).thenReturn(product);

        String result = productController.editProductPage(productId, model);
        assertEquals("editProduct", result);
        verify(productService).findById(productId);
        verify(model).addAttribute("product", product);
    }

    // Test for editProductPost() when the product is valid
    @Test
    void testEditProductPostValid() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Product A");
        updatedProduct.setProductQuantity(100);

        when(bindingResult.hasErrors()).thenReturn(false);
        String result = productController.editProductPost(updatedProduct, bindingResult);
        assertEquals("redirect:/product/list", result);
        verify(productService).update(updatedProduct);
    }

    // Test for editProductPost() when product has validation errors
    @Test
    void testEditProductPostInvalid() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Product A");
        updatedProduct.setProductQuantity(100);

        // Simulate validation errors
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = productController.editProductPost(updatedProduct, bindingResult);
        assertEquals("editProduct", result);
    }

    // Test for deleteProduct()
    @Test
    void testDeleteProduct() {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Product A");

        when(productService.findById(productId)).thenReturn(product);

        String result = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", result);
        verify(productService).delete(product);
    }

    // Test for deleteProduct() when product is not found
    @Test
    void testDeleteProductNotFound() {
        String productId = "1";

        when(productService.findById(productId)).thenReturn(null);

        String result = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", result);
        verify(productService, never()).delete(any());
    }
}
