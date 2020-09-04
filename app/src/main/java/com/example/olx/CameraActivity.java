package com.example.olx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {

    private CameraView mCamera;
    private Button mRetry, mGallery, mClose, mClick, mCameraToggle;
//    private TextView mDetect;
//    private ImageView mImageView;
    private Bitmap bitmap;
    private ProgressBar progressBar2;
//    private ListView mListView;
    private String[] ResultItem;
    private String[] ResultName;

    private int PICK_IMAGE_REQUEST = 1;
    private Uri imgUri;
    private static final String MODEL_PATH = "model_unquant.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_camera);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mCamera = findViewById(R.id.activity_camera_camera_view);
        mGallery = findViewById(R.id.activity_camera_button_gallery);
        mClose = findViewById(R.id.activity_camera_button_close);
        mClick = findViewById(R.id.activity_camera_button_click);
        progressBar2 = findViewById(R.id.progressBar2);

        mRetry = findViewById(R.id.activity_camera_button_retry);
//        mDetect = findViewById(R.id.activity_camera_text_view_detect);
//        mImageView = findViewById(R.id.activity_camera_image_view);


//        mFlash = findViewById(R.id.activity_camera_button_flash);
        mCameraToggle = findViewById(R.id.activity_camera_button_camera_toggle);
//        mListView = findViewById(R.id.activity_camera_list_view);




        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(CameraActivity.this, Home.class)); }
        });
        mCamera.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {
//                Log.d("cameraerror","error");
            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                bitmap = cameraKitImage.getBitmap();
                detect();
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked();
            }
        });
        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
            }
        });
        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        mCameraToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.toggleFacing();
            }
        });
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                showDetails(i);
//            }
//        });
        initTensorFlowAndLoadModel();
    }

    private void showDetails(int i) {
        startActivity(new Intent(CameraActivity.this, Home.class).putExtra("DiseaseName", ResultName[i]));
    }

    private void retry() {
        mCamera.start();
        mCameraToggle.setVisibility(View.VISIBLE);
//        mFlash.setVisibility(View.VISIBLE);
        mClick.setVisibility(View.VISIBLE);
        mRetry.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("DefaultLocale")
    private void detect() {
//        mImageView.setImageBitmap(bitmap);
        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        mCameraToggle.setVisibility(View.INVISIBLE);
//        mFlash.setVisibility(View.INVISIBLE);
        mClick.setVisibility(View.INVISIBLE);
        mRetry.setVisibility(View.VISIBLE);
        try {
            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
//            mDetect.setText(results.get(0).getTitle());
            displayResult(results);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("DefaultLocale")
    private void displayResult(List<Classifier.Recognition> results) {
        ResultItem = new String[results.size()];
        ResultName = new String[results.size()];
        for (int i = 0; i < results.size(); i++){
            ResultItem[i] = results.get(i).getTitle() + "  " + String.format("(%.1f%%) ", results.get(i).getConfidence() * 100.0f);
            ResultName[i] = results.get(i).getTitle();
        }
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ResultItem);
//        mListView.setAdapter(myAdapter);



        Intent intent = new Intent(CameraActivity.this,Sollution.class);
        intent.putExtra("diseaseLable",ResultItem[0]);
        intent.putExtra("BitmapImage", bitmap);
        progressBar2.setVisibility(View.GONE);
        startActivity(intent);
    }


    private void clicked() {
        mCamera.captureImage();
        progressBar2.setVisibility(View.VISIBLE);
//        mCamera.stop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCamera.start();
    }

    @Override
    protected void onPause() {
        mCamera.stop();
        super.onPause();
    }
    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ){
            imgUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            detect();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
