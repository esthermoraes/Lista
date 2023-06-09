package nascimento.moraes.esther.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import nascimento.moraes.esther.lista.R;
import nascimento.moraes.esther.lista.adapter.MyAdapter;
import nascimento.moraes.esther.lista.model.MainActivityViewModel;
import nascimento.moraes.esther.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1; //A static int serve para manter o seu valor entre as chamadas de função | Já o NEW_ITEM_REQUEST é um identificador da chamada do metodo startActivityForResult, pois ele pode ser chamado várias vezes e é necessário identificar e diferenciar cada chamada
    List<MyItem> itens = new ArrayList<>();

    MyAdapter myAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ITEM_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    myItem.photo = photo;
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider( this ).get(MainActivityViewModel.class );
                List<MyItem> itens = vm.getItens();

                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);// O FloatingActionButton é um tipo de botão, que pode ser colocado acima ("flutua") de outros elementos de UI

        RecyclerView rvItens = findViewById(R.id.rvItens); //A Recycle View permite a exibição de itens em uma lista, "reciclando" os itens e fazendo com que otimize a memório do celular

        MainActivityViewModel vm = new ViewModelProvider( this ).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST); // o método startActivityForResult assume que a Activity de destino (no caso a NewItemAcitivity) vai retornar os dados em para a Activity inicial ( no caso a MainActivity)
            }
        });
    }
}