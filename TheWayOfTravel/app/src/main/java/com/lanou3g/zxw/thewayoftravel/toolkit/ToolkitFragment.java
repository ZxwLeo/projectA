package com.lanou3g.zxw.thewayoftravel.toolkit;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.BaseFragment;
/**
 * Created by dllo on 16/5/9.
 * tablayout中的工具箱頁
 */
public class ToolkitFragment extends BaseFragment implements View.OnClickListener {
    private ImageView accountImg,exchangeImg,mapImg,translationImg;

    @Override
    protected int setLayout() {
        return R.layout.fragment_toolkit;
    }

    @Override
    protected void initView(View view) {
        accountImg = bindView(R.id.tools_account_img);
        exchangeImg = bindView(R.id.tools_exchange_img);
        mapImg = bindView(R.id.tools_map_img);
        translationImg = bindView(R.id.tools_translation_img);

    }

    @Override
    protected void initData() {
        accountImg.setOnClickListener(this);
        exchangeImg.setOnClickListener(this);
        mapImg.setOnClickListener(this);
        translationImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tools_account_img:
                //记账button
                Toast.makeText(context, "haha1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tools_exchange_img:
                //换算button
                Toast.makeText(context, "haha2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tools_map_img:
                //地图button
                Toast.makeText(context, "haha3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tools_translation_img:
                //翻译button
                Toast.makeText(context, "haha4", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
