import React from "react";
import "./Product.css"; // Import CSS file
import { useCart } from "./Context/CartContext";

const Product = ({ id, name, stock, totalPrice, productImage, code, material, size }) => {
  const { addToCart } = useCart();

  const handleAddToCart = () => {
    addToCart({ id, name, stock, totalPrice, productImage, code });
    window.alert(`Added ${name} to the cart!`);
  };

  return (
    <div className="col-md-12">
      <div className="product-card card mb-4">
        <img src={productImage} alt={name} className="card-img-top" />
        <div className="card-body">
          <h5 className="card-title">{name}</h5>
          <p className="card-text">ID: {id}</p>
          <p className="card-text">Stock: {stock}</p>
          <p className="card-text">Price: ${totalPrice}</p>
          <p className="card-text">Code: {code}</p>
          <p className="card-text">Material: {material}</p>
          <p className="card-text">Size: {size}</p>

          <div className="buttons">
            <button className="column-button">Detail</button>
            <button onClick={handleAddToCart}>Add to Cart</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Product;