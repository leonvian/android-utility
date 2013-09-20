package br.com.lvc.utility.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.lvc.utility.R;
import br.com.lvc.utility.util.loadimage.FileImageDecoder;
import br.com.lvc.utility.util.loadimage.ImageLoader;

public class CustomAdaptarImage<T extends ImageDetail> extends BaseCustomAdapter<T> {

	public CustomAdaptarImage(Activity a, List<T> d) {
		super(a, d);
	}

	@Override
	public View getView(int position, View viewGroup, ViewGroup parent) {
		ImageDetail componente = 	data.get(position);

		if(viewGroup == null) 
			viewGroup  = (ViewGroup) inflater.inflate(R.layout.item_com_foto, null);
		
		

		ImageView imageView = (ImageView) viewGroup.findViewById(R.id.image_view_foto);
	
		HashMap<String, String> descricaoValor = componente.getDescricaoValor();
		Set<String> descricoes = descricaoValor.keySet();
		
		LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.layout_descricao);
		linearLayout.removeAllViews();
		for(String descricao : descricoes) {
			String valor = descricaoValor.get(descricao);
			String valorFinal = descricao.concat(" ").concat(valor);

			TextView textView = (TextView) inflater.inflate(R.layout.text_view_adapter, null);
			linearLayout.addView(textView);
			textView.setText(valorFinal);	
		}
		
		ImageLoader.getInstance(activity, FileImageDecoder.SMALL_BITMAP).displayImage(componente.getURL(), imageView);

		return viewGroup;

	}
}