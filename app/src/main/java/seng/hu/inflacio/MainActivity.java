package seng.hu.inflacio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ValueData> valueDataArrayList;
    EditText editTextValue;
    EditText editTextQuantity;
    double rate = 5;
    int selectedIndex;
    private final String TAG = "MainActivity";
    static final String STATE_VALUES = "Value list";
    public static final String NEW_ROUNDED_VALUE = "new rounded value";
    public static final String QUANTITY = "quantity";
    private static final int VALUE_EDITOR_REQUEST_CODE = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change layout to night / day mode
        if(item.getItemId() == R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

        recreate();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedIndex = -1;
        listView = findViewById(R.id.listViewValues);
        valueDataArrayList = new ArrayList<>();
        editTextValue = findViewById(R.id.editTextValue);
        editTextQuantity = findViewById(R.id.editTextQuantity);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemClick: " + adapterView.getItemAtPosition(i).toString());
                //valueDataArrayList.get(i).setNewRoundedValue(300);
                Intent intent = new Intent(MainActivity.this, ValueEditor.class);
                Bundle bundle = new Bundle();
                bundle.putString(NEW_ROUNDED_VALUE, String.valueOf(valueDataArrayList.get(i).getNewRoundedValue()));
                bundle.putString(QUANTITY, String.valueOf(valueDataArrayList.get(i).quantity));
                intent.putExtras(bundle);
                //intent.putExtra(NEW_ROUNDED_VALUE, 3);
                //startActivity(intent);
                startActivityForResult(intent, VALUE_EDITOR_REQUEST_CODE);
                //Log.i(TAG, "onItemClick - new value in intent:" + intent.getStringExtra(NEW_ROUNDED_VALUE));
                //populateListView();
                selectedIndex = i;

            }
        });

        if(savedInstanceState != null){
            valueDataArrayList = savedInstanceState.getParcelableArrayList(STATE_VALUES);
            populateListView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VALUE_EDITOR_REQUEST_CODE){
            if (selectedIndex >= 0){
                valueDataArrayList.get(selectedIndex).setNewRoundedValue(Integer.parseInt(data.getStringExtra(NEW_ROUNDED_VALUE)));
                valueDataArrayList.get(selectedIndex).setQuantity(Integer.parseInt(data.getStringExtra(QUANTITY)));
            }
            populateListView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_VALUES, valueDataArrayList);
        super.onSaveInstanceState(outState);
    }

    public void calcValue(View view){
        Log.i(TAG, "calcValue editText value: " + editTextValue.getText());
        int curValue = Integer.parseInt(editTextValue.getText().toString());
        int curQuantity = Integer.parseInt(editTextQuantity.getText().toString());

        // Adding calculated values to array list
        Log.i(TAG, "calcValue: " + curValue);
        emptyEditTexts();
        valueDataArrayList.add(new ValueData(curValue, curQuantity, rate));
        populateListView();
        ValueEditorFragment valueEditorFragment = new ValueEditorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, valueEditorFragment).commit();

    }

    private void emptyEditTexts() {
        editTextValue.setText("");
        editTextQuantity.setText("");
        editTextValue.requestFocus();
    }

    private void populateListView() {
        //Collections.sort(values);
        Intent intent = getIntent();
        Log.i(TAG, "populateListView: " + intent.getStringExtra(NEW_ROUNDED_VALUE));

        ArrayAdapter adapter = new ValueDataAdapter(this, R.layout.adapter_view5_col, valueDataArrayList);
        listView.setAdapter(adapter);
    }


}
