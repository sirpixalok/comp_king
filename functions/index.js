const functions = require("firebase-functions");
const admin = require("firebase-admin");
const cors = require("cors")({ origin: true });
const axios = require("axios");

admin.initializeApp();
const db = admin.firestore();

// Gen 2 function with 1GiB memory and 120s timeout
exports.getSalesData = functions
  .runWith({ gen: "2", memory: "1GiB", timeoutSeconds: 120 })
  .https.onRequest((req, res) => {
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

// Gen 2 function with 512MB memory and 60s timeout
exports.addSale = functions
  .runWith({ gen: "2", memory: "512MB", timeoutSeconds: 60 })
  .https.onRequest((req, res) => {
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
