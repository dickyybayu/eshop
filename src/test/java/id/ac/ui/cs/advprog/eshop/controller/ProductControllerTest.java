package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

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
        String result = productController.createProductPost(product, bindingResult, model);
        assertEquals("redirect:list", result);
        verify(productService).create(product);
    }

    // Test for createProductPost() when product has validation errors
    @Test
    void testCreateProductPostInvalid() {
        Product product = new Product();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = productController.createProductPost(product, bindingResult, model);
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
        assertEquals("productlist", result);
        verify(productService).findAll();
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
    }

    // Test for editProductPost() (editing product after form submission)
    @Test
    void testEditProductPost() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Product A");
        product.setProductQuantity(100);

        productController.editProductPost(product, model);

        verify(productService).update(product);
        String viewName = productController.editProductPost(product, model);
        assertEquals("redirect:/product/list", viewName);
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
