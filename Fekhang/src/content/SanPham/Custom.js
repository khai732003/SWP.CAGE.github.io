import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Button,
  TextField,
  Select,
  MenuItem,
  FormControl,
  Grid,
  InputLabel,
} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import customAxios from "../../CustomAxios/customAxios";
import "./../dashboard/styles/addedituser.css";

export default function Custom() {
  const navigate = useNavigate();
  const [shapes, setShapes] = useState([]);
  const [sizes, setSizes] = useState([]);
  const [materials, setMaterials] = useState([]);
  const [selectedShape, setSelectedShape] = useState(null);
  const [selectedSize, setSelectedSize] = useState(null);
  const [selectedMaterial, setSelectedMaterial] = useState(null);
  const [categories, setCategories] = useState([]);
  const [sidePanelData, setSidePanelData] = useState([]);

  const [formData, setFormData] = useState({
    name: "CUSTOME PRODUCT",
    code: "CP PRODUCT",
    categoryId: "",
    productImage:
      "https://tse3.mm.bing.net/th?id=OIP.U5UDLyjPeHOjMtyEuBWr7gHaKe&pid=Api&P=0&h=180",
    stock: "",
    status: "customeProduct",
    note: "custome",
    cage: {
      description: "CUSTOME",
      shapeId: "",
      materialId: "",
      sizeId: "",
      spokes: "",
    },
    accessories: [], // Uncomment if you plan to use this field
  });

  const handleReturnPage = () => {
    navigate(-1);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;

    // If the property is nested, update the state accordingly
    if (name.includes("cage.")) {
      const nestedProperty = name.split("cage.")[1];
      setFormData((prevData) => ({
        ...prevData,
        cage: {
          ...prevData.cage,
          [nestedProperty]: value,
        },
      }));
    } else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value,
      }));
    }
  };

  const addCustomProductManagement = async () => {
    try {
      const response = await customAxios.post("/product/add", formData);

      if (response.status === 200) {
        navigate("/sanpham");
      }
    } catch (error) {
      // Log the detailed error response
      console.error("Detailed error response:", error.response);

      // Log the error message
      console.error("Error message:", error.message);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    addCustomProductManagement();
  };

  useEffect(() => {
    const fetchShapes = async () => {
      try {
        const response = await customAxios.get("/shapes/list");
        setShapes(response.data);
      } catch (error) {
        console.error("Error fetching shapes:", error);
      }
    };

    fetchShapes();
  }, []);

  useEffect(() => {
    const fetchSizes = async () => {
      try {
        const response = await customAxios.get("/sizes/list");
        setSizes(response.data);
      } catch (error) {
        console.error("Error fetching sizes:", error);
      }
    };

    fetchSizes();
  }, []);

  useEffect(() => {
    const fetchMaterials = async () => {
      try {
        const response = await customAxios.get("/materials/list");
        setMaterials(response.data);
      } catch (error) {
        console.error("Error fetching materials:", error);
      }
    };

    fetchMaterials();
  }, []);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await customAxios.get("/category/list");
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };
    fetchCategories();
  }, []);

  const renderCategories = () => {
    return categories.map((category) => (
      <MenuItem key={category.id} value={category.id}>
        {category.name}
      </MenuItem>
    ));
  };

  const handleChangeShape = (event) => {
    const selectedShapeId = event.target.value;
    setSelectedShape(selectedShapeId);
    // Update the formData.cage.shapeId instead of formData.shapeId
    setFormData((prevData) => ({
      ...prevData,
      cage: {
        ...prevData.cage,
        shapeId: selectedShapeId,
      },
    }));
  };

  const handleChangeSize = (event) => {
    const selectedSizeId = event.target.value;
    setSelectedSize(selectedSizeId);
    // Update the formData.cage.sizeId instead of formData.sizeId
    setFormData((prevData) => ({
      ...prevData,
      cage: {
        ...prevData.cage,
        sizeId: selectedSizeId,
      },
    }));
  };

  const handleChangeMaterial = (event) => {
    const selectedMaterialId = event.target.value;
    setSelectedMaterial(selectedMaterialId);
    // Update the formData.cage.materialId instead of formData.materialId
    setFormData((prevData) => ({
      ...prevData,
      cage: {
        ...prevData.cage,
        materialId: selectedMaterialId,
      },
    }));
  };
  console.log(formData);

  const updateSidePanelData = () => {
    // Create an array with selected options and their prices
    const newSidePanelData = [];

    // Push selected shape data
    if (selectedShape) {
      const shapeData = shapes.find((shape) => shape.id === selectedShape);
      newSidePanelData.push({
        label: "Shape",
        name: shapeData.shapeName,
        price: shapeData.price,
      });
    }

    if (selectedSize) {
      const sizeData = sizes.find((size) => size.id === selectedSize);
      newSidePanelData.push({
        label: "Size",
        name: sizeData.sizeName,
        price: sizeData.price,
      });
    }

    // Push selected material data
    if (selectedMaterial) {
      const materialData = materials.find(
        (material) => material.id === selectedMaterial
      );
      newSidePanelData.push({
        label: "Material",
        name: materialData.materialName,
        price: materialData.price,
      });
    }

    // Update the state
    setSidePanelData(newSidePanelData);
  };

  // const calculateTotal = () => {
  //   // Calculate the total price based on selected options
  //   let total = 0;
  //   sidePanelData.forEach((item) => {
  //     total += item.price;
  //   });
  //   return total;
  // };

  const calculateTotal = () => {
    // Calculate the total price based on selected options
    let total = 0;
  
    // Loop through sidePanelData to calculate the total
    sidePanelData.forEach((item) => {
      // Check if the item is related to size
      if (item.label === "Size") {
        // Get the price per spoke and the number of spokes
        const pricePerSpoke = selectedSize ? selectedSize.price : 0;
        const spokes = parseInt(formData.cage.spokes, 10) || 0;
  
        // Calculate the price for size based on the number of spokes
        const sizePrice = pricePerSpoke * spokes;
  
        // Add the calculated size price to the total
        total += sizePrice;
      } else {
        // For other items (material, shape), simply add their prices to the total
        total += item.price;
      }
    });
  
    return total;
  };
  

  const calculatePrice = () => {
    // Tính toán giá tiền dựa trên số lượng spokes và giá của size
    const selectedSizeData = sizes.find((size) => size.id === selectedSize);
    const pricePerSpoke = selectedSizeData ? selectedSizeData.price : 0;
    const spokes = parseInt(formData.cage.spokes, 10) || 0;
    return pricePerSpoke * spokes;
  };

  useEffect(() => {
    // Update side panel data whenever the selected options change
    updateSidePanelData();
  }, [selectedShape, selectedSize, selectedMaterial]);

  return (
    <div>
      <div
        className="add-edit-container"
        style={{ paddingTop: "70px", margin: "0" }}
      >
        <div className="form-add-edit">
          <form onSubmit={handleSubmit} style={{ width: 500 }}>
            <div
              className="d-flex justify-content-between align-items-center  mb-1 pb-1"
              style={{ paddingRight: 100 }}
            >
              <div
                className="mb-5 pb-lg-2"
                style={{
                  color: "#393f81",
                  position: "absolute",
                  left: "10%",
                  top: "20%",
                }}
              >
                <Button
                  sx={{ fontSize: 18 }}
                  variant="outlined"
                  startIcon={<ArrowBackIosIcon />}
                  onClick={handleReturnPage}
                >
                  BACK
                </Button>
              </div>
            </div>

            <div className="side-panel">
              <h2>Order Summary</h2>
              <ul>
                {sidePanelData.map((item, index) => (
                  <li key={index}>
                    {item.label}: {item.name} - Price: {item.price}
                  </li>
                ))}
                <li>Spokes Price: {calculatePrice()}</li>
              </ul>
              <p>Total: {calculateTotal()}</p>
            </div>

            {/* Category Select Input */}
            <FormControl fullWidth margin="normal">
              <InputLabel id="categoryIdLabel">Select Category</InputLabel>
              <Select
                labelId="categoryIdLabel"
                id="categoryId"
                name="categoryId"
                value={formData.categoryId}
                onChange={handleInputChange}
                fullWidth
                required
              >
                {renderCategories()}
              </Select>
            </FormControl>

            {/* Stock Input */}
            <TextField
              label="Stock"
              id="stock"
              name="stock"
              type="number"
              value={formData.stock}
              onChange={handleInputChange}
              fullWidth
              required
              margin="normal"
            />

            {/* Select Shape */}
            <FormControl fullWidth margin="normal">
              <InputLabel id="shapeIdLabel">Select Shape</InputLabel>
              <Select
                labelId="shapeIdLabel"
                id="shapeId"
                name="shapeId"
                value={selectedShape || ""}
                onChange={handleChangeShape}
                fullWidth
                required
              >
                {shapes.map((shape) => (
                  <MenuItem key={shape.id} value={shape.id}>
                    <Grid container justifyContent="space-between">
                      <Grid item>{shape.shapeName}</Grid>
                      <Grid item>{shape.price}</Grid>
                    </Grid>
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {/* Display selected shapeName based on shapeId */}
            {selectedShape &&
              shapes.find((shape) => shape.id === selectedShape)?.shapeName}

            {/* Select Material */}
            <FormControl fullWidth margin="normal">
              <InputLabel id="materialIdLabel">Select Material</InputLabel>
              <Select
                labelId="materialIdLabel"
                id="materialId"
                name="materialId"
                value={selectedMaterial}
                onChange={handleChangeMaterial}
                fullWidth
                required
              >
                {materials.map((material) => (
                  <MenuItem key={material.id} value={material.id}>
                    <Grid container justifyContent="space-between">
                      <Grid item>{material.materialName}</Grid>
                      <Grid item>{material.price}</Grid>
                    </Grid>
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {selectedMaterial &&
              materials.find((material) => material.id === selectedMaterial)
                ?.materialName}

            <FormControl fullWidth margin="normal">
              <InputLabel id="sizeIdLabel">Select Size</InputLabel>
              <Select
                labelId="sizeIdLabel"
                id="sizeId"
                name="sizeId"
                value={selectedSize || ""}
                onChange={handleChangeSize}
                fullWidth
                required
              >
                {sizes.map((size) => (
                  <MenuItem key={size.id} value={size.id}>
                    <Grid container justifyContent="space-between">
                      <Grid item>{size.sizeName}</Grid>
                      <Grid item>{size.price}</Grid>
                    </Grid>
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {/* Display selected sizeName based on sizeId */}
            {selectedSize &&
              sizes.find((size) => size.id === selectedSize)?.sizeName}

            
            <TextField
              label="Spokes"
              id="spokes"
              name="spokes"
              type="number"
              value={formData.cage.spokes}
              onChange={(event) =>
                setFormData({
                  ...formData,
                  cage: {
                    ...formData.cage,
                    spokes: event.target.value,
                  },
                })
              }
              fullWidth
              required
              margin="normal"
            />

            {/* Uncomment the following block if you plan to use the Accessories field */}
            {/* <TextField
                label="Accessories"
                id="accessories"
                name="accessories"
                value={formData.accessories}
                onChange={handleInputChange}
                fullWidth
                required
                margin="normal"
              /> */}

            {/* Submit Button */}
            <Button
              type="submit"
              variant="contained"
              color="primary"
              size="large"
            >
              Submit
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
}
