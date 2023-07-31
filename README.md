# Plant_Species_Detector_App


The Plant Detector Android App is an application that allows users to identify plants by capturing images through the camera or selecting images from the gallery using the image picker. The app leverages a machine learning model based on TensorFlow Lite to perform plant recognition and provide users with the scientific name of the identified plant.

## Features

- Plant Identification: Users can identify plants by taking pictures of the plant using the device's camera or selecting images from the gallery using the image picker.
- Scientific Name Display: The app will display the scientific name of the identified plant in a TextView.

## Requirements

- Android 6.0 (Marshmallow) or higher.
- Camera and storage permissions.

## Installation

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.

## How to Use

1. Launch the Plant Detector App on your Android device.
2. Grant the required camera and storage permissions if prompted.
3. Choose the "Capture Image" option to take a picture of the plant using the device's camera or select "Pick from Gallery" to choose an existing image from the gallery.
4. The machine learning model will process the image and identify the plant.
5. The scientific name of the identified plant will be displayed in the TextView.

## TensorFlow Lite Model

The machine learning model used for plant recognition is based on TensorFlow Lite, a lightweight version of TensorFlow designed for mobile and embedded devices. The model has been trained on a large dataset of plant images to accurately classify plants and provide reliable results.

The TensorFlow Lite model is integrated into the Android app to perform real-time plant identification efficiently on the device without requiring an internet connection. The model's size and performance have been optimized to ensure a smooth user experience while providing accurate results.

## Model Accuracy and Updates

The accuracy of the plant detection model may vary based on the quality of the input images and the diversity of the plant dataset used for training. We are continuously working to improve the model's accuracy and may release updates to the app periodically to incorporate better models.

## Contributing

If you find any issues, bugs, or have suggestions for improvements, feel free to open an issue or create a pull request in the GitHub repository. We welcome contributions from the community to make the Plant Detector Android App even better!


## Disclaimer

The Plant Detector App is designed to assist users in identifying plants and providing scientific names. While the machine learning model is trained to be accurate, it may not always provide correct results. Users should exercise caution and not rely solely on the app's output for critical applications such as medical or environmental decisions.

The app may also encounter difficulties in identifying certain plant species or new/uncommon plants that are not part of the model's training dataset.

## Contact

For any inquiries or support related to the Plant Detector Android App, please contact us at abhishekverma2002cs@gmail.com.

Thank you for using the Plant Detector App! We hope you find it useful and enjoyable for identifying the amazing flora around you!
