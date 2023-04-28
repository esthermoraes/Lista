package nascimento.moraes.esther.lista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import nascimento.moraes.esther.lista.activity.MyItem;

public class MainActivityViewModel extends ViewModel {
    List<MyItem> itens = new ArrayList<>();

       public List<MyItem> getItens(){
           return itens;
       }
}
