package seng.hu.inflacio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ValueEditor extends AppCompatActivity {

    private static final String TAG = "ValueEditor";
    // Data tags
    // EditText for editing vlues
    EditText editTextNewValue;
    EditText editTextQuantity;
    EditText editTextOldValue;
    private final double rate = 1.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_editor);

        // Connects local variables with layout items
        editTextOldValue = findViewById(R.id.editTextOldValueEdit);
        editTextNewValue = findViewById(R.id.editTextNewValueEdit);
        editTextQuantity = findViewById(R.id.editTextQuantityEdit);

        Intent intent = getIntent();

        // Getting the values from the calling activity and showing them in EditText boxes
        editTextOldValue.setText(intent.getStringExtra(MainActivity.OLD_VALUE));
        editTextNewValue.setText(intent.getStringExtra(MainActivity.NEW_ROUNDED_VALUE));
        editTextQuantity.setText(intent.getStringExtra(MainActivity.QUANTITY));
    }

    public void saveValue_onClick(View view) {
        // Sending back the values to the calling Activity
        Intent intent = new Intent();
        intent.putExtra(MainActivity.OLD_VALUE, editTextOldValue.getText().toString());
        intent.putExtra(MainActivity.NEW_ROUNDED_VALUE, editTextNewValue.getText().toString());
        intent.putExtra(MainActivity.QUANTITY, editTextQuantity.getText().toString());

        setResult(RESULT_OK, intent);
        Log.i(TAG, "saveValue_onClick: value sent back");
        finish();
    }

    public void recalculate_onClick(View view){
        int oldValue = Integer.parseInt(editTextOldValue.getText().toString());
        int newValue = getNewInflation(oldValue);

        editTextNewValue.setText(String.valueOf(newValue));
    }

    private int getNewInflation(int prevValue){
        int result;
        int tmpValue;
        int mod;

        tmpValue = (int) (prevValue * rate);
        mod = tmpValue % 5;

        if (mod == 0)
            result = tmpValue;
        else if (mod <= 2)
            result = tmpValue - mod;
        else
            result = tmpValue + (5 - mod);

        return result;
    }
}
