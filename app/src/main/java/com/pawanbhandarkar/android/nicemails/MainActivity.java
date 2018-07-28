package com.pawanbhandarkar.android.nicemails;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {


    public static final  String BASE_EMAIL_START = "Dear sir,\n" +
            "This is to inform you that I have collected the renewal cheque from:\n";
    public static final String BASE_EMAIL_END = "\n\nWith Regards,\n" +
            "Kashinath Bhandarkar\n9000128034";
    
    public static String Email= BASE_EMAIL_START ;
    EditText NameET, 
             DateET, 
             PolicyET,
             ChequeET,
             AmountET,
             BranchET;

    Button  SubmitButton,
            AddButton, 
            ClearButton, 
            PreviewButton;
    
    String[] addresses = {"bhojaraja.k@nic.co.in","604301@nic.co.in"};
    
    String  subject = "Renewal Premium Collected",
            Name="",
            PolicyNumber="604301/",
            ChequeNumber="",
            BankName="",
            DueDate="",
            Amount="",
            Branch="";
    
    Spinner BankSpinner;




    final String LOG_TAG =  this.getClass().getName();
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NameET = (EditText)findViewById(R.id.NameEditText);
        PolicyET = (EditText)findViewById(R.id.PolicyNumberEditText);
        ChequeET = (EditText)findViewById(R.id.ChequeNumberEditText);
        AmountET = (EditText)findViewById(R.id.AmountEditText);
        DateET = (EditText)findViewById(R.id.DateEditText);
        BranchET = (EditText)findViewById(R.id.BranchEditText);
        BankSpinner = (Spinner) findViewById(R.id.BankNamesSpinner);
        AddButton = (Button)findViewById(R.id.AddButton);
        ClearButton = (Button)findViewById(R.id.ClearButton);
        PreviewButton = (Button)findViewById(R.id.PreviewButton);
        SubmitButton = (Button)findViewById(R.id.SendButton);





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Banks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BankSpinner.setAdapter(adapter);

        DateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

       

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("The Email content will be erased and reset. Are you sure you want to clear everything?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                clearAll(true);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateET.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        };


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                BankName= "Bank :"+BankSpinner.getSelectedItem().toString().toUpperCase();
                Name = NameET.getText().toString();
                PolicyNumber= PolicyET.getText().toString();
                ChequeNumber= ChequeET.getText().toString();
                Branch = BranchET.getText().toString();
                Amount= AmountET.getText().toString();
                DueDate= DateET.getText().toString();

                if(Name.isEmpty() || PolicyNumber == "604301/" || DueDate.isEmpty())
                    Toast.makeText(MainActivity.this, "Missing Details ", Toast.LENGTH_LONG).show();
                else {
                    Email += "\nName : " + Name;
                    Email += "\nPolicy Number : " + PolicyNumber;
                    Email += "\nAmount : ₹" + Amount + "/-";
                    Email += "\nCheque Number : " + ChequeNumber;
                    Email += "\n" + BankName;
                    Email += "\nBranch : " + Branch;
                    Email += "\nDue on : " + DueDate;
                    clearAll(false);
                }
            }
        });


        PreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(MainActivity.this, PreviewPopUp.class);
                startActivity(intent);
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                if(!Email.endsWith(BASE_EMAIL_END))
                    Email+=BASE_EMAIL_END;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, Email);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);

            }
        });



    }
    
    
    private void clearAll(Boolean reset)
    {
        if(reset)
            Email = BASE_EMAIL_START;
        
        NameET.setText("");
        PolicyET.setText("604301/");
        ChequeET.setText("");
        AmountET.setText("");
        DateET.setText("");
        BranchET.setText("");
    }
    





}
