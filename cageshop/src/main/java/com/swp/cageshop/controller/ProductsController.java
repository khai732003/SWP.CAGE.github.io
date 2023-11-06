package com.swp.cageshop.controller;

import com.swp.cageshop.DTO.AccessoryDTO;
import com.swp.cageshop.DTO.BirdCageDTO;
import com.swp.cageshop.DTO.CategoryDTO;
import com.swp.cageshop.DTO.ProductDTO;
import com.swp.cageshop.entity.BirdCages;
import com.swp.cageshop.service.categoriesService.ICategoriesService;
import com.swp.cageshop.service.ordersService.IOrdersService;
import com.swp.cageshop.service.productsService.IBirdCagesService;
import com.swp.cageshop.service.productsService.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cageshop/api")
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @Autowired
    private ICategoriesService categoriesService;


    @Autowired(required = false)
    private IBirdCagesService birdCageService;

    @Autowired
    private IOrdersService iOrdersService;

//    @DeleteMapping("/product/deleteall")
//    public void deleteAll(){
//        productsService.deleteAll();
//    }

    @GetMapping("/product/top3")
    public List<ProductDTO> getTop3(){
        return productsService.getTop3NewestProductDTOs();
    }
    @PostMapping("/product/test")
    public ResponseEntity<?> addsProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO != null) {
            // Fetch all available categories
            List<CategoryDTO> availableCategories = categoriesService.getAllCategories();
            if (!availableCategories.isEmpty()) {
                // Return available categories and let the front-end handle the selection
                return ResponseEntity.ok(availableCategories);
            } else {
                return ResponseEntity.badRequest().body("No categories available.");
            }
        }
        return ResponseEntity.badRequest().body("ProductDTO is null.");
    }

    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO != null){
            Long categoryId = productDTO.getCategoryId();
            CategoryDTO category = categoriesService.getOneCategory(categoryId);
            if (category != null) {
                productDTO.setCategoryId(categoryId);

                ProductDTO savedProductDTO = productsService.addProduct(productDTO);

                if (savedProductDTO != null) {
                    return ResponseEntity.ok(savedProductDTO);
                } else {
                    return ResponseEntity.badRequest().body("Failed to add product.");
                }
            } else {
                return ResponseEntity.badRequest().body("Category not found.");
            }
        }
        return ResponseEntity.badRequest().body("ProductDTO is null.");
    }
    @PostMapping("/product/add-accessories")
    public ResponseEntity<?> addAccessoriesToProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO != null) {
            Long productId = productDTO.getId();
            List<AccessoryDTO> accessories = productDTO.getAccessories();
            ProductDTO updatedProduct = productsService.addAccessoriesToProduct(productId, accessories);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("ProductDTO is null.");
    }
    @PostMapping("/product/add-accessories/{productId}")
    public ResponseEntity<ProductDTO> addAccessoriesToProduct(
            @PathVariable Long productId,
            @RequestBody List<AccessoryDTO> accessories) {
        ProductDTO updatedProduct = productsService.addAccessoriesToProduct(productId, accessories);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/product/clone-and-add-accessories/{productId}")
    public ResponseEntity<ProductDTO> cloneAndAddAccessoriesToProduct(
            @PathVariable Long productId,
            @RequestBody List<AccessoryDTO> accessories) {
        ProductDTO updatedProduct = productsService.cloneAndAddAccessories(productId, accessories);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/product/get-list")
    public List<ProductDTO> listProducts() {
        return productsService.listAllProducts();
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        boolean deleted = productsService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete product.");
        }
    }

    @GetMapping("/product/select/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable long id) {
        ProductDTO productDTO = productsService.listProducts(id);
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productsService.updateProduct(id, productDTO);
        if (updatedProductDTO != null) {
            return ResponseEntity.ok(updatedProductDTO);
        } else {
            return ResponseEntity.badRequest().body("Failed to update product.");
        }
    }



    @GetMapping("/product/list-without-cage")
    public ResponseEntity<List<ProductDTO>> getProductsWithoutCage() {
        List<ProductDTO> productsWithoutCage = productsService.findProductsWithoutCage();
        return ResponseEntity.ok(productsWithoutCage);
    }

    @GetMapping("/product/list-with-accessories")
    public ResponseEntity<List<ProductDTO>> getProductsWithAccessories() {
        List<ProductDTO> productsWithAccessories = productsService.findProductsWithAccessories();
        return ResponseEntity.ok(productsWithAccessories);
    }

    @GetMapping("/product/list-without-accessories")
    public ResponseEntity<List<ProductDTO>> getProductsWithoutAccessories() {
        List<ProductDTO> productsWithoutAccessories = productsService.findProductsWithoutAccessories();
        return ResponseEntity.ok(productsWithoutAccessories);
    }

    @GetMapping("/product/list-with-cage-and-accessories")
    public ResponseEntity<List<ProductDTO>> getProductsWithCageWithAccessories() {
        List<ProductDTO> productsWithoutAccessories = productsService.productsWithCageWithAccessories();
        return ResponseEntity.ok(productsWithoutAccessories);
    }


    @GetMapping("/product/list-price-asc")
    public List<ProductDTO> getProductsByTotalPriceAsc() {
        return productsService.getProductsByTotalPriceAsc();
    }

    @GetMapping("/product/list-price-desc")
    public List<ProductDTO> getProductsByTotalPriceDesc() {
        return productsService.getProductsByTotalPriceDesc();
    }



    @GetMapping("/product/list-date-asc")
    public List<ProductDTO> getProductsByCreateDateAsc() {
        return productsService.getProductsSortedByCreateDateASC();
    }

    @GetMapping("/product/list-date-desc")
    public List<ProductDTO> getProductsByCreateDateDesc() {
        return productsService.getProductsSortedByCreateDateDESC();
    }


    @GetMapping("/product/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Long categoryId) {
        return productsService.getProductsByCategory(categoryId);
    }

    @GetMapping("/product/outofstock")
    public List<ProductDTO> getProductsOutOfStock() {
        return productsService.getProductsOutOfStock();
    }

    @GetMapping("/product/available")
    public List<ProductDTO> getProductsByStatusAvailable() {
        return productsService.getProductsByStatusAvailable();
    }

    @GetMapping("/product/nomoremade")
    public List<ProductDTO> getProductsStatusNoMoreMade() {
        return productsService.getProductsStatusNoMoreMade();
    }

    @GetMapping("/product/new")
    public List<ProductDTO> getProductsByStatusNew() {
        return productsService.getProductsByStatusNew();
    }


//    @PostMapping("/{orderId}/add-product/{productId}")
//    public ResponseEntity<String> moveProductToOrderDetail(
//            @PathVariable Long orderId,
//            @PathVariable Long productId) {
//        if (productsService.moveProductToOrderDetail(orderId, productId)) {
//            return new ResponseEntity<>("Product moved to order detail successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to move product to order detail", HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/product/search/{keyword}")
    public ResponseEntity<List<ProductDTO>> searchProductsByKeyword(@PathVariable String keyword) {
        List<ProductDTO> products = productsService.searchProductsByKeyword(keyword);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/product/price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @PathVariable double minPrice,
            @PathVariable double maxPrice) {
        List<ProductDTO> products = productsService.getProductsByPriceRange(minPrice, maxPrice);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


//    @GetMapping("/{productId}/accessories")
//    public ResponseEntity<List<AccessoryDTO>> getProductAccessories(@PathVariable Long productId) {
//        List<AccessoryDTO> accessories = productsService.getProductAccessories(productId);
//
//        if (!accessories.isEmpty()) {
//            return new ResponseEntity<>(accessories, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

    @GetMapping("/product/size/{size}")
    public ResponseEntity<List<ProductDTO>> getProductsBySize(@PathVariable String size) {
        List<ProductDTO> products = productsService.getProductsBySize(size);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/product/material/{material}")
    public ResponseEntity<List<ProductDTO>> getProductsByMaterial(@PathVariable String material) {
        List<ProductDTO> products = productsService.getProductsByMaterial(material);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/product/accessories-type/{accessoryType}")
    public ResponseEntity<List<ProductDTO>> getProductsByAccessoriesType(@PathVariable String accessoryType) {
        List<ProductDTO> products = productsService.getProductsByAccessoriesType(accessoryType);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


//    @GetMapping("/type/{type}/products")
//    public ResponseEntity<List<ProductDTO>> getProductsByType(@PathVariable String type) {
//        List<ProductDTO> products = productsService.getProductsByType(type);
//
//        if (!products.isEmpty()) {
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

//

    @GetMapping("/product/sorted-by/{sortBy}")
    public ResponseEntity<List<ProductDTO>> getProductsSortedBy(@PathVariable String sortBy) {
        List<ProductDTO> products = productsService.getProductsSortedBy(sortBy);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/product/limited-stock/{maxStock}")
    public ResponseEntity<List<ProductDTO>> getProductsWithLimitedStock(@PathVariable int maxStock) {
        List<ProductDTO> products = productsService.getProductsWithLimitedStock(maxStock);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/product/release-date/{startDate}-{endDate}")
    public ResponseEntity<List<ProductDTO>> getProductsByReleaseDateRange(
            @PathVariable String startDate,
            @PathVariable String endDate) {
        List<ProductDTO> products = productsService.getProductsByReleaseDateRange(startDate, endDate);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/product/select/{productId}/birdcage")
    public ResponseEntity<?> getBirdCageForProduct(@PathVariable Long productId) {
        // Retrieve the product based on the productId
        ProductDTO product = productsService.listProducts(productId);

        if (product != null) {
            // Retrieve the bird cage associated with the product
            BirdCages birdCage = birdCageService.getBirdCageByProductId(productId);

            if (birdCage != null) {
                return ResponseEntity.ok(birdCage);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

// @GetMapping("/recommended/{count}/products")
//    public ResponseEntity<List<ProductDTO>> getRecommendedProducts(@PathVariable int count) {
//        List<ProductDTO> products = productsService.getRecommendedProducts(count);
//
//        if (!products.isEmpty()) {
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
//
//    @GetMapping("/best-selling/{count}/products")
//    public ResponseEntity<List<ProductDTO>> getBestSellingProducts(@PathVariable int count) {
//        List<ProductDTO> products = productsService.getBestSellingProducts(count);
//
//        if (!products.isEmpty()) {
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }



//    @PostMapping("/product/addWithAccessories")
//    public ResponseEntity<?> addProductWithAccessories(@RequestBody ProductDTO mainProductDTO, @RequestParam Long categoryId, @RequestBody List<ProductDTO> accessoryDTOs) {
//        ProductDTO savedMainProductDTO = productsService.addProductWithAccessories(mainProductDTO, accessoryDTOs, categoryId);
//
//        if (savedMainProductDTO != null) {
//            return ResponseEntity.ok(savedMainProductDTO);
//        } else {
//            return ResponseEntity.badRequest().body("Failed to add main product.");
//        }
//    }
//    @PostMapping("/product/addwithbirdcage")
//    public ResponseEntity<?> addProductWithBirdCage(@RequestBody ProductDTO productDTO, @RequestBody BirdCageDTO birdCageDTO) {
//        ProductDTO savedProductDTO = productsService.addProductWithBirdCage(productDTO, birdCageDTO);
//
//        if (savedProductDTO != null) {
//            return ResponseEntity.ok(savedProductDTO);
//        } else {
//            return ResponseEntity.badRequest().body("Failed to add product with bird cage.");
//        }
//    }







//    @PostMapping("/{productCategoryId}/add-with-accessories")
//    public ResponseEntity<ProductDTO> addProductWithAccessories(
//            @PathVariable Long productCategoryId,
//            @RequestBody List<AccessoryDTO> accessories) {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setCategoryId(productCategoryId);
//
//        ProductDTO savedProductDTO = productsService.addProductWithAccessories(productDTO, accessories);
//
//        if (savedProductDTO != null) {
//            return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//    @PostMapping("/{productCategoryId}/add-with-birdcage")
//    public ResponseEntity<ProductDTO> addProductWithBirdCage(
//            @PathVariable Long productCategoryId,
//            @RequestBody ProductDTO productDTO,
//            @RequestBody BirdCageDTO birdCageDTO) {
//        productDTO.setCategoryId(productCategoryId);
//
//        ProductDTO savedProductDTO = productsService.addProductWithBirdCage(productDTO, birdCageDTO);
//
//        if (savedProductDTO != null) {
//            return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }




































//SAVE ENTITY



    //    @GetMapping("/products/byCategory")
////    public ResponseEntity<List<Products>> getProductsByCategory(@RequestParam Long categoryId) {
//////        Categories category = "con cac anh hung";
////        if (category != null) {
////            List<Products> products = productsService.getProductsByCategory(category);
////            return ResponseEntity.ok(products);
////        } else {
////            return ResponseEntity.badRequest().body(null);
////        }
//    }


//    @PostMapping("/product/add")
//    public ResponseEntity<?> addProduct(@RequestBody Products product, @RequestParam Long categoryId) {
//        Categories category = categoriesService.getOneCategory(categoryId);
//        if (category != null) {
//            product.setCategory(category);
//            Products savedProduct = productsService.addProducts(product);
//            return ResponseEntity.ok(savedProduct);
//        } else {
//            return ResponseEntity.badRequest().body("Category not found.");
//        }
//    }
//
//    @GetMapping("/product/list")
//    public List<Products> listProducts() {
//        return productsService.listAllProducts();
//    }
//
//    @DeleteMapping("/product/delete/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
//        boolean deleted = productsService.deleteProducts(id);
//        if (deleted) {
//            return ResponseEntity.ok("Product deleted successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Failed to delete product.");
//        }
//    }
//
//    @GetMapping("/product/{id}")
//    public ResponseEntity<Products> getProduct(@PathVariable long id) {
//        Products product = productsService.listProducts(id);
//        if (product != null) {
//            return ResponseEntity.ok(product);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/product/update/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody Products product) {
//        Products updatedProduct = productsService.updateProducts(id, product);
//        if (updatedProduct != null) {
//            return ResponseEntity.ok(updatedProduct);
//        } else {
//            return ResponseEntity.badRequest().body("Failed to update product.");
//        }
//    }

