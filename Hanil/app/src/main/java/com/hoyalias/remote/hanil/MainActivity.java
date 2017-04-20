package com.hoyalias.remote.hanil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int FREQUENCY = 38000;

    private static final int[][] POWER = {
            {4474, 4368, 632, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1605, 605, 1605, 605, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 1605, 605, 1579, 632, 1579, 632, 1579, 632},
            {4552, 4302, 635, 562, 619, 562, 615, 562, 614, 566, 615, 1556, 639, 1560, 639, 1560, 635, 1560, 639, 562, 615, 562, 615, 562, 615, 566, 615, 562, 614, 566, 615, 562, 615, 566, 614, 1564, 635, 1564, 636, 1584, 614, 1560, 639, 1560, 639, 1560, 639, 1581, 614, 1581, 615, 58545, 4552, 4306, 640}
    };
    private static final int[][] TIMER = {
            {4500, 4395, 605, 579, 605, 553, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1605, 605, 1605, 605, 1579, 632, 1579, 632, 579, 605, 579, 605, 579, 605, 553, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 1605, 605},
            {4548, 4302, 639, 562, 615, 562, 615, 562, 618, 562, 614, 1585, 615, 1585, 615, 1556, 639, 1560, 639, 1581, 619, 1556, 639, 562, 615, 562, 615, 562, 619, 563, 614, 562, 619, 562, 615, 562, 639, 541, 618, 1585, 639, 1535, 639, 1560, 664, 1531, 668, 1556, 639, 1531, 664, 58670, 4553, 4306, 639}
    };
    private static final int[][] WINDDIRECTION = {
            {4474, 4368, 632, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 579, 605, 579, 605, 1579, 632, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1605, 605, 579, 605, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 1579, 632},
            {4555, 4304, 640, 562, 611, 562, 615, 562, 615, 567, 615, 1583, 615, 1562, 636, 1583, 615, 1558, 640, 563, 615, 567, 611, 1558, 640, 562, 615, 562, 616, 567, 615, 562, 616, 562, 615, 1563, 640, 1558, 640, 567, 615, 1583, 615, 1583, 616, 1587, 615, 1583, 615, 1558, 636, 58601, 4555, 4308, 640}
    };
    private static final int[][] WINDPOWER = {
            {4500, 4395, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1605, 605, 1605, 605, 1605, 605, 1579, 632, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 553, 605, 579, 605, 579, 605, 1579, 632, 1579, 632, 1579, 632, 1579, 632, 1605, 605, 1579, 632, 1579, 632},
            {4552, 4302, 639, 562, 614, 562, 616, 561, 615, 566, 615, 1560, 639, 1556, 639, 1560, 640, 1581, 615, 1556, 639, 562, 619, 562, 614, 562, 614, 562, 619, 562, 615, 562, 619, 562, 615, 566, 614, 1564, 635, 1560, 639, 1560, 639, 1560, 639, 1560, 639, 1581, 614, 1581, 614, 58302, 4553, 4306, 640}
    };
    private static final int[][] WINDTYPE = {
            {4500, 4395, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 1605, 605, 1605, 605, 1579, 632, 579, 605, 1579, 632, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 579, 605, 1579, 632, 579, 605, 1579, 632, 1605, 605, 1605, 605, 1605, 605, 1605, 605, 1605, 605},
            {4548, 4306, 635, 562, 615, 562, 619, 562, 615, 561, 619, 1560, 635, 1585, 615, 1556, 640, 1555, 639, 566, 615, 1581, 614, 562, 619, 562, 614, 562, 614, 566, 615, 562, 615, 566, 615, 1560, 639, 566, 615, 1585, 615, 1580, 619, 1560, 635, 1585, 615, 1560, 639, 1556, 664, 58274, 4552, 4306, 639}
    };

    private static final int[] NULL_BUTTON = {0};

    private ConsumerIrManager manager = null;

    private int fanType = 0;

    public void clickHandler(View paramView) {
        int[] buttonType = NULL_BUTTON;

        int buttonId = paramView.getId();

        if (buttonId == R.id.power)
            buttonType = POWER[fanType];
        else if (buttonId == R.id.windpower)
            buttonType = WINDPOWER[fanType];
        else if (buttonId == R.id.timer)
            buttonType = TIMER[fanType];
        else if (buttonId == R.id.winddirection)
            buttonType = WINDDIRECTION[fanType];
        else if (buttonId == R.id.windtype)
            buttonType = WINDTYPE[fanType];

        this.manager.transmit(38000, buttonType);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.manager = ((ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE));

        if (this.manager == null) {
            Toast.makeText(this, R.string.error_not_supported_device, Toast.LENGTH_SHORT).show();
            finish();
        }

        Spinner spin = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{getResources().getString(R.string.common), getResources().getString(R.string.baby)});
        spin.setAdapter(spinAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   fanType = i;
                   TextView tv = ((TextView) adapterView.getChildAt(0));
                   Button windType = (Button) findViewById(R.id.windtype);
                   if (fanType == 0) {
                       windType.setText(getResources().getString(R.string.windtype));
                       tv.setTextColor(Color.BLUE);

                   } else {
                       windType.setText(getResources().getString(R.string.baby));
                       tv.setTextColor(Color.RED);
                   }

                   SharedPreferences prefs = getSharedPreferences("hanil", MODE_PRIVATE);
                   SharedPreferences.Editor editor = prefs.edit();
                   editor.putInt("FAN_TYPE", fanType);
                   editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            }
        );

        SharedPreferences prefs = getSharedPreferences("hanil", MODE_PRIVATE);
        fanType = prefs.getInt("FAN_TYPE", 0);

        spin.setSelection(fanType);
    }
}
