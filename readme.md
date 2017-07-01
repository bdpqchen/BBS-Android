# 求实BBS-Android

## 应用介绍
  天津大学求实BBS社区客户端

## 项目介绍
1. 架构 ： MVP+RxJava2+Retrofit2+Dagger2+..
2. 基础设施：滑动返回、夜间模式、顶部提示..

## 项目结构

#### 关于模块
> 采用类模块化结构，所有功能模块统一依赖于commons，包括网络层，基础UI层，本地数据库，统一使用commons所提供的实现。添加模块时，可直接创建模块文件夹，继承/实现commons提供的基类/接口，

#### 关于夜间模式
   - 对于 *未* 自定义color/background...的View采用系统默认的配色
   - 对于 *已* 定义的color/background...的View需要在values-night添加相应的配色

#### 关于bugly

> 项目使用了bugly的bug上报模块,应用全量更新模块,热更新能力

 - bugly和instant run 不兼容, 如果要使用ir,请注释app 里build.gradle apply from: 'tinker-support.gradle'
 - 用到了一些独立的签名文件和key文件
 - 基线版本 tinkerID 为 base-[对应版本号]
 - 补丁版本 tinkerID 为 patch-[对应版本号]+[自增序号]

### 关于版本号
- 没有热更新过的版本号遵循 [主版本].[次版本].[bug修复版本]
- 次版本修改时机为 模块 功能的改动
- 热更新不会修改版本号, 只修改对应 tinkerID

#### 快速添加模块
```
添加任一功能模块时都需要在commons同级目录下新建对应package，任意两个package（功能模块）不应该相互依赖，
功能模块下仍可以创建所需的子package
```

##### 新建
- Activity : 任意可见的Activity都应列入对应的manifests、di依赖注入， 继承BaseActivity， 提供对应的Contract接口，Presenter 例如：
```
public class HomeActivity extends BaseActivity<HomePresenter> HomeContract.View {
    ......
}
```
```
public interface HomeContract {

    interface View extends BaseView{
        void showUpdateDialog(int versionCode);
    }

    interface Presenter extends BasePresenter<View>{
        void checkUpdate(int currentVersionCode);
    }
}
```
```
public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter{

    private RxDoHttpClient mHttpClient;

    @Inject
    public HomePresenter(RxDoHttpClient httpClient){
        LogUtil.d("HomePresenter is injected");
        this.mHttpClient = httpClient;
        mHttpClient.getDataList();
    }

    ...
}
```


- Fragment（作为容器）: 容器Fragment需要继承SimpleFragment重写对应函数
```
public class MainFragment extends SimpleFragment {
  ...
}
```

- Fragment（作为内容）: 作为内容提供者，需要继承BaseFragment, 并重写相应函数，类似与Activity需要提供Presenter，Contract
```
public class IndividualFragment extends BaseFragment<IndividualPresenter> implements IndividualContract.View {
  ....
}
```
- Presenter : 使用Dagger2依赖注入，任一Presneter 需要提供一个可注入的构造函数
```
public class IndividualPresenter extends RxPresenter<IndividualContract.View> implements IndividualContract.Presenter {

    @Inject
    public IndividualPresenter(){

    }
}
```
如果有参数，比如
```
@Inject
    public HomePresenter(RxDoHttpClient httpClient){
        LogUtil.d("HomePresenter is injected");
        this.mHttpClient = httpClient;
        mHttpClient.getDataList();
    }
```
需要在AppModule里提供对应Scope的对应实例
```
@Provides
 @Singleton
 RxDoHttpClient provideRxDoHttpClient(){
     return new RxDoHttpClient();
 }
```



- Adapter ：BaseAdapter目前只支持RecyclerView，并不完善，如有需要，可以使用。5.5

- Network ：


###

## 新特性

1. 惯用手模式

  即可支持左右单手模式。

  #### 定义
  > 我们把整个屏幕垂直等分3个区域，如果一个可操作的View处于两边的区域内，我们称这两个区域为单手盲区，
  那么在单手模式下，将进行对应的反方向定位该View, 使得用户在单手握手机的情况下，也能轻松操作该View.
  如果在同一水平方向上同时出现两个对称View分别处在两个盲区内，那么将考虑这两个View的操作频率而定，
  或者，在设计阶段尽力避免这样的情况。

  #### 优化事项
  - BottomBar、Toolbar上的操作对象暂不加入该功能。
2.
