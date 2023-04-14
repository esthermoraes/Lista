package nascimento.moraes.esther.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import nascimento.moraes.esther.lista.R;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1; // A static int serve para manter o seu valor entre as chamadas de função
    Uri photoSelected = null; // Esssa variavel do tipo Uri (é o endereço para um dado que não está  dentro do espaço do APP, mas sim no de outras APP's) guarda o endereço da foto escolhida pelo usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        ImageButton imgCI = findViewById(R.id.imbCI); // O ImageButton é um tipo de botão, que ao ser "clicado" tem como resposta uma imagem; Nessa APP, esse butão  abre a galeria do celular para que o usuario escolha uma imagem
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // O ACTION_OPEN_DOCUMENT é um tipo de ACTION que cria uma Intent implicita, usada para abrir a galeria para escolher a foto
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem);
        //Definição da ação do click do botão
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle); // Uso do metodo findViewById para localizar o elemento atraves de seu Id
                String title = etTitle.getText().toString(); // Retorna a informação digitada e "coletada" no/do elemento em forma de String
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc); // Uso do metodo findViewById para localizar o elemento atraves de seu Id
                String description = etDesc.getText().toString(); // Retorna a informação digitada e "coletada" no/do elemento em forma de String
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É mecessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(); // Pegando o parametro e passando ele de ação para poder enviar para alguem
                i.setData(photoSelected); //Indicando que seja respondido atraves do photoSelected
                i.putExtra("title", title);
                i.putExtra("description", description);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_PICKER_REQUEST){ // O requestCode mostra qual é a chamada da startActivityForResult, refere-se a resposta; Nesse APP, tem id PHOTO_PICKER_REQUEST
            if(resultCode == Activity.RESULT_OK){ // O resultCode mostre se ouve sucesso no retorno da Activity de destino
                photoSelected = data.getData(); // O data é uma intent que contém os dados retornados da Activity de destino
                ImageView imvPhotoPreview = findViewById(R.id.imvPhotoPreview); // A ImageView é um componente usado para exibição de imagens; Nessa APP, exibe a imagem escolhida pelo usuario em sua galeria
                imvPhotoPreview.setImageURI(photoSelected);
            }
        }
    }
}