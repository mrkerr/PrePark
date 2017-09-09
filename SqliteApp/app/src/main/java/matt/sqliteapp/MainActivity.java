package matt.sqliteapp;

import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editPhone_Number, editTextId;
    Button btnAddData, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_Name);
        editSurname = (EditText)findViewById(R.id.editText_Surname);
        editPhone_Number = (EditText)findViewById(R.id.editText_Phone_Number);
        editTextId = (EditText)findViewById(R.id.editText_ID);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_viewAll);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_remove);
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editPhone_Number.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                       Cursor result = myDb.getAllData();
                        if (result.getCount() == 0) {
                            //show message
                            showMessage("Error", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext()) {
                            buffer.append("Id : "+ result.getString(0)+"\n");
                            buffer.append("Name : "+ result.getString(1)+"\n");
                            buffer.append("Surname : "+ result.getString(2)+"\n");
                            buffer.append("Phone_Number : "+ result.getString(3)+"\n\n");
                        }

                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(),
                                editSurname.getText().toString(), editPhone_Number.getText().toString());
                        if(isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        int deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
