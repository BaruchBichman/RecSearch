package com.example.baruch.recsearch;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initBranchesList() {

        new AsyncTask<Void, Void, List<>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Branch> doInBackground(Void... params) {
                return DB_ManagerFactory.getDb_manager().getBranches();
            }

            @Override
            protected void onPostExecute(List<Branch> branches) {
                branchesList = branches;
                progressDialog[0].dismiss();
                if (branches != null) {
                    setBranchesListView();
                }
                else {
                    Toast.makeText(ManageBranches.this, "there are no branches to show", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
