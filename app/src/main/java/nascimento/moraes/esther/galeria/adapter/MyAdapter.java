package nascimento.moraes.esther.galeria.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nascimento.moraes.esther.galeria.activity.MainActivity;
import nascimento.moraes.esther.galeria.activity.MyItem;

public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens){
        this.itens = itens;
    }
}
