const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
const path = require("path");



const app = express();
app.use(cors());
app.use(express.json());
app.use("/assets", express.static(path.join(__dirname, "public")));

// ✅ Connexion MongoDB Atlas avec tes vraies infos
const uri = 'mongodb+srv://simobambou:45545247@ecom.0am7o58.mongodb.net/ecom?retryWrites=true&w=majority&appName=ecom';

mongoose.connect(uri)
  .then(() => {
    console.log("✅ Connecté à MongoDB Atlas");
    app.listen(3000, '0.0.0.0', () => {
      console.log("🚀 Serveur démarré sur http://localhost:3000");
    });
  })
  .catch((error) => console.error("❌ Erreur de connexion MongoDB :", error));
// ✅ Schéma User
const userSchema = new mongoose.Schema({
  id: String,
  email: String,
  password: String,
  name : String,
  token: String,
  role: String
});
const User = mongoose.model('User', userSchema);
// ✅ Schéma Mongoose
const productSchema = new mongoose.Schema({
  productID: { type: String, required: true, unique: true },
  productTitle: String,
  productPrice: Number,
  productOldPrice: Number,
  productImageResURL: String,
  productQuantity: Number,
  productDescription: String,
  productCategory: String,
});

// ✅ Register
app.post("/register", async (req, res) => {
  try {
    const user = new User(req.body);
    await user.save();
    res.status(201).json({ message: "Utilisateur inscrit avec succès." });
  } catch (err) {
    res.status(500).json({ error: "Échec d'inscription." });
  }
});

// ✅ Login
app.post("/login", async (req, res) => {
  const { email, password } = req.body;
  const user = await User.findOne({ email, password });
  if (user) {
    res.status(200).json(user);
  } else {
    res.status(401).json({ error: "Identifiants incorrects." });
  }
});


// ✅ Modèle
const Product = mongoose.model("Product", productSchema);

// ✅ Route POST pour ajouter un ou plusieurs produits
app.post("/products", async (req, res) => {
  try {
    const data = req.body;
    if (Array.isArray(data)) {
      await Product.insertMany(data);
      res.status(201).json({ message: "Produits insérés avec succès." });
    } else {
      await Product.create(data);
      res.status(201).json({ message: "Produit inséré avec succès." });
    }
  } catch (err) {
    console.error("Erreur d'insertion :", err);
    res.status(500).json({ error: "Échec d'insertion." });
  }
});
// ✅ Route GET pour récupérer tous les produits
app.get("/products", async (req, res) => {
  try {
    const products = await Product.find();
    res.status(200).json(products);
  } catch (err) {
    console.error("Erreur de récupération :", err);
    res.status(500).json({ error: "Échec de récupération." });
  }
});
app.put("/products/:id", async (req, res) => {
  try {
    const updated = await Product.findOneAndUpdate(
      { productID: req.params.id },
      req.body,
      { new: true }
    );
    if (!updated) return res.status(404).json({ error: "Produit introuvable" });
    res.status(200).json(updated);
  } catch (err) {
    res.status(500).json({ error: "Erreur de mise à jour." });
  }
});

// Dans ton fichier backend, par ex. src/api-server/index.js ou routes/product.js

app.delete('/products/:id', async (req, res) => {
  try {
    const result = await Product.findOneAndDelete({ productID: req.params.id });
    if (!result) {
      return res.status(404).json({ message: "Produit non trouvé" });
    }
    res.status(200).json({ message: "Produit supprimé" });
  } catch (error) {
    res.status(500).json({ message: "Erreur serveur lors de la suppression" });
  }
});


