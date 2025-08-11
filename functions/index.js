const functions = require("firebase-functions");
const admin = require("firebase-admin");
const cors = require("cors")({ origin: true });
const axios = require("axios");

admin.initializeApp();
const db = admin.firestore();

// Example endpoint: Get sample sales data
exports.getSalesData = functions.https.onRequest((req, res) => {
  cors(req, res, async () => {
    try {
      const snapshot = await db.collection("sales").limit(10).get();
      const sales = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
      res.json({ success: true, sales });
    } catch (err) {
      console.error(err);
      res.status(500).json({ success: false, error: err.message });
    }
  });
});

// Example endpoint: Add new sales record
exports.addSale = functions.https.onRequest((req, res) => {
  cors(req, res, async () => {
    try {
      const saleData = req.body;
      const ref = await db.collection("sales").add(saleData);
      res.json({ success: true, id: ref.id });
    } catch (err) {
      console.error(err);
      res.status(500).json({ success: false, error: err.message });
    }
  });
});
