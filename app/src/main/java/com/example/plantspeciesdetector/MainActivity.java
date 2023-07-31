package com.example.plantspeciesdetector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantspeciesdetector.R;
import com.example.plantspeciesdetector.ml.LiteModelAiyVisionClassifierPlantsV13;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICKER = 2;
    private static final int DESIRED_IMAGE_SIZE = 224;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Plant Detector");
        Button camera = findViewById(R.id.camera);
        Button gallery = findViewById(R.id.gallery);
        imageView = findViewById(R.id.input);
        TextView output = findViewById(R.id.output);
        Button predict = findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LiteModelAiyVisionClassifierPlantsV13 model = LiteModelAiyVisionClassifierPlantsV13.newInstance(MainActivity.this);

                    // Get the Bitmap from the ImageView
                    Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    // Create inputs for reference.
                    TensorImage image = TensorImage.fromBitmap(imageBitmap);

                    // Runs model inference and gets the result.
                    LiteModelAiyVisionClassifierPlantsV13.Outputs outputs = model.process(image);
                    List<Category> probability = outputs.getProbabilityAsCategoryList();

                    // Sort the list in descending order of confidence scores
                    Collections.sort(probability, new Comparator<Category>() {
                        @Override
                        public int compare(Category o1, Category o2) {
                            return Float.compare(o2.getScore(), o1.getScore());
                        }
                    });

                    // Get the top prediction
                    Category topPrediction = probability.get(0);
                    String topLabel = topPrediction.getLabel();
                    float topConfidence = topPrediction.getScore();

                    // Display the top prediction in the output TextView
                    String outputText = "Plant Name:  " + topLabel;
                    output.setText(outputText);

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO Handle the exception
                }
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getResizedBitmap(imageBitmap, DESIRED_IMAGE_SIZE);
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageBitmap = getResizedBitmap(imageBitmap, DESIRED_IMAGE_SIZE);
                imageView.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image from gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int size) {
        float scale = Math.min((float) size / bitmap.getWidth(), (float) size / bitmap.getHeight());
        int width = Math.round(scale * bitmap.getWidth());
        int height = Math.round(scale * bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
