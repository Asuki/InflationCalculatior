package seng.hu.inflacio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ValueEditor extends AppCompatActivity {

    private static final String TAG = "ValueEditor";
    EditText editTextNewValue;
    EditText editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_editor);

        editTextNewValue = findViewById(R.id.editTextNewValueEdit);
        editTextQuantity = findViewById(R.id.editTextQuantityEdit);

        Intent intent = getIntent();

        editTextNewValue.setText(intent.getStringExtra(MainActivity.NEW_ROUNDED_VALUE));
        editTextQuantity.setText(intent.getStringExtra(MainActivity.QUANTITY));
    }

    public void saveValue_onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.NEW_ROUNDED_VALUE, editTextNewValue.getText().toString());
        intent.putExtra(MainActivity.QUANTITY, editTextQuantity.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
