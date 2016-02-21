package trembleproductions.swift;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.ArrayList;

import trembleproductions.swift.main.MapNode;
import trembleproductions.swift.main.TouchImageView;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    ArrayList<MapNode> chosenNodes = new ArrayList<>();
    Button route;
    Button clear;
    EditText startText;
    EditText endText;
    TouchImageView imageview;
    Canvas lineACanvas;
    Bitmap lineABmp;
    Bitmap lineAMutBmp;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Era of Navigation");
        setSupportActionBar(toolbar);
        imageview = (TouchImageView)findViewById(R.id.view);
        imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageview.setImageResource(R.drawable.ccm);

        lineABmp = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
        lineAMutBmp = lineABmp.copy(Bitmap.Config.ARGB_8888, true);

        lineACanvas = new Canvas(lineAMutBmp);

        addListenerOnImageView();

        addListenerOnRoute();
        addListenerOnClear();

    }



    public void createLine(float x, float y, float xend, float yend, int color){
            Paint p = new Paint();
            p.setColor(color);
            p.setStrokeWidth(10);
            lineACanvas.drawLine(x, y, xend, yend, p);
            imageview.setImageBitmap(lineAMutBmp);
    }

    public void addListenerOnImageView() {
        imageview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float x = event.getX();
                float y = event.getY();
                Paint p = new Paint();
                p.setColor(Color.RED);
                p.setStrokeWidth(15);
                p.setStyle(Paint.Style.FILL);
                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        startText.setText("ACTION_DOWN- " + x + " : " + y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        startText.setText("ACTION_MOVE- " + x + " : " + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        startText.setText("ACTION_UP- " + x + " : " + y);
                        lineACanvas.drawCircle(x, y, 10, p);
                        break;
                }
                return true;
            }
        });
    }

    public void addListenerOnClear(){
        clear = (Button) findViewById(R.id.Clear);
        startText = (EditText)findViewById(R.id.startText);
        endText = (EditText)findViewById(R.id.endText);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startText.setText("", TextView.BufferType.EDITABLE);
                endText.setText("", TextView.BufferType.EDITABLE);
                imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageview.setImageResource(R.drawable.ccm);

                lineABmp = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                lineAMutBmp = lineABmp.copy(Bitmap.Config.ARGB_8888, true);

                lineACanvas = new Canvas(lineAMutBmp);
                route.setEnabled(true);
            }
        });
    }

    public void addListenerOnRoute(){
        route = (Button) findViewById(R.id.Route);
        startText = (EditText) findViewById(R.id.startText);
        route.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createLine((float) 0, (float) 0, (float) 1000, (float) 500, Color.BLUE);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(15);
                paint.setStyle(Paint.Style.FILL);
                lineACanvas.drawCircle((float) 1000, (float) 500, 200, paint);
                route.setEnabled(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        int itemId = 2;
        int groupId = Menu.NONE;
        int order = 103;
        menu.add(groupId, itemId, order, "Maps");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
    }
}


