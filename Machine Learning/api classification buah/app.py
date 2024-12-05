from flask import Flask, request, jsonify
from PIL import Image
import numpy as np
from tensorflow.keras.preprocessing.image import ImageDataGenerator, load_img, img_to_array
from tensorflow import keras
import tensorflow as tf
import csv
import io
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

model = keras.models.load_model("final.h5")

def transform_image(img):
    img = img.resize((224, 224))
    img = img_to_array(img)
    img = img.astype(np.float32) / 255
    img = np.expand_dims(img, axis=0)
    return img


def predict(x):
    predictions = model.predict(x)
    return predictions


app = Flask(__name__)
@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        file = request.files.get('file')
        if file is None or file.filename == "":
            return jsonify({"error": "no file"})
        try:
            class_names = ["Apel","Anggur","Buah Naga","Ceri", "Durian", "Jambu Biji","Jeruk", "Kiwi", "Lemon", "Mangga", "Nanas", "Pir","Pisang", "Semangka", "Stroberi" ]
            image_bytes = file.read()
            pillow_img = Image.open(io.BytesIO(image_bytes))
            predictions = predict(transform_image(pillow_img))
            predicted_class_index = tf.argmax(predictions[0]).numpy()
            predicted_class = class_names[predicted_class_index]

            # Membaca info gizi
            df = pd.read_csv("Informasi Gizi Buah.csv", sep=',')
            # Menghilangkan spasi tambahan di kolom 'Bahan'
            df['Bahan'] = df['Bahan'].str.strip()
            # Nama makanan
            food_names = df['Bahan'].values
            # Data Makanan
            data = df[[
                'Kalori', 'Lemak(g)', 'Karbohidrat(g)', 'Protein(g)']].values
            # Normalisasi data
            data_norm = (data - np.min(data, axis=0)) / \
                (np.max(data, axis=0) - np.min(data, axis=0))

            # Mengambil informasi nutrisi berdasarkan kelas prediksi
            predicted_class = class_names[predicted_class_index]
            nutrient_info = df.loc[df['Bahan'] == predicted_class, [
                'Kalori', 'Lemak(g)', 'Karbohidrat(g)', 'Protein(g)', 'Ukuran',  'Keterangan']]

            # Konversi nutrient_info menjadi dictionary
            nutrient_info_dict = nutrient_info.to_dict(orient='records')
        except Exception as e:
            return jsonify({"error": str(e)})
    return "OK"

if __name__ == "__main__":
    app.run(debug=True)