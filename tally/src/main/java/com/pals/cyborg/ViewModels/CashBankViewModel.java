package com.pals.cyborg.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pals.cyborg.Interface.OnNetworkTaskCompleted;
import com.pals.cyborg.Interface.OnParseCompleted;
import com.pals.cyborg.Models.CashandBankModel;
import com.pals.cyborg.Parsers.CashBankParser;
import com.pals.cyborg.Repository.NetworkRepository;
import com.pals.cyborg.Utils.PayLoads;

import java.util.ArrayList;

public class CashBankViewModel extends ViewModel implements OnNetworkTaskCompleted, OnParseCompleted {

    private MutableLiveData<ArrayList<CashandBankModel>> model;

    private MutableLiveData<String> error;

    private PayLoads payLoads;

    private MutableLiveData<Boolean> hasData;

    private NetworkRepository networkRepository;

    public  CashBankViewModel(){
        this.model = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        hasData = new MutableLiveData<>();
        this.payLoads = new PayLoads();
        this.networkRepository = new NetworkRepository(this);
    }

    public LiveData<Boolean> hasData(){
        return hasData;
    }

     public LiveData<String> getError(){
        return  error;
     }

    public LiveData<ArrayList<CashandBankModel>> getData(){
       pullData();
        return model;
    }

    public void updateView(String fromDate,String toDate){
        networkRepository.doTask(payLoads.getBankCashPayLoad(fromDate,toDate));

    }

    private void pullData() {
        networkRepository.doTask(payLoads.getBankCashPayLoad("0","0"));
    }

    @Override
    public void OnSuccess(String res) {
     new CashBankParser(this).execute(res);
    }

    @Override
    public void OnError(String err) {
         error.setValue(err);
    }

    @Override
    public void OnParsed(ArrayList<?> data) {
        if(data.size() > 0){
            hasData.setValue(true);
            model.setValue((ArrayList<CashandBankModel>) data);
        }else {
            hasData.setValue(false);
        }
    }

    @Override
    public void OnParseFailed(String err) {
        error.setValue(err);
    }
}
