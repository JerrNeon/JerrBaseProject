package com.cw.jerrbase.fragment;

import android.util.Log;

import com.cw.jerrbase.R;
import com.cw.jerrbase.activity.AutoLayoutTestActivity;
import com.cw.jerrbase.activity.ImageStatusActivity;
import com.cw.jerrbase.base.fragment.BaseFragment;
import com.cw.jerrbase.bean.MarketMainModelVO;
import com.cw.jerrbase.bean.MarketMainModelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/14 15:02
 */
public class RxJavaFragment extends BaseFragment {

    private int index = 0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_rxjava;
    }

    @OnClick(R.id.button2)
    public void rxjava() {
        switch (index) {
            case 0:
                rxjava1();
                index++;
                break;
            case 1:
                rxjava2();
                index++;
                break;
            case 2:
                rxjava3();
                index++;
                break;
            case 3:
                rxjava4();
                index++;
                break;
            case 4:
                rxjava5();
                index++;
                break;
            case 5:
                rxjava6();
                index++;
                break;
            case 6:
                rxjava7();
                index++;
                break;
            default:
                index = 0;
                break;
        }
    }

    @OnClick(R.id.buttonStatus)
    public void buttonStatus() {
        openActivity(ImageStatusActivity.class);
    }

    @OnClick(R.id.autoLayout)
    public void autoLayout(){openActivity(AutoLayoutTestActivity.class);}

    private void rxjava1() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 3; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i("tag", "rajava1------>" + integer);
            }
        });
    }

    private void rxjava2() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        Observable.from(list).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("tag", "rajava2------>" + s);
            }
        });
    }

    private void rxjava3() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 3; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.i("tag", "rajava3------>" + aBoolean);
            }
        });
    }

    private void rxjava4() {
        List<MarketMainModelList> mlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<MarketMainModelVO> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                MarketMainModelVO model = new MarketMainModelVO();
                model.setColor1("颜色" + j);
                list.add(model);
            }
            MarketMainModelList modellist = new MarketMainModelList();
            modellist.setMainList(list);
            mlist.add(modellist);
        }
        Observable.from(mlist).flatMap(new Func1<MarketMainModelList, Observable<MarketMainModelVO>>() {
            @Override
            public Observable<MarketMainModelVO> call(MarketMainModelList marketMainModelList) {
                return Observable.from(marketMainModelList.getMainList());
            }
        }).subscribe(new Subscriber<MarketMainModelVO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MarketMainModelVO marketMainModel) {
                Log.i("tag", "rajava4------>" + marketMainModel.getColor1());
            }
        });
    }

    private void rxjava5() {

    }

    private void rxjava6() {

    }

    private void rxjava7() {

    }
}
