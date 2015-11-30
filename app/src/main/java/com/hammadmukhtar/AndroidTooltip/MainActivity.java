package com.hammadmukhtar.AndroidTooltip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hammadmukhtar.tooltip.Tooltip;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Tooltip tooltipWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.btn_top_left);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.btn_top_right);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.btn_bottom_left);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.btn_bottom_right);
        b4.setOnClickListener(this);
        b5 = (Button) findViewById(R.id.btn_center);
        b5.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_top_left:
                tooltipWindow = new Tooltip(this);
                tooltipWindow.showToolTip(b1,"Top Left","Dismiss");
                break;
            case R.id.btn_top_right:
                tooltipWindow = new Tooltip(this);
                tooltipWindow.showToolTip(b2,"Top Right","Dismiss");
                break;
            case R.id.btn_bottom_left:
                tooltipWindow = new Tooltip(this);
                tooltipWindow.showToolTip(b3,"Bottom Right","Dismiss");
                break;
            case R.id.btn_bottom_right:
                tooltipWindow = new Tooltip(this);
                tooltipWindow.showToolTip(b4,"Bottom Right","Dismiss");
                break;
            case R.id.btn_center:
                tooltipWindow = new Tooltip(this);
                tooltipWindow.showToolTip(b5,"Center","Dismiss");
                break;

        }
    }
}
