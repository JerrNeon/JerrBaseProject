package com.cw.andoridmvp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bigkoo.alertview.OnItemClickListener;
import com.cw.andoridmvp.adapter.CarHotListAdapter;
import com.cw.andoridmvp.base.adapter.BaseListAdapter;
import com.cw.andoridmvp.base.fragment.BaseLazyListFragment;
import com.cw.andoridmvp.bean.MarketMainModel;
import com.cw.andoridmvp.bean.MarketMainModelList;
import com.cw.andoridmvp.common.Constants;
import com.cw.andoridmvp.net.callback.ResultCallback;
import com.cw.andoridmvp.net.request.OkHttpRequest;
import com.cw.andoridmvp.okgo.OkgoResultCallback;
import com.cw.andoridmvp.retrofit.HttpRequestClient;
import com.cw.andoridmvp.retrofit.HttpResultCallback;
import com.cw.andoridmvp.util.ImageUtil;
import com.cw.andoridmvp.util.dialog.DialogUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (测试retrofit请求、仿IOS对话框、拍照相册)
 * @create by: chenwei
 * @date 2016/10/8 16:39
 */
public class TabInfoLvFragment extends BaseLazyListFragment<MarketMainModel> implements TakePhoto.TakeResultListener, InvokeListener {

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        //Log.i("tag", getParemters(getClass()));
        return mView;
    }

    @Override
    protected void sendRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("keywords", "");//搜索品牌，规格、指导价
        params.put("type", "1");//查询类型（1资源，2寻车，空为全部）
        params.put("page", pageIndex + "");
        params.put("rows", pageSize + "");
        //okhttpRequest(params);
        //retrofitRequest(params);
        okGoRequest(params);
    }

    private void okhttpRequest(Map<String, String> params) {
        String url = Constants.baseUrl + Constants.GET_MAIN_LIST;
        new OkHttpRequest.Builder().params(params).url(url).postValiForm(new ResultCallback<MarketMainModelList>(getActivity(), url) {

            @Override
            public void onResponse(MarketMainModelList response) {
                updateRefreshAndData(response.getMainList());
            }

            @Override
            public void onAfter() {
                super.onAfter();
                setPullUpOrDownRefreshComplete();
            }
        });
    }

    private void retrofitRequest(Map<String, Object> params) {
        HttpRequestClient.post(Constants.GET_MAIN_LIST, params, new HttpResultCallback<MarketMainModelList>(getActivity()) {

            @Override
            public void onSuccess(MarketMainModelList marketMainModelList) {
                updateRefreshAndData(marketMainModelList.getMainList());
                //DialogUtils.showDateTimeDialog(mContext);
                setPullUpOrDownRefreshComplete();
            }
        });
    }

    private void okGoRequest(Map<String, String> params) {
        String url = Constants.baseUrl + Constants.GET_MAIN_LIST;
        OkGo.post(url).tag(this).params(params).execute(new OkgoResultCallback<MarketMainModelList>(mContext) {
            @Override
            public void onSuccess(MarketMainModelList marketMainModelList) {
                updateRefreshAndData(marketMainModelList.getMainList());
                setPullUpOrDownRefreshComplete();
            }
        });
    }

    @Override
    protected BaseListAdapter getAdapter() {
        return new CarHotListAdapter(mContext);
    }

    @Override
    protected RequestType getRequestType() {
        return RequestType.PULLTOREFRESHLISTVIEW;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        DialogUtils.showImageDialog(mContext, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                //裁剪配置
                CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).create();
                //压缩配置(缺陷：不支持自定义压缩后的图片路径)
                CompressConfig config = new CompressConfig.Builder().setMaxSize(50 * 50).create();
                takePhoto.onEnableCompress(config, true);
                if (position == 0)
                    takePhoto.onPickFromCapture(Uri.fromFile(new File(ImageUtil.getImageCachPath(System.currentTimeMillis() + ""))));
                else if (position == 1)
                    takePhoto.onPickFromGallery();
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        DialogUtils.showAlertDialog(mContext, "确认删除吗", new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

            }
        });
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i("tag", "path------------>" + result.getImage().getPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(mContext, type, invokeParam, this);
    }
}
