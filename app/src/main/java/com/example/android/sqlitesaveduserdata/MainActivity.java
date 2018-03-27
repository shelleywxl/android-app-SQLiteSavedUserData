package com.example.android.sqlitesaveduserdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;
import android.database.Cursor;


public class MainActivity extends AppCompatActivity {

    //SQLiteOpenHelper dbHelper = new DatabaseHelper(this);
    DatabaseHelper myDb;
    EditText etName, etEmail, etShow, etId;
    Button btnAdd, btnView, btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.name_et);
        etEmail = (EditText) findViewById(R.id.email_et);
        etShow = (EditText) findViewById(R.id.show_et);
        etId = (EditText) findViewById(R.id.id_et);
        btnAdd = (Button) findViewById(R.id.add_btn);
        btnView = (Button) findViewById(R.id.view_btn);
        btnUpdate = (Button) findViewById(R.id.update_btn);
        btnDelete = (Button) findViewById(R.id.delete_btn);


        // for adding data
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAdded = myDb.dbAddData(etName.getText().toString(),
                        etEmail.getText().toString(),
                        etShow.getText().toString());
                if (isAdded)
                    Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data NOT Added", Toast.LENGTH_LONG).show();
            }
        });


        //for viewing data
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.dbViewData();
                if (res.getCount() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Error" );
                    builder.setMessage("Nothing found" );
                    builder.show();

                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0) + "\n" );
                    buffer.append("Name: " + res.getString(1) + "\n" );
                    buffer.append("Email: " + res.getString(2) + "\n" );
                    buffer.append("Favorite TV Show: " + res.getString(3) + "\n" );
                }
                //showMessage("All Stored Data:", buffer.toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("All Stored Data:" );
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


        // for updating data
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etId.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Need ID number to update", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isUpdated = myDb.dbUpdateData(etId.getText().toString(),
                        etName.getText().toString(),
                        etEmail.getText().toString(),
                        etShow.getText().toString());
                if (isUpdated)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data NOT Updated", Toast.LENGTH_LONG).show();
            }
        });


        // for deleting data
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.dbDeleteData(etId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data NOT Deleted", Toast.LENGTH_LONG).show();
            }
        });

    }
}
