package com.pals.cyborg.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pals.cyborg.Interface.OnNetworkTaskCompleted;
import com.pals.cyborg.Interface.OnParseCompleted;
import com.pals.cyborg.Models.LedgerModel;
import com.pals.cyborg.Parsers.LedgerParser;
import com.pals.cyborg.Repository.NetworkRepository;
import com.pals.cyborg.Utils.PayLoads;

import java.util.ArrayList;

public class LedgerViewModel extends ViewModel implements OnNetworkTaskCompleted, OnParseCompleted {

    private MutableLiveData<ArrayList<LedgerModel>> ledgers;

    private MutableLiveData<String> error;

    private PayLoads payLoads;

    private NetworkRepository networkRepository;

    private LedgerParser ledgerParser;

    private MutableLiveData<Boolean> hasData;

    public LiveData<String> getError(){
        return error;
    }

    public LedgerViewModel(){
        ledgers = new MutableLiveData<>();
        error = new MutableLiveData<>();
        payLoads = new PayLoads();
        hasData = new MutableLiveData<>();
        networkRepository = new NetworkRepository(this);
        ledgerParser = new LedgerParser(this);
    }

    public LiveData<ArrayList<LedgerModel>> getLedgers(){
        getRemoteLedgers();
        return ledgers;
    }

    public LiveData<Boolean> hasData(){
        return hasData;
    }

    private void getRemoteLedgers(){
        networkRepository.doTask(payLoads.getLedPayload());
    }

    @Override
    public void OnSuccess(String res) {
         ledgerParser.execute(res);
    }

    @Override
    public void OnError(String err) {
       error.setValue(err);
    }

    @Override
    public void OnParsed(ArrayList<?> data) {
      if(data.size() > 0) {
          hasData.setValue(true);
          ledgers.setValue((ArrayList<LedgerModel>) data);
      }else{
          hasData.setValue(false);
      }
    }

    @Override
    public void OnParseFailed(String err) {
      error.setValue(err);
    }
}
